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
        const val IS_RECOGNIZED_KEY = "is_recognized"
    }

    private val dogDetailViewModel: DogDetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        val dog = intent?.extras?.parcelable<Dog>(DOG_DETAIL)
        val isRecognized = intent?.extras?.getBoolean(IS_RECOGNIZED_KEY, false) ?: false

        if (dog == null) {
            Toast.makeText(this, R.string.error_showing_dog_not_found, Toast.LENGTH_SHORT).show()
            finish()
            return
        }


        setContent {

            val state = dogDetailViewModel.uiState

            if (state.value is ApiResponseStatus.Success){
                finish()
            }else{
                DogDetailScreen(
                    dog = dog,
                    apiResponseStatus = state.value,
                    onAddDogToUser = {
                        onClickButton(
                            isRecognized = isRecognized,
                            dogId = dog.id
                        )
                    },
                    onDismissClick = {onDismissClick()}
                )
            }


        }
    }

    private fun onDismissClick(){
        dogDetailViewModel.resetApiResponse()
    }

    private fun onClickButton( dogId: Long,isRecognized: Boolean,) {
        if (isRecognized) {
            dogDetailViewModel.addDogToUser(dogId)
        } else {
            finish()
        }
    }
}