package com.example.dogedex.core.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dogedex.core.dogdetail.ui.theme.DogedexTheme
import com.example.dogedex.core.model.User
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
        startActivity(Intent(this, Class.forName("com.example.dogedex.camera.main.MainActivity")))
        finish()
    }
}