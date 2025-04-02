package com.example.dogedex.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.dogedex.R
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.model.Dog
import com.example.dogedex.parcelable
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
        //enableEdgeToEdge()
        setContent {

            DogDetailScreen(
                finishActivity = { finish() }
            )
        }
    }

}