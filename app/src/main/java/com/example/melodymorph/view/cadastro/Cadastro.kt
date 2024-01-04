package com.example.melodymorph.view.cadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.melodymorph.R
import com.example.melodymorph.databinding.ActivityCadastroBinding

class Cadastro : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}