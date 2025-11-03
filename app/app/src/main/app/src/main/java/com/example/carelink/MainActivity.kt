package com.example.carelink

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val PHONE_NUMBER = "+79005390290" // Оксана
    private val REQUEST_CALL_PERMISSION = 1
    private val REQUEST_SMS_PERMISSION = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Простой layout — 3 кнопки
        val layout = androidx.constraintlayout.widget.ConstraintLayout(this).apply {
            setBackgroundColor(ContextCompat.getColor(this@MainActivity, android.R.color.white))
        }

        val callButton = Button(this).apply {
            text = "📞 Позвонить Оксане"
            textSize = 24f
            setPadding(40, 40, 40, 40)
            setBackgroundColor(ContextCompat.getColor(this@MainActivity, android.R.color.holo_green_light))
            setOnClickListener {
                makeCall()
            }
        }

        val medicineButton = Button(this).apply {
            text = "💊 Лекарства"
            textSize = 24f
            setPadding(40, 40, 40, 40)
            setBackgroundColor(ContextCompat.getColor(this@MainActivity, android.R.color.holo_blue_light))
            setOnClickListener {
                startActivity(Intent(this@MainActivity, MedicineReminderActivity::class.java))
            }
        }

        val helpButton = Button(this).apply {
            text = "🆘 Помощь"
            textSize = 24f
            setPadding(40, 40, 40, 40)
            setBackgroundColor(ContextCompat.getColor(this@MainActivity, android.R.color.holo_red_light))
            setOnLongClickListener {
                sendSOS()
                true
            }
        }

        layout.addView(callButton)
        layout.addView(medicineButton)
        layout.addView(helpButton)

        setContentView(layout)

        // Упрощённая расстановка (для MVP — можно улучшить позже)
        callButton.post {
            val w = callButton.width
            val h = callButton.height
            val totalHeight = layout.height

            callButton.x = (layout.width - w) / 2f
            callButton.y = (totalHeight * 0.2f)

            medicineButton.x = (layout.width - w) / 2f
            medicineButton.y = (totalHeight * 0.45f)

            helpButton.x = (layout.width - w) / 2f
            helpButton.y = (totalHeight * 0.7f)
        }
    }

    private fun makeCall() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL_PERMISSION)
        } else {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$PHONE_NUMBER"))
            startActivity(intent)
        }
    }

    private fun sendSOS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), REQUEST_SMS_PERMISSION)
        } else {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$PHONE_NUMBER"))
            intent.putExtra("sms_body", "SOS! Мне нужна помощь!")
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Не удаётся отправить SMS", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CALL_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall()
                } else {
                    Toast.makeText(this, "Нужно разрешение на звонки", Toast.LENGTH_SHORT).show()
                }
            }
            REQUEST_SMS_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSOS()
                } else {
                    Toast.makeText(this, "Нужно разрешение на SMS", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
