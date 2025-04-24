package com.example.carteirinhaestudanteapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import android.Manifest
import android.content.pm.PackageManager
import com.example.carteirinhaestudanteapp.databinding.ActivityCadastrarCarteirinhaBinding
import java.io.File
import kotlin.random.Random

class CadastrarCarteirinhaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastrarCarteirinhaBinding
    private lateinit var currentPhotoPath: String

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            binding.imgFoto.setImageURI(it)
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            val photoUri = Uri.fromFile(File(currentPhotoPath))
            binding.imgFoto.setImageURI(photoUri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastrarCarteirinhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelecionarFoto.setOnClickListener {
            showImageSourceDialog()
        }

        binding.btnSalvar.setOnClickListener {
            val nome = binding.etNome.text.toString()
            val telefone = binding.etTelefone.text.toString()
            val cidade = binding.etCidade.text.toString()
            val estado = binding.etEstado.text.toString()
            val matricula = "MAT" + Random.nextInt(100000, 999999)

            val intent = Intent(this, CarteirinhaActivity::class.java).apply {
                putExtra("nome", nome)
                putExtra("telefone", telefone)
                putExtra("cidade", cidade)
                putExtra("estado", estado)
                putExtra("matricula", matricula)
                putExtra("fotoUri", currentPhotoPath)
            }

            startActivity(intent)
        }
    }

    private fun showImageSourceDialog() {
        val options = arrayOf("Galeria", "Câmera")
        AlertDialog.Builder(this)
            .setTitle("Selecionar foto")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> galleryLauncher.launch("image/*")
                    1 -> {
                        if (checkCameraPermission()) {
                            openCamera()
                        } else {
                            requestCameraPermission()
                        }
                    }
                }
            }
            .show()
    }

    private fun openCamera() {
        val photoFile = createImageFile()
        val photoUri = FileProvider.getUriForFile(
            this,
            "${packageName}.fileprovider",
            photoFile
        )
        currentPhotoPath = photoFile.absolutePath
        cameraLauncher.launch(photoUri)
    }

    private fun createImageFile(): File {
        val fileName = "foto_${System.currentTimeMillis()}.jpg"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(storageDir, fileName)
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSION
        )
    }

    companion object {
        const val REQUEST_CAMERA_PERMISSION = 1
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this, "Permissão de câmera negada", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
