package com.example.carteirinhaestudanteapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carteirinhaestudanteapp.databinding.ActivityRegisterBinding
import java.security.MessageDigest

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializa o ViewBinding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)

        // Função para gerar hash SHA-256 da senha
        fun hashPassword(password: String): String {
            val md = MessageDigest.getInstance("SHA-256")
            val hashBytes = md.digest(password.toByteArray())
            return hashBytes.joinToString("") { "%02x".format(it) }
        }

        binding.btnRegister.setOnClickListener {
            val username = binding.etNewUsername.text.toString()
            val password = binding.etNewPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val hashedPassword = hashPassword(password)

                // Salva os dados no SharedPreferences
                sharedPreferences.edit().apply {
                    putString("username", username)
                    putString("password", hashedPassword)
                    putBoolean("isLoggedIn", false)
                    apply()
                }

                Toast.makeText(this, "Cadastro realizado com sucesso! Faça login.", Toast.LENGTH_SHORT).show()

                // Limpa os campos
                binding.etNewUsername.text?.clear()
                binding.etNewPassword.text?.clear()

                // Vai para a tela de login
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvBackToLogin.setOnClickListener {
            finish() // Volta para a tela de login
        }
    }
}
