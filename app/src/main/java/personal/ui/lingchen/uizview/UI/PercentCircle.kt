package personal.ui.lingchen.uizview.UI

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * @author：zach
 * @Date: 2019-12-12 20:26
 * @Email: xinde.zhang@cftcn.com
 * @ProjectName: UIZView
 * @PackageName: personal.ui.lingchen.uizview.UI
 * @Description: 2G厨上水机主页圆环背景
 */

class PercentCircle : View {
    val TAG: String = "PercentCircle"
    private val DEFAULT_WIDTH = dpTpPx(200f)
    private val DEFAULT_HEIGHT = dpTpPx(200f)
    var bgColor = Color.argb(0xff, 0xE1, 0xF7, 0xFF)
    var fontColor = Color.WHITE
    var dotColor = Color.argb(0xff,0x3c,0x89,0xf3)
    var dotBgColor = Color.WHITE
    var lineWidth = dpTpPx(5f)
    private var lastPercent: Int = 0
    var targetPercent: Int = 0
        set(value) {
            lastPercent = field
            field = if (value in 0..101) value else 0
            if (lastPercent != value) {
                startAnimator()
            }
        }
    private var currentPercent: Int = 0
    private var mWidth = 0
    private var mHeight = 0
    private var maxRadius = 0f
    private var maskFilter = BlurMaskFilter(10f, BlurMaskFilter.Blur.SOLID)
    private var paint: Paint = Paint()
    private var color1 = Color.argb(0xff, 0xf6, 0x6e, 0x6e)
    private var color2 = Color.argb(0xff, 0xc1, 0x69, 0xa8)
    private var color3 = Color.argb(0xff, 0x6b, 0x82, 0xd1)
    private var color4 = Color.argb(0xff, 0x3c, 0x89, 0xf3)
    private var sweepGradient: SweepGradient? = null
    private var animator: ValueAnimator? = null
    private fun startAnimator() {
        animator = ValueAnimator.ofInt(lastPercent, targetPercent)
        animator?.let {
            it.duration = (abs(targetPercent - lastPercent) / 100f * 1500).toLong()
            it.addUpdateListener { animation ->
                currentPercent = animation.animatedValue.toString().toInt()
                postInvalidate()
            }
            it.start()
        }
    }


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = lineWidth
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        var w = 0
        var h = 0

        when (widthMode) {
            MeasureSpec.EXACTLY -> {
                w = widthSize
            }
            MeasureSpec.AT_MOST -> {
                w = min(DEFAULT_WIDTH.toInt(), widthSize)
            }
            else -> {
                w = DEFAULT_WIDTH.toInt()
            }
        }

        when (heightMode) {
            MeasureSpec.EXACTLY -> {
                h = heightSize
            }
            MeasureSpec.AT_MOST -> {
                h = min(DEFAULT_HEIGHT.toInt(), heightSize)
            }
            else -> {
                h = DEFAULT_HEIGHT.toInt()
            }
        }
        setMeasuredDimension(w, h)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w - lineWidth.toInt()
        mHeight = h - lineWidth.toInt()

        maxRadius = min(mWidth, mHeight) / 2f


        sweepGradient = SweepGradient(mWidth / 2f, mHeight / 2f, intArrayOf(color2, color3, color4, color1, color2), null)
    }

    private fun drawBg(canvas: Canvas?) {
        canvas?.let {
            paint.style = Paint.Style.FILL
            paint.color = bgColor
            it.drawCircle(mWidth / 2f, mHeight / 2f, maxRadius, paint)
        }
    }

    private fun drawFont(canvas: Canvas?) {
        canvas?.let {
            //            setLayerType(LAYER_TYPE_SOFTWARE, null)
            paint.style = Paint.Style.FILL
            paint.setShadowLayer(10f, 0f, 5f, Color.GRAY)
            paint.color = fontColor
            paint.maskFilter = maskFilter
            it.drawCircle(mWidth / 2f, mHeight / 2f, maxRadius * 0.8f, paint)
            paint.maskFilter = null
        }
    }

    private fun drawValueLine(canvas: Canvas?) {
        canvas?.let {
            paint.setShadowLayer(0f, 0f, 0f, Color.TRANSPARENT)
            paint.color = Color.BLUE
            paint.style = Paint.Style.STROKE
            var left = mWidth / 2 - maxRadius * 0.9f
            var right = mWidth / 2 + maxRadius * 0.9f
            var top = mHeight / 2 - maxRadius * 0.9f
            var bottom = mHeight / 2 + maxRadius * 0.9f
            paint.shader = sweepGradient
            var angle = 360 * currentPercent / 100f
            if (currentPercent == 0) {
                angle = 0.01f
            }
            it.drawArc(RectF(left, top, right, bottom), 180f, angle, false, paint)
            paint.shader = null
        }
    }

    private fun drawStartDot(canvas: Canvas?) {
        canvas?.let {
            val dotCx = mWidth / 2f - maxRadius * 0.9f
            val dotCy = mHeight / 2f
            paint.color = dotBgColor
            val dotR = maxRadius * 0.05f
            paint.style = Paint.Style.FILL
            it.drawCircle(dotCx, dotCy, dotR, paint)
            paint.color = dotColor
            paint.style = Paint.Style.FILL_AND_STROKE
            it.drawPoint(dotCx, dotCy, paint)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawBg(canvas)
        drawFont(canvas)

        drawValueLine(canvas)
        drawStartDot(canvas)
    }

    private fun dpTpPx(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }


}