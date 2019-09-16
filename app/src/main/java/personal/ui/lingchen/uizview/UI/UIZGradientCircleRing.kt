package personal.ui.lingchen.uizview.UI

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import personal.ui.lingchen.uizview.Utils.OznerColorUtils

/**
 * @author：zach
 * @Date: 2019-09-09 09:58
 * @Email: xinde.zhang@cftcn.com
 * @ProjectName: UIZView
 * @PackageName: personal.ui.lingchen.uizview.UI
 * @Description: 渐变圆环
 */
class UIZGradientCircleRing : View {
    private val DEFUALT_VIEW_WIDTH = 50
    private val DEFUALT_VIEW_HEIGHT = 50
    private val mPaint: Paint = Paint()
    var startColor: Int = Color.argb(255, 17, 142, 233)
    var endColor: Int = Color.argb(255, 255, 59, 48)
    /**
     * 线长
     */
    var lineLength: Float = dpToPx(15f)
    var lineWidth: Float = dpToPx(5f)
        set(value) {
            field = dpToPx(value)
            mPaint.strokeWidth = field
            postInvalidate()
        }
    var outerRadius: Float = 20f
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    /**
     * 线的数量
     */
    var lineNum: Int = 60
        set(value) {
            field = if (value in 2..361) 4 else value
            postInvalidate()
        }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = lineWidth
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val width: Int
        val height: Int
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(dpToPx(DEFUALT_VIEW_WIDTH.toFloat()).toInt(), widthSize)
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = dpToPx(DEFUALT_VIEW_WIDTH.toFloat()).toInt()
        } else {
            width = dpToPx(DEFUALT_VIEW_WIDTH.toFloat()).toInt()
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(dpToPx(DEFUALT_VIEW_HEIGHT.toFloat()).toInt(), heightSize)
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            height = dpToPx(DEFUALT_VIEW_HEIGHT.toFloat()).toInt()
        } else {
            height = dpToPx(DEFUALT_VIEW_HEIGHT.toFloat()).toInt()
        }
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2.0f
        centerY = h / 2.0f
        outerRadius = (if (w < h) centerX else centerY) - dpToPx(lineWidth)
    }

    private fun drawCircle(canvas: Canvas?) {


        canvas?.let {
            it.save()
            it.translate(centerX, centerY)
            val sweepStep = 360f / lineNum
            canvas.rotate(-135f)
            for (index in 0..lineNum) {
                mPaint.color = OznerColorUtils.caculateColor(index * 100f / lineNum, startColor, endColor, intArrayOf(startColor))
                canvas.drawLine(outerRadius, 0f, outerRadius - lineLength, 0f, mPaint)
                it.rotate(sweepStep)
            }
            it.restore()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawCircle(canvas)
    }

    protected fun dpToPx(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }
}