package com.example.dogedex.core.dogdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dogedex.core.R
import com.example.dogedex.core.model.Dog

//@Preview(showSystemUi = true)
//@Composable
//fun MostProbableDogsDialogPreview() {
//    DogedexTheme {
//        Surface {
//            MostProbableDogsDialog(getFakeDogs(), {}) {}
//        }
//    }
//}

@Preview(apiLevel = 35)
@Composable
private fun PreviewDialog(){
    MostProbableDogsDialog(mostProbableDogs = getFakeDogs(), onShowMostProbableDogsDialogDismiss = {}, onItemClicked = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MostProbableDogsDialog(
    mostProbableDogs: MutableList<Dog>,
    onShowMostProbableDogsDialogDismiss: () -> Unit,
    onItemClicked: (Dog) -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = {
            onShowMostProbableDogsDialogDismiss()
        },
        modifier = Modifier
            .background(colorResource(android.R.color.white))
    ){

        Column(
            Modifier.padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.other_probable_dogs),
                color = colorResource(id = R.color.text_black),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            MostProbableDogsList(mostProbableDogs) {
                onItemClicked(it)
                onShowMostProbableDogsDialogDismiss()
            }

            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onShowMostProbableDogsDialogDismiss() }
                ) {
                    Text(stringResource(id = R.string.dismiss))
                }
            }
        }
        }
}


@Composable
fun MostProbableDogsList(dogs: MutableList<Dog>, onItemClicked: (Dog) -> Unit) {
    Box(
        Modifier.height(250.dp)
    ) {
        LazyColumn(
            content = {
                items(dogs) {
                    MostProbableDogItem(dog = it, onItemClicked)
                }
            }
        )
    }
}


@Composable
fun MostProbableDogItem(dog: Dog, onItemClicked: (Dog) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(
                enabled = true,
                onClick = { onItemClicked(dog) }
            ),
    ) {
        Text(
            dog.name,
            modifier = Modifier.padding(8.dp),
            color = colorResource(id = R.color.text_black),
        )
    }
}

fun getFakeDogs(): MutableList<Dog> {
    val dogList = mutableListOf<Dog>()
    dogList.add(
        Dog(
            1, 1, "Chihuahua", "Chihuahua", "Toy",
            "19", "Brave", "12 - 15",
            "2.0", "2.5", "12.0", false
        )
    )
    dogList.add(
       Dog(
            2, 2, "Pug", "Pug", "Toy",
            "12", "Friendly", "www.pug.com",
            "10 - 12", "4.5", "12.0", true
        )
    )
    dogList.add(
        Dog(
            3, 3, "Husky", "Husky", "Sporting",
            "15", "www.husky.com", "8 - 12 ",
            "5.0", "4.5", "12.0", false
        )
    )
    return dogList
}