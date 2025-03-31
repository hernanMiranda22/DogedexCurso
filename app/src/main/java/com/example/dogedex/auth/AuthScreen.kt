package com.example.dogedex.auth

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
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
    onUserLoggedIn : (User) -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
){

    val user = authViewModel.user
    val userValue = user.value
    if (userValue != null){
        onUserLoggedIn(userValue)
    }

    val navController = rememberNavController()

    val status = authViewModel.uiState.value

    AuthNavHost(
        onLoginButtonClick = { email, password ->
            authViewModel.login(email, password)
        },
        onSignUpButtonClick = { email, password, passwordConfirm ->
            authViewModel.signUp(email, password, passwordConfirm)
        },
        navController = navController,
        authViewModel = authViewModel
    )

    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {
        ErrorDialog(
            messageId = status.messageId,
            onDismissClick = { authViewModel.resetApiResponse() })
    }
}

@Composable
private fun AuthNavHost(
    navController: NavHostController,
    onLoginButtonClick:(String, String) -> Unit,
    onSignUpButtonClick:(String, String, String) -> Unit,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = loginScreenDestination
    ) {
        composable(route = loginScreenDestination) {
            LoginScreen(
                onRegisterButtonClick = { navController.navigate(route = signUpScreenDestination)},
                onLoginButtonClick = onLoginButtonClick,
                authViewModel= authViewModel
            )
        }

        composable(route = signUpScreenDestination) {
            SignUpScreen(
                onSignUpButtonClick = onSignUpButtonClick,
                onNavigationIconClick = {navController.navigateUp()} ,
                authViewModel = authViewModel
            )
        }
    }
}