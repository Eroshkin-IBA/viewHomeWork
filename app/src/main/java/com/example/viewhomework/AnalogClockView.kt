package com.example.viewhomework

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import java.time.LocalTime
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin

class AnalogClockView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var hourL = 0
    private var minuteL = 0
    private var secondL = 0

    private val calendar = Calendar.getInstance()

    private val hourPaint = Paint()
    private val minutePaint = Paint()
    private val secondPaint = Paint()
    private val stripePaint = Paint()
    private val circlePaint = Paint()

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AnalogClockView)
        hourL = typedArray.getInteger(R.styleable.AnalogClockView_hourLength, 0)
        minuteL = typedArray.getInteger(R.styleable.AnalogClockView_minuteLength, 0)
        secondL = typedArray.getInteger(R.styleable.AnalogClockView_secondLength, 0)

        circlePaint.color = Color.BLACK
        circlePaint.strokeWidth = 30f
        circlePaint.style = Paint.Style.STROKE

        hourPaint.color = Color.BLACK
        hourPaint.strokeWidth = 40f

        minutePaint.color = Color.GREEN
        minutePaint.strokeWidth = 25f

        secondPaint.color = Color.BLUE
        secondPaint.strokeWidth = 5f

        stripePaint.color = Color.BLACK
        stripePaint.strokeWidth = 50f

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (Math.min(centerX, centerY) * 0.9).toFloat()

        canvas.drawCircle(centerX, centerY, radius, circlePaint)


        for (i in 0 until 12) {
            val angle = Math.PI / 6 * i
            val startX = centerX + (radius - 50) * cos(angle).toFloat()
            val startY = centerY + (radius - 50) * sin(angle).toFloat()
            val endX = centerX + radius * cos(angle).toFloat()
            val endY = centerY + radius * sin(angle).toFloat()
            canvas.drawLine(startX, startY, endX, endY, stripePaint)
        }

        val hours = calendar.get(Calendar.HOUR)
        val minutes = calendar.get(Calendar.MINUTE)
        val seconds = calendar.get(Calendar.SECOND)

        // Часовая стрелка
        val hourAngle = Math.PI * ((hours % 12) + minutes / 60.0) / 6
        canvas.drawLine(
            centerX - radius * 0.2f * cos(-hourAngle).toFloat(),
            centerY + radius * 0.2f * sin(-hourAngle).toFloat(),
            centerX + (radius * 0.5f + hourL) * cos(hourAngle).toFloat(),
            centerY + (radius * 0.5f + hourL) * sin(hourAngle).toFloat(),
            hourPaint
        )

        // Минутная стрелка
        val minuteAngle = Math.PI * (minutes - 15 + seconds / 60.0) / 30
        canvas.drawLine(
            centerX - radius * 0.2f * cos(-minuteAngle).toFloat(),
            centerY + radius * 0.2f * sin(-minuteAngle).toFloat(),
            centerX + (radius * 0.7f + minuteL) * cos(minuteAngle).toFloat(),
            centerY + (radius * 0.7f + minuteL) * sin(minuteAngle).toFloat(),
            minutePaint
        )

        // Секундная стрелка
        val secondAngle = Math.PI * (seconds - 15) / 30
        canvas.drawLine(
            centerX - radius * 0.2f * cos(-secondAngle).toFloat(),
            centerY + radius * 0.2f * sin(-secondAngle).toFloat(),
            centerX + (radius * 0.7f + secondL) * cos(secondAngle).toFloat(),
            centerY + (radius * 0.7f + secondL) * sin(secondAngle).toFloat(),
            secondPaint
        )

    }

    private val timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            // Обновляем время каждую секунду

            calendar.timeInMillis = System.currentTimeMillis()
            invalidate()
        }

        override fun onFinish() {}
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        timer.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        timer.cancel()
    }

}