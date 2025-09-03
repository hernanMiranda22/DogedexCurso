package com.example.dogedex.core.auth

//import android.content.Context
//import android.os.Bundle
//import android.util.Patterns
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.example.dogedex.R
//import com.example.dogedex.databinding.FragmentLoginBinding
//import com.example.dogedex.databinding.FragmentSignUpBinding
//import com.example.dogedex.isValidEmail
//
//class SignUpFragment : Fragment() {
//
//    private lateinit var binding: FragmentSignUpBinding
//
//
//    interface SignUpFragmentActions {
//        fun onSignUpFieldsValidate(email : String, password : String,
//                                   passwordConfirm : String)
//    }
//
//    private lateinit var signUpFragmentActions: SignUpFragmentActions
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        signUpFragmentActions = try {
//            context as SignUpFragmentActions
//        }catch (e : ClassCastException){
//            throw ClassCastException("$context must implement SignUpFragmentActions")
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentSignUpBinding.inflate(inflater)
//        setupSignUpButton()
//        return binding.root
//    }
//
//    private fun setupSignUpButton() {
//        binding.signUpButton.setOnClickListener {
//            validateField()
//        }
//    }
//
//    private fun validateField() {
//
//        binding.emailInput.error = ""
//        binding.passwordInput.error = ""
//        binding.passwordInput.error = ""
//
//        val email = binding.emailEdit.text.toString()
//
//        if (!isValidEmail(email)){
//            binding.emailInput.error = getString(R.string.email_is_no_valid)
//            return
//        }
//
//        val password = binding.passwordEdit.text.toString()
//        if (password.isEmpty()){
//            binding.passwordInput.error = getString(R.string.password_must_not_be_empty)
//            return
//        }
//
//        val passwordConfirm = binding.confirmPasswordEdit.text.toString()
//        if (password.isEmpty()){
//            binding.confirmPasswordInput.error = getString(R.string.password_must_not_be_empty)
//            return
//        }
//
//        if (password != passwordConfirm){
//            binding.passwordInput.error = getString(R.string.passwords_do_not_match)
//            return
//        }
//
//        //Sign Up
//        signUpFragmentActions.onSignUpFieldsValidate(email, password, passwordConfirm)
//    }
//}