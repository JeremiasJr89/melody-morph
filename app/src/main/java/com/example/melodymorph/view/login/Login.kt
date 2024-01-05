package com.example.melodymorph.view.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.melodymorph.R
import com.example.melodymorph.databinding.ActivityCadastroBinding
import com.example.melodymorph.databinding.ActivityLoginBinding
import com.example.melodymorph.view.cadastro.Cadastro
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonEntrar.setOnClickListener { view ->
            val email = binding.editMail.text.toString()
            val senha = binding.editSenha.text.toString()
            if (email.isEmpty() || senha.isEmpty()) {
                val snackar =
                    Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackar.setBackgroundTint(Color.RED)
                snackar.show()
            } else {
                auth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener { autentication ->
                        if (autentication.isSuccessful) {
                            goScreanMain()
                        }
                    }.addOnFailureListener { exception ->
                        val mensagemError = when (exception) {
                            is FirebaseAuthWeakPasswordException -> "Digite uma senha com no minimo 6 caracteres!"
                            is FirebaseAuthInvalidCredentialsException -> "Digite um email valido!"
                            is FirebaseAuthUserCollisionException -> "Esta conta ja foi cadastrada!"
                            is FirebaseNetworkException -> "Sem conexão com a internet!"
                            else -> "Error!"
                        }
                        val snackbar = Snackbar.make(
                            view,
                            mensagemError, Snackbar.LENGTH_SHORT
                        )
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()
                    }
            }
        }

        binding.txtCadastro.setOnClickListener {
            goScreanMain()
        }

    }

    private fun goScreanMain() {
        val intent = Intent(this, Cadastro::class.java)
        startActivity(intent)
        finish()
    }
}