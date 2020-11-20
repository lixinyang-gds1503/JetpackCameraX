package cn.lxyhome.jetpackcamerax.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView





class LxyHomeImageView: View {
    private lateinit var mPaint: Paint
   // private lateinit var modePaint:Paint
    private var mode:PorterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)


    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }

    private fun init() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)

        mPaint.color = Color.GRAY
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = dip2px(context,0.1f).toFloat()
       // modePaint = Paint(mPaint)
        setLayerType(View.LAYER_TYPE_HARDWARE,null)
    }


    override fun onDraw(canvas: Canvas?) {
        //super.onDraw(canvas)
        canvas?.drawARGB(0, 0, 0, 0)
        val Bpath = getBpath()
        canvas?.drawPath(Bpath,mPaint)
        val saveLayer =
            canvas?.saveLayer(0f, 0f, width *7/8f,  width *7/8f, null)
        val dstBitmap = getDSTBitmap(width / 2, width / 2)
        canvas?.drawBitmap(dstBitmap,width*3/8f,width/4f,mPaint)
        mPaint.xfermode = mode
        canvas?.drawBitmap(getSRCBitmap(width/2,width/2),width/8f,width/8f,mPaint)
        mPaint.xfermode = null
        canvas?.restoreToCount(saveLayer!!)
    }

    private fun getBpath(): Path {
        val path: Path = Path()
        path.moveTo(0f,0f)
        path.lineTo(width.toFloat(),0f)
        path.lineTo(width.toFloat(),height.toFloat())
        path.lineTo(0f,height.toFloat())
        path.lineTo(0f,0f)
        path.close()
        return path
    }

    private fun getDSTBitmap(width:Int,height:Int):Bitmap{
        val dstBitmap  = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(dstBitmap)
        val dstPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        dstPaint.style =  Paint.Style.FILL
        dstPaint.color = Color.BLUE
        canvas.drawCircle(canvas.width/2f,canvas.width/2f,canvas.width/2f,dstPaint)
        return dstBitmap
    }

    private fun getSRCBitmap(width:Int,height:Int): Bitmap {
        val strBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(strBitmap)
        val srcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        srcPaint.style =  Paint.Style.FILL
        srcPaint.color = Color.RED
        canvas.drawRoundRect(RectF(0f,0f,canvas.width.toFloat(),canvas.height.toFloat()),6f,6f,srcPaint)
        return strBitmap
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        var context = context
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun setMode(mode: PorterDuff.Mode) {
        this.mode = PorterDuffXfermode(mode)
        invalidate()
    }
}