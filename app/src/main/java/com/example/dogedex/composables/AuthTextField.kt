package com.example.dogedex.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AuthFields(
    textValue : String,
    onTextChanged: (String) -> Unit,
    modifier : Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    labelValue : String,
){
    OutlinedTextField(
        value = textValue,
        onValueChange = { onTextChanged(it) },
        modifier = modifier,
        label = {
            Text(text = labelValue)
        },
        visualTransformation = visualTransformation,
    )
}