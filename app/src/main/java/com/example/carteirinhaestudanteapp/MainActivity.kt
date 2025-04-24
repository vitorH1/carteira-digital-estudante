package com.example.carteirinhaestudanteapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carteirinhaestudanteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences
        sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)

        // Limpa o estado de login ao abrir a MainActivity
        sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()

        // Quando o botão de login é clicado
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            val registeredUsername = sharedPreferences.getString("username", "")
            val registeredPassword = sharedPreferences.getString("password", "")

            if (username == registeredUsername && password == registeredPassword) {
                // Login bem-sucedido
                Toast.makeText(this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()

                // Salva o estado de login no SharedPreferences
                sharedPreferences.edit().apply {
                    putBoolean("isLoggedIn", true)
                    apply()
                }

                // Vai para o Dashboard
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()  // Fecha a MainActivity para que o usuário não volte a ela
            } else {
                // Se falhar o login
                Toast.makeText(this, "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show()
            }
        }

        // Quando o usuário quer se registrar
        binding.tvGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
