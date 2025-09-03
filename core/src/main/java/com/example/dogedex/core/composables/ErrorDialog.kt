package com.example.dogedex.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.dogedex.core.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDialog(
    messageId: Int,
    onDismissClick: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = {},
        modifier = Modifier
            .background(colorResource(android.R.color.white))
            .height(150.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .semantics { testTag = "error-dialog" },
        properties = DialogProperties(
            dismissOnBackPress = true
        )
    ) {
        Box(
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.oops_something_happened),
                    modifier = Modifier.padding(4.dp),
                    textAlign = TextAlign.Start,
                    color = colorResource(R.color.text_black),
                    fontSize = 16.sp
                )
                Text(
                    text = stringResource(messageId),
                    modifier = Modifier.padding(4.dp),
                    textAlign = TextAlign.Start,
                    color = colorResource(R.color.light_gray)
                )
            }

            Button(
                onClick = { onDismissClick() },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Text(text = "Try Again")
            }
        }
    }
}