package com.example.dogedex.viewmodel

import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.auth.AuthTasks
import com.example.dogedex.auth.AuthViewModel
import com.example.dogedex.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    private lateinit var viewModel : AuthViewModel


    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

//    @Test
//    fun `when email and password is empty `()



    @Test
    fun `when auth repository, login is complete and apiResponse return success`() = runTest {

        val fakeUser = User(1L, "Gfdgdfgfd@gmail.com" , "")

        class FakeRepositoryAuth :AuthTasks {
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(
                    fakeUser
                )
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirm: String
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(
                    fakeUser
                )
            }
        }



        viewModel = AuthViewModel(authRepository = FakeRepositoryAuth())

        viewModel.login("Gfdgdfgfd@gmail.com", "123456")

        assertEquals(fakeUser.email, viewModel.user.value?.email)

        assert(viewModel.uiState.value is ApiResponseStatus.Success)
    }

    @Test
    fun `when auth repository, login is complete and apiResponse return error`() = runTest {

        class FakeRepositoryAuth :AuthTasks {
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Error(20)
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirm: String
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Error(20)
            }
        }

        val email = ""
        val password = ""

        viewModel = AuthViewModel(authRepository = FakeRepositoryAuth())

        viewModel.login(email, password)

        assert(viewModel.uiState.value is ApiResponseStatus.Error)
    }

    @Test
    fun `when auth repository return state apiResponse is reset`() = runTest {

        class FakeRepositoryAuth :AuthTasks {
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Error(20)
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirm: String
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Error(20)
            }
        }


        viewModel = AuthViewModel(authRepository = FakeRepositoryAuth())

        viewModel.resetApiResponse()

        assert(viewModel.uiState.value == null)
    }


    @Test
    fun `when sign up is correct api status is success`() = runTest {
        val fakeUser = User(1L, "Gfdgdfgfd@gmail.com" , "hghghghghg")

        class FakeRepositoryAuth :AuthTasks {
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(
                    fakeUser
                )
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirm: String
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(
                    fakeUser
                )
            }
        }

        viewModel = AuthViewModel(authRepository = FakeRepositoryAuth())

        viewModel.signUp(fakeUser.email, "12345", "12345")
        assertEquals(fakeUser.email, viewModel.user.value?.email)

        assert(viewModel.uiState.value is ApiResponseStatus.Success)
    }

    @Test
    fun `when passwords is not identical api status is error`() = runTest {
        val fakeUser = User(1L, "Gfdgdfgfd@gmail.com" , "hghghghghg")

        class FakeRepositoryAuth :AuthTasks {
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(
                    fakeUser
                )
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirm: String
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Error(25)
            }
        }

        viewModel = AuthViewModel(authRepository = FakeRepositoryAuth())

        viewModel.signUp(fakeUser.email, "12345", "12345")

        assert(viewModel.uiState.value is ApiResponseStatus.Error)
    }
}