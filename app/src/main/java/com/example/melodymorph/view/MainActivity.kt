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
import java.lang.Long.getLong

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
                        val idade = documento.getLong("idade")
                        binding.txtResultadoNome.text = documento.getString("nome")
                        binding.txtResultadoSobrenome.text = documento.getString("nome")
                        binding.txtResultadoIdade.text = idade.toString()
                    }
                }
        }
        binding.buttonUpgradeData.setOnClickListener {
           db.collection(users).document("Jere")
               .update("sobrenome", "Santos").addOnCompleteListener{
                   Log.d("db_update","Sucesso ao atualizar os dados do usuario")
               }
        }
        binding.buttonDelData.setOnClickListener {
            db.collection(users).document("Jere")
                .delete().addOnCompleteListener{
                    Log.d("db_update","Sucesso ao deletar os dados do usuario")
                }
        }


    }

}