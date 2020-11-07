package com.example.quiz

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class LineChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr){

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

        canvas?.drawRoundRect(RectF(),25.toFloat(),20.toFloat(),paint)

        //draw Axes
        var startwidth=width*10/100
        paint.color=Color.BLACK
        canvas?.drawRect(20f,20f,40f,40f,paint)

        //canvas?.drawText("Not implemented",(width/3).toFloat(),40f,paint)
        //canvas?.drawCircle((width/2).toFloat(),(height/2).toFloat(),20.toFloat(),paint)
    }


    private fun drawAxes(canvas: Canvas?){
        paint.color= Color.GRAY
        paint.strokeWidth=2f

        //canvas?.drawLine(10f,5f,10f,height.toFloat()-10f,paint)

        //x axes
        val heightOfX=height.toFloat()-50f

        canvas?.drawLine(12f,heightOfX,width.toFloat()-10f,heightOfX,paint)

        val increment = (width-26)/10
        var x = 26f
        val y = heightOfX
        for ( i in 1..10){
            canvas?.drawLine(x,y-10f,x,y+10f,paint)
            x+=increment
        }


    }

}