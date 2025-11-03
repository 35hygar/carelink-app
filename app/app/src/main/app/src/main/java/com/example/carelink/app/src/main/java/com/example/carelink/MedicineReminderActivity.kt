package com.example.carelink

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MedicineReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = androidx.constraintlayout.widget.ConstraintLayout(this).apply {
            setBackgroundColor(getColor(android.R.color.white))
        }

        val title = TextView(this).apply {
            text = "💊 Напоминание"
            textSize = 32f
            setTextColor(getColor(android.R.color.black))
        }

        val medicineName = TextView(this).apply {
            text = "Пилюля"  // ← Это можно изменить позже
            textSize = 28f
            setTextColor(getColor(android.R.color.black))
        }

        val instruction = TextView(this).apply {
            text = "Не забудьте принять лекарство!"
            textSize = 24f
            setTextColor(getColor(android.R.color.darker_gray))
        }

        val confirmButton = androidx.appcompat.widget.AppCompatButton(this).apply {
            text = "✅ Принял(а)"
            textSize = 24f
            setBackgroundColor(getColor(android.R.color.holo_green_light))
            setOnClickListener {
                Toast.makeText(this@MedicineReminderActivity, "Отлично! Пилюля принята.", Toast.LENGTH_SHORT).show()
                finish() // Возврат на главный экран
            }
        }

        layout.addView(title)
        layout.addView(medicineName)
        layout.addView(instruction)
        layout.addView(confirmButton)

        setContentView(layout)

        // Простая расстановка (для MVP)
        title.post {
            val totalWidth = layout.width
            val totalHeight = layout.height

            title.x = 40f
            title.y = 80f

            medicineName.x = 40f
            medicineName.y = title.y + title.height + 40f

            instruction.x = 40f
            instruction.y = medicineName.y + medicineName.height + 20f

            confirmButton.x = (totalWidth - confirmButton.width) / 2f
            confirmButton.y = totalHeight * 0.7f
        }
    }
}
