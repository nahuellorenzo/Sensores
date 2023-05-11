package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import android.view.View

import android.os.VibratorManager

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private lateinit var vibrator: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener { vibratePhone() }
    }

    private fun vibratePhone() {
        if (vibrator.hasVibrator()) {
            if (android.os.Build.VERSION.SDK_INT >= 26) {
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

