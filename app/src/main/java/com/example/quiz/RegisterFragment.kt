package com.example.quiz

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.quiz.databinding.FragmentRegisterBinding
import com.example.quiz.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth


class RegisterFragment : Fragment() {

    private lateinit var binding : FragmentRegisterBinding
    private lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_register,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            val password_again = binding.editTextTextPassword2.text.toString()

            register(email, password, password_again )
        }



    }

    private fun register(email: String, password: String, password_again: String){
        var appContext = requireContext()

        if (email.isNullOrEmpty() || password.isNullOrEmpty() || password_again.isNullOrEmpty()) {
            Toast.makeText(appContext,"Please fill in every field!", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length<6)
        {
            Toast.makeText(appContext,"Password is too short", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != password_again) {
            Toast.makeText(appContext, "Passwords doesn't match!", Toast.LENGTH_SHORT).show()
            return
        }

        sharedPref = context?.getSharedPreferences("credentials", Context.MODE_PRIVATE)!!
        var auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var editor=sharedPref.edit()
                    editor.clear()
                    editor.putString("email",email)
                    editor.putString("password",password)
                    editor.apply()
                    Toast.makeText(appContext, "Registration succesfull!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_startFragment)


                } else {
                    Toast.makeText(appContext, "Bad credentials! Please try again!", Toast.LENGTH_SHORT).show()
                }
            }

    }
}