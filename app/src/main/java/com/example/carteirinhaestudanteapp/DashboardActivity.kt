package com.example.carteirinhaestudanteapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.carteirinhaestudanteapp.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o ViewBinding
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences para verificar o estado do login
        val sharedPreferences: SharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)

        // Verifica se o usuário está logado
        if (!sharedPreferences.getBoolean("isLoggedIn", false)) {
            // Se não estiver logado, vai para a tela de login
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Fecha a atividade atual para que o usuário não volte para ela
        }

        // Configura os botões com ViewBinding
        binding.btnCadastrarCarteirinha.setOnClickListener {
            // Aqui vamos abrir a tela de cadastro da carteirinha
            val intent = Intent(this, CadastrarCarteirinhaActivity::class.java)
            startActivity(intent)
        }

        binding.btnVerCarteirinha.setOnClickListener {
            // Aqui vamos abrir a carteirinha do estudante
            val intent = Intent(this, CarteirinhaActivity::class.java)
            startActivity(intent)
        }
    }
}
