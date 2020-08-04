package personal.ui.lingchen.uizview.UI

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.max
import kotlin.math.min

/**
 * @author：zach
 * @Date: 2019-11-01 16:58
 * @Email: xinde.zhang@cftcn.com
 * @ProjectName: UIZView
 * @PackageName: personal.ui.lingchen.uizview.UI
 * @Description:
 */
class UIZRippleIntroView : View, Runnable {
    override fun run() {
        removeCallbacks(this)
        count += 10
        count %= interval
        invalidate()
    }

    var maxRadius: Int = 500
    var interval = 50
    var count = 0
    var centerRippleColor = 0x3C89F3
    lateinit var mRipplePaint: Paint

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        mRipplePaint = Paint()
        mRipplePaint.isAntiAlias = true
        mRipplePaint.style = Paint.Style.FILL
        mRipplePaint.color = centerRippleColor
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        maxRadius = min(w,h)/2
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            var save = it.save()
            for (st in count..maxRadius step interval) {
                mRipplePaint.alpha = 255 * (maxRadius - st) / maxRadius
                canvas.drawCircle(width / 2.0f, height / 2.0f, st.toFloat(), mRipplePaint)
            }
            it.restoreToCount(save)
            postDelayed(this, 120)
        }

    }

}