package com.example.carteirinhaestudanteapp

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.carteirinhaestudanteapp.databinding.ActivityCarteirinhaBinding

class CarteirinhaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarteirinhaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Força orientação horizontal
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // Inicializa o ViewBinding
        binding = ActivityCarteirinhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recupera os dados da Intent
        val nome = intent.getStringExtra("nome") ?: ""
        val telefone = intent.getStringExtra("telefone") ?: ""
        val cidade = intent.getStringExtra("cidade") ?: ""
        val estado = intent.getStringExtra("estado") ?: ""
        val matricula = intent.getStringExtra("matricula") ?: ""
        val fotoUri = intent.getStringExtra("fotoUri")

        // Atualiza a UI via binding
        binding.apply {
            tvNome.text = " $nome"
            tvTelefone.text = telefone
            tvCidadeEstado.text = "$cidade - $estado"
            tvMatricula.text = matricula

            fotoUri?.let {
                imgFotoCarteirinha.setImageURI(Uri.parse(it))
            }
        }
    }
}
