package com.example.melodymorph.view.cadastro

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.melodymorph.R
import com.example.melodymorph.databinding.ActivityCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseCommonRegistrar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.FirebaseCommonLegacyRegistrar

class Cadastro : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    private var auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonCadastrar.setOnClickListener { view ->
            val email = binding.editMail.text.toString()
            val senha = binding.editSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                val snackbar = Snackbar.make(
                    view,
                    getString(R.string.preencha_todos_os_campos), Snackbar.LENGTH_SHORT
                )
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {
                auth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener { cadastro ->
                        if (cadastro.isSuccessful) {
                            val snackbar = Snackbar.make(
                                view,
                                getString(R.string.sucesso_ao_cadastrar_usuario),
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.setBackgroundTint(Color.BLUE)
                            snackbar.show()
                            binding.editMail.setText("")
                            binding.editSenha.setText("")
                        }
                    }.addOnFailureListener { exception ->
                        val mensagemError = when (exception) {
                            is FirebaseAuthWeakPasswordException -> "Digite uma senha com no minimo 6 caracteres!"
                            is FirebaseAuthInvalidCredentialsException ->"Digite um email valido!"
                            is FirebaseAuthUserCollisionException -> "Esta conta ja foi cadastrada!"
                            is FirebaseNetworkException -> "Sem conexão com a internet!"
                            else -> "Erro ao cadastrar o usuario!"
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
    }
}