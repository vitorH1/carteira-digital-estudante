package com.example.carteirinhaestudanteapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carteirinhaestudanteapp.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Usamos SharedPreferences para armazenar o estado de login
        val sharedPreferences: SharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)

        // Verificar se o usuário já está logado
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            // Se o usuário estiver logado, redirecionamos para o Dashboard
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish() // Fechamos a LoginActivity para que o usuário não volte para ela
        }

        binding.btnLogin.setOnClickListener {
            val usuario = binding.etUsername.text.toString()
            val senha = binding.etPassword.text.toString()

            // Verifica as credenciais (aqui você pode personalizar conforme necessário)
            if (usuario == "usuarioExemplo" && senha == "senhaExemplo") {  // Credenciais de exemplo
                // Salva o estado de login no SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", true)  // Marcamos que o usuário está logado
                editor.apply()  // Salva as alterações

                // Redireciona para o DashboardActivity
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish() // Fecha a LoginActivity
            } else {
                // Caso as credenciais estejam incorretas
                Toast.makeText(this, "Credenciais inválidas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
