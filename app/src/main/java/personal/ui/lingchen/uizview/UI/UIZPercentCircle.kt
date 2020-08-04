package personal.ui.lingchen.uizview.UI

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import personal.ui.lingchen.uizview.R

/**
 * @author：zach
 * @Date: 2019-09-09 13:18
 * @Email: xinde.zhang@cftcn.com
 * @ProjectName: UIZView
 * @PackageName: personal.ui.lingchen.uizview.UI
 * @Description:碧丽水机滤芯百分比UI
 */
class UIZPercentCircle : View {
    val TAG: String = "UIZPercentCircle"
    private val DEFUALT_VIEW_WIDTH = 50
    private val DEFUALT_VIEW_HEIGHT = 50
    private val mPaint = Paint()
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var radius: Float = 0f
    private val rectF = RectF()
    private val offLineBGColor = -0x121213
    private val offLineValueColor = -0x434344
    private val defaultBGColor = -0x240e02
    var valueStartColor = -0x801906
    var valueEndColor = -0xe16409
    private val mCircleColors = intArrayOf(-0x732106, -0xeb3f06, -0xdb1a32, -0xf209ec, -0x73ed2, -0x9acf5, -0x94661)
    private val defaultThumbColor = Color.WHITE
    private val blurMaskFilter = BlurMaskFilter(10f, BlurMaskFilter.Blur.SOLID)

    private var animator: ValueAnimator? = null

    private var tempValue = 0

    fun startAnimator() {
        animator = ValueAnimator.ofInt(lastValue, targetValue)
        animator?.let {
            it.duration = ((Math.abs(targetValue - lastValue) / 100f) * 1500).toLong()
            it.addUpdateListener { animation ->
                tempValue = animation.animatedValue.toString().toInt()
                postInvalidate()
            }
            it.start()
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastValue = 0
                    startAnimator()
                }
            }
        }
        return super.onTouchEvent(event)
    }


    var isOnLine = true
        set(value) {
            field = value
            postInvalidate()
        }

    var bgColor: Int = defaultBGColor
        set(value) {
            field = value
            postInvalidate()
        }

    var thumbColor: Int = defaultThumbColor
        set(value) {
            field = value
            postInvalidate()
        }
    var circleWidth = 15f
        set(value) {
            field = value
            postInvalidate()
        }
    private var lastValue = 0

    var targetValue: Int = 0
        set(value) {
            lastValue = field
            field = if (value in 0..101) value else 100
            if (lastValue != value) {
                startAnimator()
            }
        }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.UIZPercentCircle)
        bgColor = typedArray.getColor(R.styleable.UIZPercentCircle_bgColor, defaultBGColor)
        valueStartColor = typedArray.getColor(R.styleable.UIZPercentCircle_valueStartColor, valueStartColor)
        valueEndColor = typedArray.getColor(R.styleable.UIZPercentCircle_valueEndColor, valueEndColor)
        circleWidth = typedArray.getDimension(R.styleable.UIZPercentCircle_circleWidth, circleWidth)
        thumbColor = typedArray.getColor(R.styleable.UIZPercentCircle_thumbColor, defaultThumbColor)
        targetValue = typedArray.getInt(R.styleable.UIZPercentCircle_targetValue, 0)
        typedArray.recycle()
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2f
        centerY = h / 2f
        radius = (if (w < h) centerX else centerY) - circleWidth
        rectF.left = centerX - radius
        rectF.top = centerY - radius
        rectF.right = centerX + radius
        rectF.bottom = centerY + radius
    }

    fun init() {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            //绘制背景
            mPaint.shader = null
            mPaint.maskFilter = null
            mPaint.color = if (isOnLine) bgColor else offLineBGColor
            mPaint.style = Paint.Style.STROKE
            mPaint.strokeWidth = circleWidth
            it.drawCircle(centerX, centerY, radius, mPaint)

            //绘制值


            //绘制起始点圆环

            if (isOnLine) {
                var shader = SweepGradient(centerX, centerY, intArrayOf(valueStartColor, valueEndColor, valueStartColor), floatArrayOf(0f, 0.96f, 1f))
                var matrix = Matrix()
                matrix.setRotate(180f, centerX, centerY)
                shader.setLocalMatrix(matrix)
                mPaint.shader = shader
            } else {
                mPaint.shader = null
                mPaint.color = offLineValueColor
            }

            if (tempValue == 0) {
//                mPaint.color = valueStartColor
                mPaint.style = Paint.Style.FILL
                it.drawCircle(centerX - radius, centerY, circleWidth / 2, mPaint)
            }

            mPaint.maskFilter = blurMaskFilter
            mPaint.style = Paint.Style.STROKE
            var sweep = tempValue * 360f / 100f
            if (tempValue == 0) {
                sweep = 0.01f
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.drawArc(rectF.left, rectF.top, rectF.right, rectF.bottom, -180f, sweep, false, mPaint)
            } else {
                it.drawArc(rectF, -180f, sweep, false, mPaint)
            }
//            }

            //绘制圆点
            mPaint.shader = null
            mPaint.maskFilter = null
            mPaint.color = Color.WHITE
            mPaint.style = Paint.Style.FILL
            it.drawCircle(centerX - radius, centerY, circleWidth / 4, mPaint)
        }
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

    protected fun dpToPx(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }
}