package com.example.dogedex.doglist

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.dogedex.R
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.composables.ErrorDialog
import com.example.dogedex.composables.LoadingWheel
import com.example.dogedex.model.Dog

private const val GRID_SPAN_COUNT = 3

@Preview
@Composable
fun DogListScreenPreview() {
//    DogListScreen(listOf<Dog>(), {}, {})
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DogListScreen(
    onDogClicked: (Dog) -> Unit,
    onNavigationIconClick: () -> Unit,
    viewModel: DogListViewModel = hiltViewModel()
) {

    val state = viewModel.uiState.value
    val dogList = viewModel.dogList.value


    Scaffold(
        topBar = {
            DogListTopBar(onBackClick =  {onNavigationIconClick()})
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_SPAN_COUNT)
        ) {
            items(dogList) {
                DogGridItem(dog = it, onDogClicked = onDogClicked)
            }
        }
    }

    if (state is ApiResponseStatus.Loading){
        LoadingWheel()
    }else if (state is ApiResponseStatus.Error){
        ErrorDialog(messageId = state.messageId, onDismissClick = {viewModel.resetApiResponse()})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DogListTopBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text(stringResource(R.string.my_dog_collection)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black
        ),
        navigationIcon = { BackNavigationIcon(onBackClick) }
    )
}

@Composable
private fun BackNavigationIcon(onBackClick: () -> Unit) {
    IconButton(
        onClick = {onBackClick()}
    ) {
        Icon(
            painter = rememberVectorPainter(image = Icons.AutoMirrored.Sharp.ArrowBack),
            contentDescription = null
        )
    }
}

@Composable
private fun DogGridItem(
    dog: Dog,
    onDogClicked: (Dog) -> Unit
) {
    if (dog.inCollection) {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            onClick = { onDogClicked(dog) },
            shape = RoundedCornerShape(4.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(dog.imageUrl),
                contentDescription = null,
                modifier = Modifier.background(Color.White)
            )
        }
    } else {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            color = Red,
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = stringResource(R.string.dog_index_format, dog.index),
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontSize = 42.sp,
                fontWeight = FontWeight.Black
            )
        }
    }

}

//@Composable
//fun DogItem(dog: Dog, onDogClicked: (Dog) -> Unit) {
//    if (dog.inCollection) {
//        Text(
//            text = dog.name,
//            modifier = Modifier
//                .padding(16.dp)
//                .clickable {
//                    onDogClicked(dog)
//                }
//        )
//    } else {
//        Text(
//            text = stringResource(R.string.dog_index_format, dog.index),
//            modifier = Modifier
//                .padding(16.dp)
//                .background(color = Red)
//
//        )
//    }
//
//}
