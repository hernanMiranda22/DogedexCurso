package com.example.dogedex.core.dogdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.example.dogedex.core.R
import com.example.dogedex.core.api.ApiResponseStatus
import com.example.dogedex.core.composables.ErrorDialog
import com.example.dogedex.core.composables.LoadingWheel

@Preview(showSystemUi = true)
@Composable
fun DogDetailPreview(){
//    val dog = Dog(
//        1L, 78, "Pug", "Herding", "70",
//        "75", "", "10 - 12",
//        "Friendly, playful", "5", "6"
//    )
    DogDetailScreen(finishActivity =  { })
}

@Preview
@Composable
fun ErrorDialogPreview(){
    MostProbableDogsDialog(mostProbableDogs = getFakeDogs(), onShowMostProbableDogsDialogDismiss = {}, onItemClicked = {})
}

@Composable
fun DogDetailScreen(
    finishActivity : () -> Unit,
    dogDetailViewModel: DogDetailViewModel = hiltViewModel()
){
    val probableDogsDialogEnable = remember { mutableStateOf(false) }
    val state = dogDetailViewModel.uiState.value
    val dog = dogDetailViewModel.dog.value
    val isRecognized = dogDetailViewModel.isRecognition.value

    Scaffold(
        floatingActionButton = {
            FloatingButton(isRecognized, dogDetailViewModel, finishActivity)
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.colorSecondary))
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding(),
                ),
            contentAlignment = Alignment.TopCenter
        ){



            if (state is ApiResponseStatus.Success) {
                finishActivity()
            }
            DogInformation(dog, isRecognized){
                dogDetailViewModel.getProbableDogs()
                probableDogsDialogEnable.value = true
            }

            Image(
                painter = rememberAsyncImagePainter(dog.imageUrl),
                modifier = Modifier
                    .width(270.dp)
                    .padding(top = 80.dp),
                contentDescription = stringResource(R.string.dogImageContentDescription),
            )


            if (state is ApiResponseStatus.Loading){
                LoadingWheel()
            }else if (state is ApiResponseStatus.Error){
                ErrorDialog(messageId = state.messageId, onDismissClick =  {dogDetailViewModel.resetApiResponse()})
            }

            val probableDogList = dogDetailViewModel.probableDogList.collectAsStateWithLifecycle().value

            if (probableDogsDialogEnable.value){
                MostProbableDogsDialog(
                    mostProbableDogs =  probableDogList,
                    onShowMostProbableDogsDialogDismiss = {probableDogsDialogEnable.value = false},
                    onItemClicked = {
                        dogDetailViewModel.updateDog(it)
                    }
                )
            }

        }
    }

}

@Composable
private fun FloatingButton(
    isRecognized: Boolean,
    dogDetailViewModel: DogDetailViewModel,
    finishActivity: () -> Unit
) {
    FloatingActionButton(
        onClick = {
            if (isRecognized) {
                dogDetailViewModel.addDogToUser()
            } else {
                finishActivity()
            }
        },
        modifier = Modifier
            .padding(4.dp)
            .semantics { testTag = "close-details-screen-fab" },
        shape = CircleShape,
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = null
        )
    }
}

@Composable
fun DogInformation(
    dog: com.example.dogedex.core.model.Dog,
    isRecognized: Boolean,
    onProbableDogsButtonClick: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 180.dp)
    ){
        Surface(modifier = Modifier
            .fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            color = colorResource(android.R.color.white)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
               Text(
                   text = stringResource(R.string.dog_index_format, dog.index),
                   modifier = Modifier
                       .fillMaxWidth(),
                   fontSize = 32.sp,
                   color = colorResource(R.color.text_black),
                   textAlign = TextAlign.End,

               )

                Text(
                    text = dog.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
                    fontSize = 32.sp,
                    color = colorResource(R.color.text_black),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )

                LifeIcon()

                Text(
                    text = dog.lifeExpectancy,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontSize = 16.sp,
                    color = colorResource(R.color.text_black),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = dog.temperament,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    fontSize = 16.sp,
                    color = colorResource(R.color.text_black),
                    textAlign = TextAlign.Center
                )

                if (isRecognized) {
                    Button(
                        modifier = Modifier.padding(16.dp),
                        onClick = { onProbableDogsButtonClick() },
                    ) {
                        Text(
                            text = stringResource(id = R.string.not_your_dog_button),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                        )
                    }
                }
                
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 16.dp),
                    color = colorResource(R.color.divider)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DogDataColum(
                        gender = stringResource(R.string.female),
                        weight = dog.weightFemale,
                        height = dog.heightFemale,
                        modifier = Modifier.weight(1f)
                    )

                    VerticalDivider(
                        modifier = Modifier
                            .height(42.dp)
                            .padding(start = 8.dp, end = 8.dp),
                        thickness = 1.dp,
                        color = colorResource(R.color.divider)
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = dog.type,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            color = colorResource(R.color.text_black),
                            fontFamily = FontFamily.SansSerif)

                        Text(
                            text = stringResource(R.string.group),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = colorResource(R.color.dark_gray),
                            textAlign = TextAlign.Center
                        )
                    }


                    VerticalDivider(
                        modifier = Modifier
                            .height(42.dp)
                            .padding(start = 8.dp, end = 8.dp),
                        thickness = 1.dp,
                        color = colorResource(R.color.divider)
                    )

                    DogDataColum(
                        gender = stringResource(R.string.male),
                        weight = dog.weightMale,
                        height = dog.heightMale,
                        modifier = Modifier.weight(1f)
                    )

                }
            }
        }
    }
}

@Composable
fun LifeIcon() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 80.dp, end = 80.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = colorResource(id = R.color.color_primary)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_hearth_white),
                    contentDescription = null,
                    tint = colorResource(android.R.color.white),
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                        .padding(4.dp)
                )
            }

            Surface(
                shape = RoundedCornerShape(bottomEnd = 2.dp, topEnd = 2.dp),
                modifier = Modifier
                    .width(200.dp)
                    .height(6.dp),
                color = colorResource(R.color.color_primary)
            ) {  }
    }
}

@Composable
private fun DogDataColum(gender : String, weight : String, height : String, modifier : Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = gender,
            textAlign = TextAlign.Center,
            color = colorResource(R.color.text_black),
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = weight,
            textAlign = TextAlign.Center,
            color = colorResource(R.color.text_black),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = stringResource(R.string.weight),
            textAlign = TextAlign.Center,
            color = colorResource(R.color.text_black),
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = height,
            textAlign = TextAlign.Center,
            color = colorResource(R.color.text_black),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = stringResource(R.string.height),
            textAlign = TextAlign.Center,
            color = colorResource(R.color.text_black),
            modifier = Modifier.padding(8.dp)
        )
    }
}

