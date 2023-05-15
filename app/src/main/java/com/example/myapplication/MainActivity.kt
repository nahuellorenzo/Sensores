package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : ComponentActivity() {
    private lateinit var vibrator: Vibrator
    private val CAMERA_PERMISSION_REQUEST_CODE = 123
    private val CAMERA_REQUEST_CODE = 456
    private var isFlashOn: Boolean = false

    private lateinit var cameraManager: CameraManager
    private lateinit var cameraId: String
    private lateinit var toggleFlashButton: Button
    private lateinit var captureButton: Button
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        try {
            cameraId = cameraManager.cameraIdList[0] // Obtén el ID de la cámara trasera
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

        toggleFlashButton = findViewById(R.id.button2)
        toggleFlashButton.setOnClickListener {
            toggleFlashlight()
        }

        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        val button1 = findViewById<Button>(R.id.button1)

        captureButton = findViewById(R.id.captureButton)
        imageView = findViewById(R.id.imageView)

        captureButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
            } else {
                openCamera()
            }
        }

        button1.setOnClickListener { vibratePhone() }
    }

    private fun toggleFlashlight() {
        try {
            cameraManager.setTorchMode(cameraId, !isFlashOn) // Cambia el estado del flash
            isFlashOn = !isFlashOn
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(photo)
        }
    }

    private fun vibratePhone() {
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= 26) {
                Toast.makeText(this, "Vibrando", Toast.LENGTH_SHORT).show();
                vibrator.vibrate(VibrationEffect.createOneShot(5000, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(5000)
            }
        }
        else{
            Toast.makeText(this, "Error no tiene el vibrador", Toast.LENGTH_SHORT).show()
        }
    }
}

