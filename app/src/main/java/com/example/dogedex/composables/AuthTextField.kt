package com.example.dogedex.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AuthFields(
    textValue : String,
    onTextChanged: (String) -> Unit,
    modifier : Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    labelValue : String,
    errorMessageId : Int? = null,
    errorSemantics : String = "",
    fieldSemantics : String = ""
){

    Column(
        modifier = modifier
    ) {
        if (errorMessageId != null){
            Text(
                text = stringResource(id = errorMessageId),
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { testTag = errorSemantics }
            )
        }
    }

    OutlinedTextField(
        value = textValue,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .semantics { testTag = fieldSemantics },
        label = {
            Text(text = labelValue)
        },
        visualTransformation = visualTransformation,
        isError = errorMessageId != null
    )
}