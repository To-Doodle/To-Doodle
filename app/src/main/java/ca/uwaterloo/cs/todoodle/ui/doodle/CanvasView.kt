package ca.uwaterloo.cs.todoodle.ui.doodle

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import ca.uwaterloo.cs.todoodle.ui.doodle.DoodleFragment.Companion.paintBrush
import ca.uwaterloo.cs.todoodle.ui.doodle.DoodleFragment.Companion.path

class CanvasView : View {

    var params :  ViewGroup.LayoutParams? = null
    var canvas: Canvas? = null
    var bitmap: Bitmap? = null
    private var mPath: Path? = null
    private var mX = 0f
    private var mY = 0f
    private val TOUCH_TOLERANCE = 4f
    private var redoList = ArrayList<Path>()

    companion object{
        var pathList = ArrayList<Path>()
        var colourList = ArrayList<Int>()
        var currentBrush = Color.BLACK
    }

    constructor(context: Context) : this(context, null) {
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    fun init(width: Int, height: Int){
        paintBrush.isAntiAlias = true
        paintBrush.color = currentBrush
        paintBrush.style = Paint.Style.STROKE
        paintBrush.strokeJoin = Paint.Join.ROUND
        paintBrush.strokeWidth = 8f

//        params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap ?: return)
    }

    fun undo() {
        if (pathList.size != 0) {
            redoList.add((pathList.last()))
            pathList.removeAt(pathList.size - 1)
            invalidate()
        }
    }

    fun redo() {
        if (redoList.size != 0) {
            pathList.add(redoList.last())
            redoList.removeAt(redoList.size - 1)
            invalidate()
        }
    }

    private fun touchStart(x: Float, y: Float) {
        mPath = Path()
        pathList.add(mPath!!)

        mPath!!.reset()
        mPath!!.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touchMove(x: Float, y: Float) {
        val dx: Float = Math.abs(x - mX)
        val dy: Float = Math.abs(y - mY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath!!.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun touchUp() {
        mPath!!.lineTo(mX, mY)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var x = event.x
        var y = event.y

        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
                return true
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                redoList.clear()
                invalidate()
                return true
            }
            else -> return false
        }
        return false
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        this.canvas!!.drawColor(Color.WHITE)
        for(i in pathList.indices){
            this.canvas!!.drawPath(pathList[i], paintBrush)
            invalidate()
        }
        canvas.drawBitmap(bitmap!!, 0f, 0f, paintBrush)
        canvas.restore()
    }
}