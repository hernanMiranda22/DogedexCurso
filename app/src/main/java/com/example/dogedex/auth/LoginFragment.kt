package com.example.dogedex.auth

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dogedex.R
import com.example.dogedex.databinding.FragmentLoginBinding
import com.example.dogedex.isValidEmail


class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding

    interface LoginFragmentActions {
        fun onRegisterButtonClick()
        fun onLoginButtonClick(email : String, password : String)
    }

    private lateinit var loginFragmentActions: LoginFragmentActions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginFragmentActions = try {
            context as LoginFragmentActions
        }catch (e : ClassCastException){
            throw ClassCastException("$context must implement LoginFragmentActions")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.loginRegisterButton.setOnClickListener {
            loginFragmentActions.onRegisterButtonClick()
        }
        binding.loginButton.setOnClickListener {
            validateField()
        }
        return binding.root
    }

    private fun validateField() {
        binding.emailLayout.error = ""
        binding.passwordLayout.error = ""
        binding.passwordLayout.error = ""

        val email = binding.emailEdit.text.toString()

        if (!isValidEmail(email)){
            binding.emailEdit.error = getString(R.string.email_is_no_valid)
            return
        }

        val password = binding.passwordEdit.text.toString()
        if (password.isEmpty()){
            binding.passwordEdit.error = getString(R.string.password_must_not_be_empty)
            return
        }

        loginFragmentActions.onLoginButtonClick(email, password)
    }
}