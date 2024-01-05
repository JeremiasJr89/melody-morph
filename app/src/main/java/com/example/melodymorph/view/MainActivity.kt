package com.example.melodymorph.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.melodymorph.databinding.ActivityMainBinding
import com.example.melodymorph.view.login.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val Auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val users = "Users"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonDeslogar.setOnClickListener {
            Auth.signOut()
            val backLogin =  Intent(this, Login::class.java)
            startActivity(backLogin)
            finish()
        }
        binding.buttonGravarData.setOnClickListener {

            val usersMap = hashMapOf(
                "nome" to "Jere",
                "sobrenome" to "Andrade",
                "idade" to 28,
                "cpf" to 11111111111
            )
            db.collection(users).document("Jere")
                .set(usersMap).addOnCompleteListener{
                    Log.d("db","Sucesso ao salvar os dados do usuario")
                }.addOnFailureListener{

                }
        }

        binding.buttonLerData.setOnClickListener {
            db.collection(users).document("Jere")
                .addSnapshotListener { documento, error ->
                    if (documento != null) {
                        binding.txtResultado.text = documento.getString("nome")
                    }
                }
        }


    }

}