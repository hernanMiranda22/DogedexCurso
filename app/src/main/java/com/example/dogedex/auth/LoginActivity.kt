package com.example.dogedex.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.example.dogedex.main.MainActivity
import com.example.dogedex.R
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.databinding.ActivityLoginBinding
import com.example.dogedex.dogdetail.ui.theme.DogedexTheme
import com.example.dogedex.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            DogedexTheme {
                AuthScreen(
                    onUserLoggedIn = ::startMainActivity,
                )
            }
        }
    }

    private fun startMainActivity(user : User) {
        User.setLoggedInUser(this, user)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

//    override fun onRegisterButtonClick() {
//        findNavController(R.id.nav_host_fragment)
//            .navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
//    }
//
//    override fun onLoginButtonClick(email: String, password: String) {
//        authViewModel.login(email,password)
//    }
//
//    override fun onSignUpFieldsValidate(
//        email: String,
//        password: String,
//        passwordConfirm: String
//    ) {
//        authViewModel.signUp(email, password, passwordConfirm)
//
//    }

//    private fun showErrorDialog(messageId : Int){
//        AlertDialog.Builder(this)
//            .setTitle(R.string.there_was_an_error)
//            .setMessage(messageId)
//            .setPositiveButton(android.R.string.ok){ _, _ -> }
//            .create()
//            .show()
//    }

    //        val binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_host_fragment)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        authViewModel.uiState.observe(this){ uiState ->
//
//            when(uiState){
//                is ApiResponseStatus.Error -> {
//                    showErrorDialog(uiState.messageId)
//                    binding.loadingWheel.visibility = View.GONE
//                }
//                is ApiResponseStatus.Loading -> {
//                    binding.loadingWheel.visibility = View.VISIBLE
//                }
//                is ApiResponseStatus.Success -> {
//                    binding.loadingWheel.visibility = View.GONE
//                }
//            }
//        }
//
//        authViewModel.userLivedata.observe(this){ user ->
//            if (user != null){
//                User.setLoggedInUser(this, user)
//                startMainActivity()
//            }
//        }
}