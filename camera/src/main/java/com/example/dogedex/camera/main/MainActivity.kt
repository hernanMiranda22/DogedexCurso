package com.example.dogedex.camera.main

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.Manifest
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dogedex.core.auth.LoginActivity
import com.example.dogedex.core.api.ApiResponseStatus
import com.example.dogedex.core.dogdetail.DogDetailComposeActivity
import com.example.dogedex.core.doglist.DogListActivity
import com.example.dogedex.camera.machinelearning.DogRecognition
import com.example.dogedex.canera.R
import com.example.dogedex.canera.databinding.ActivityMainBinding
import com.example.dogedex.core.api.ApiServiceInterceptor
import com.example.dogedex.core.model.User.Companion.getLoggedInUser
import com.example.dogedex.core.settings.SettingsActivity
import com.example.dogedex.core.testutils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                setupCamera()
            } else {
                Toast.makeText(this,
                    getString(R.string.you_need_to_accept_camera_permission), Toast.LENGTH_SHORT).show()
            }
        }

    private lateinit var binding : ActivityMainBinding
    private lateinit var imageCapture : ImageCapture
    private lateinit var cameraExecutor : ExecutorService
    private var isCameraReady = false
    private val mainViewModel : MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val user = getLoggedInUser(this)
        if (user == null){
            openLoginActivity()
            return
        }else{
            ApiServiceInterceptor.setSessionToken(user.authenticationToken)
        }

        binding.settingFab.setOnClickListener {
            openSettingActivity()
        }

        binding.dogListFab.setOnClickListener {
            openDogListActivity()
        }

        mainViewModel.status.observe(this){ uiState ->
            when(uiState){
                is ApiResponseStatus.Error -> {
                    Toast.makeText(this, uiState.messageId , Toast.LENGTH_SHORT).show()
                    binding.loadingWheel.visibility = View.GONE
                }
                is ApiResponseStatus.Loading -> {
                    binding.loadingWheel.visibility = View.VISIBLE
                }
                is ApiResponseStatus.Success -> {
                    binding.loadingWheel.visibility = View.GONE
                }
            }
        }

        mainViewModel.dogRecognized.observe(this){
            enabledTakePhotoButton(it)
        }

        mainViewModel.dog.observe(this){
            dog ->
            if (dog != null){
                openDogDetailActivity(dog)
            }
        }

        requestCameraPermission()
    }

    private fun openDogDetailActivity(dog: com.example.dogedex.core.model.Dog) {
        val intent = Intent(this, DogDetailComposeActivity::class.java)
        intent.putExtra(DogDetailComposeActivity.DOG_DETAIL, dog)
        intent.putExtra(DogDetailComposeActivity.MOST_PROBABLE_DOGS_IDS, ArrayList<String>(mainViewModel.probableDogIds))
        intent.putExtra(DogDetailComposeActivity.IS_RECOGNIZED_KEY, true)
        startActivity(intent)
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {

                setupCamera()
            }

           ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.CAMERA
            ) -> {

                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.aceptame))
                    .setMessage(getString(R.string.aceptame_por_favor))
                    .setPositiveButton(android.R.string.ok){ _,_ ->
                        requestPermissionLauncher.launch(
                            Manifest.permission.CAMERA
                        )
                    }
                    .setNegativeButton(android.R.string.cancel){
                            _,_ ->
                    }.show()
            }

            else -> {
                // You can directly ask for the permission.
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }

    }

    private fun setupCamera(){
        binding.cameraPreview.post {
            imageCapture = ImageCapture.Builder()
                .setTargetRotation(binding.cameraPreview.display.rotation)
                .build()

            cameraExecutor = Executors.newSingleThreadExecutor()
            startCamera()
            isCameraReady = true
        }

    }

//    private fun takePhoto(){
//        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(getOutputPhotoFile()).build()
//        imageCapture.takePicture(outputFileOptions, cameraExecutor,
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onError(error: ImageCaptureException)
//                {
//                    Toast.makeText(this@MainActivity, "Error taking photo ${error.message}", Toast.LENGTH_SHORT).show()
//                }
//                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                    // insert your code here.
//
//                }
//            })
//    }
//
//    private fun getOutputPhotoFile(): File {
//        val mediaDir = externalMediaDirs.firstOrNull()?.let {
//            File(it, resources.getString(R.string.app_name) + ".jpg").apply { mkdirs() }
//        }
//        return if (mediaDir != null && mediaDir.exists()){
//            mediaDir
//        }else{
//            filesDir
//        }
//    }

    private fun startCamera(){
        //Instancia de la camara
        val cameraProviderFuture =
            ProcessCameraProvider.getInstance(this)

        EspressoIdlingResource.increment()

        cameraProviderFuture.addListener({
            //Used to bind the lifecyle of cameras to the lifecycle owner
            val cameraProvider = cameraProviderFuture.get()

            //preview
            val preview = Preview.Builder().build()
            preview.surfaceProvider = binding.cameraPreview.surfaceProvider

            //select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
                EspressoIdlingResource.decrement()
                mainViewModel.recognizedImage(imageProxy)
            }

            cameraProvider.bindToLifecycle(
                this, cameraSelector, preview, imageCapture, imageAnalysis
            )
        }, ContextCompat.getMainExecutor(this))
    }

    private fun enabledTakePhotoButton(dogRecognition: DogRecognition) {
        if (dogRecognition.confidence > 70.0){
            binding.takePhotoFab.alpha = 1f
            binding.takePhotoFab.setOnClickListener {
                mainViewModel.getDogByMlId(dogRecognition.id)
            }
        }else{
            binding.takePhotoFab.alpha = 0.2f
            binding.takePhotoFab.setOnClickListener(null)
        }
    }

    private fun openDogListActivity() {
        startActivity(Intent(this,DogListActivity::class.java))
    }

    private fun openSettingActivity() {
        startActivity(Intent(this,SettingsActivity::class.java))
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::cameraExecutor.isInitialized){
            cameraExecutor.shutdown()
        }

    }
}