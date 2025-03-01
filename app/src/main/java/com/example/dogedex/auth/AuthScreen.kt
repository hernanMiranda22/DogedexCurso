package com.example.dogedex.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.auth.AuthNavDestination.loginScreenDestination
import com.example.dogedex.auth.AuthNavDestination.signUpScreenDestination
import com.example.dogedex.composables.ErrorDialog
import com.example.dogedex.composables.LoadingWheel
import com.example.dogedex.model.User

@Composable
fun AuthScreen(
    status : ApiResponseStatus<User>?,
    onErrorDialogDismiss : () -> Unit,
    onLoginButtonClick:(String, String) -> Unit,
    onSignUpButtonClick:(String, String, String) -> Unit
){
    val navController = rememberNavController()

    AuthNavHost(
        onLoginButtonClick = onLoginButtonClick,
        onSignUpButtonClick = onSignUpButtonClick,
        navController = navController)

    if (status is ApiResponseStatus.Loading){
        LoadingWheel()
    }else if (status is ApiResponseStatus.Error){
        ErrorDialog(messageId = status.messageId, onDismissClick = onErrorDialogDismiss)
    }
}

@Composable
private fun AuthNavHost(
    navController: NavHostController,
    onLoginButtonClick:(String, String) -> Unit,
    onSignUpButtonClick:(String, String, String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = loginScreenDestination
    ) {
        composable(route = loginScreenDestination) {
            LoginScreen(
                onRegisterButtonClick = { navController.navigate(route = signUpScreenDestination)},
                onLoginButtonClick = onLoginButtonClick
            )
        }

        composable(route = signUpScreenDestination) {
            SignUpScreen(
                onSignUpButtonClick = onSignUpButtonClick,
                onNavigationIconClick = {navController.navigateUp()}
            )
        }
    }
}