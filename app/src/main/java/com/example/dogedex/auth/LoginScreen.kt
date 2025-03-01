package com.example.dogedex.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dogedex.R
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.composables.AuthFields

@Preview
@Composable
fun LoginPreview(){
    //LoginScreen()
}

@Composable
fun LoginScreen(
    status : ApiResponseStatus.Error<Any>? = null,
    onRegisterButtonClick:() -> Unit,
    onLoginButtonClick:(String, String) -> Unit,

    ){
    Scaffold(
        topBar = {
            LoginTopBar()
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
        ){
            ContentLogin(
                onLoginButtonClick = onLoginButtonClick,
                onRegisterButtonClick = onRegisterButtonClick
            )
        }
    }
}

@Composable
private fun ContentLogin(
    onLoginButtonClick:(String, String) -> Unit,
    onRegisterButtonClick:() -> Unit,
) {

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 32.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthFields(
            textValue = email.value,
            onTextChanged = { newValue -> email.value = newValue },
            modifier = Modifier
                .fillMaxWidth(),
            labelValue = stringResource(R.string.email)
        )

        AuthFields(
            textValue = password.value,
            onTextChanged = { newValue -> password.value = newValue },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            labelValue = stringResource(R.string.password),
            visualTransformation = PasswordVisualTransformation()
        )
        
        Button(
            onClick = { onLoginButtonClick(email.value, password.value) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.color_primary)
            )
        ) {
            Text(text = stringResource(R.string.login))
        }

        Text(
            text = stringResource(R.string.do_not_have_an_account),
            modifier = Modifier.padding(top = 16.dp)
        )

        Button(
            onClick = { onRegisterButtonClick() },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(android.R.color.white)
            )
        ) {
            Text(
                text = stringResource(R.string.register),
                color = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginTopBar() {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.color_primary),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}
