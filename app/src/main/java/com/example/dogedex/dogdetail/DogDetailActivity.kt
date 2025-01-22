package com.example.dogedex.dogdetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil3.load
import com.example.dogedex.model.Dog
import com.example.dogedex.R
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.databinding.ActivityDogDetailBinding
import com.example.dogedex.parcelable

class DogDetailActivity : AppCompatActivity() {

    companion object {
        const val DOG_DETAIL = "dog"
        const val IS_RECOGNIZED_KEY = "is_recognized"
    }

    private val dogDetailViewModel : DogDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dog = intent?.extras?.parcelable<Dog>(DOG_DETAIL)
        val isRecognized = intent?.extras?.getBoolean(IS_RECOGNIZED_KEY, false) ?: false

        if (dog == null){
            Toast.makeText(this, R.string.error_showing_dog_not_found , Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        dogDetailViewModel.uiState.observe(this){ uiState ->

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
                    finish()
                }
            }
        }

        binding.dogIndex.text = getString(R.string.dog_index_format, dog.index)
        binding.lifeExpectancy.text = getString(R.string.dog_life_expectancy_format, dog.lifeExpectancy)
        binding.dog = dog
        binding.dogImage.load(dog.imageUrl)
        binding.closeButton.setOnClickListener {
            if (isRecognized){
                dogDetailViewModel.addDogToUser(dog.id)
            }
            finish()
        }
    }
}