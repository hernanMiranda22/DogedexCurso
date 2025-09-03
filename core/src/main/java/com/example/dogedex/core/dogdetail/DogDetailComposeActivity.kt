package com.example.dogedex.core.dogdetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogDetailComposeActivity : ComponentActivity() {

    companion object {
        const val DOG_DETAIL = "dog"
        const val MOST_PROBABLE_DOGS_IDS = "most_probable_dogs_ids"
        const val IS_RECOGNIZED_KEY = "is_recognized"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            DogDetailScreen(
                finishActivity = { finish() }
            )
        }
    }

}