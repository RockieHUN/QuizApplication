package com.example.quiz

import android.content.Context
import android.content.SharedPreferences
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

class LineChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr){


    private lateinit var sharedPref : SharedPreferences

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply{
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("",Typeface.BOLD)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val view= requireViewById<View>(R.id.LineChart)
        val width = view.width
        val height = view.height

        paint.strokeWidth=2f
        paint.color= Color.RED

        //get results from shared pref
        sharedPref = context.getSharedPreferences("results",Context.MODE_PRIVATE)
        var preferences = sharedPref.all

        var total = preferences["total"]
        var correct = preferences ["correct"]
        var wrong = preferences["wrong"]

        total as Int
        correct as Int
        wrong as Int

        var startwidth=width*10/100
        var rectWidth = (width - 2*startwidth) / 3
        var unit = height / total


        //draw chart
        paint.color = Color.GREEN
        canvas?.drawRect(startwidth.toFloat(),(height-unit*correct).toFloat(),(startwidth+rectWidth).toFloat(),height.toFloat(),paint)
        startwidth += rectWidth
        paint.color = Color.BLUE
        canvas?.drawRect(startwidth.toFloat(),(height-unit*total).toFloat(),(startwidth+rectWidth).toFloat(),height.toFloat(),paint)
        startwidth+=rectWidth
        paint.color = Color.RED
        canvas?.drawRect(startwidth.toFloat(),(height-unit*wrong).toFloat(),(startwidth+rectWidth).toFloat(),height.toFloat(),paint)


    }

}