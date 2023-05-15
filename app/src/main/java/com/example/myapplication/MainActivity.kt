package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import android.content.Intent
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build


class MainActivity : ComponentActivity() {
    private lateinit var vibrator: Vibrator
    private var isFlashOn: Boolean = false

    private lateinit var cameraManager: CameraManager
    private lateinit var cameraId: String
    private lateinit var toggleFlashButton: Button
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

        button1.setOnClickListener { vibratePhone() }

        var buttonCamara = findViewById<Button>(R.id.irACamara)
        buttonCamara.setOnClickListener{
            val intent = Intent(this, CameraActivity:: class.java)
            startActivity(intent)
        }
        }
        private fun toggleFlashlight() {
            try {
                cameraManager.setTorchMode(cameraId, !isFlashOn) // Cambia el estado del flash
                isFlashOn = !isFlashOn
            } catch (e: CameraAccessException) {
                e.printStackTrace()
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


