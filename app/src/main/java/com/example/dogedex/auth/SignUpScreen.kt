package com.example.dogedex.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dogedex.R
import com.example.dogedex.composables.AuthFields

@Preview
@Composable
fun PreviewSignUp() {
    //SignUpScreen()
}

@Composable
fun SignUpScreen(
    onNavigationIconClick: () -> Unit,
    onSignUpButtonClick: (String, String, String) -> Unit,
    authViewModel: AuthViewModel
) {
    Scaffold(
        topBar = {
            SignUpTopBar(
                onNavigationIconClick = onNavigationIconClick
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            ContentSignUp(
                resetFieldErrors = {authViewModel.resetErrors()},
                onSignUpButtonClick = onSignUpButtonClick,
                authViewModel = authViewModel
            )
        }
    }
}

@Composable
private fun ContentSignUp(
    resetFieldErrors : () -> Unit,
    onSignUpButtonClick: (String, String, String) -> Unit,
    authViewModel: AuthViewModel
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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
            textValue = email,
            onTextChanged = {
                email = it
                resetFieldErrors()
            },
            modifier = Modifier
                .fillMaxWidth(),
            labelValue = stringResource(R.string.email),
            errorMessageId = authViewModel.emailError.value
        )


        AuthFields(
            textValue = password,
            onTextChanged = {
                password = it
                resetFieldErrors()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            labelValue = stringResource(R.string.password),
            visualTransformation = PasswordVisualTransformation(),
            errorMessageId = authViewModel.passwordError.value
        )

        AuthFields(
            textValue = confirmPassword,
            onTextChanged = {
                confirmPassword = it
                resetFieldErrors()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            labelValue = stringResource(R.string.confirm_password),
            visualTransformation = PasswordVisualTransformation(),
            errorMessageId = authViewModel.confirmPasswordError.value
        )

        Button(
            onClick = {onSignUpButtonClick(email,password,confirmPassword)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .semantics { testTag = "signUp-button" },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.color_primary)
            )
        ) {
            Text(
                text = stringResource(R.string.sign_up)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignUpTopBar(
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.color_primary),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        ),
        navigationIcon = {
            IconBack(
                onNavigationIconClick = onNavigationIconClick
            )
        }
    )
}


@Composable
fun IconBack(onNavigationIconClick: () -> Unit) {
    IconButton(onClick = { onNavigationIconClick() }) {
        Icon(
            painter = rememberVectorPainter(
                Icons.AutoMirrored.Sharp.ArrowBack
            ),
            contentDescription = null
        )
    }
}