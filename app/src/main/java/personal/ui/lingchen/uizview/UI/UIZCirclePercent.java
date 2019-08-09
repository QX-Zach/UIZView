package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import personal.ui.lingchen.uizview.R;

/**
 * @author：zach
 * @Date: 2019-07-15 10:23
 * @Email: xinde.zhang@cftcn.com
 * @ProjectName: UIZView
 * @PackageName: personal.ui.lingchen.uizview.UI
 * @Description:
 */
public class UIZCirclePercent extends View {
    private final int DEFUALT_VIEW_WIDTH = 50;
    private final int DEFUALT_VIEW_HEIGHT = 50;
    private float MAX_PERCENT = 100.f;
    private float gap = 4;
    private Paint mPaint;
    private float centerX, centerY;
    private int circleColor = 0xff00A6F4, strokeColor = 0xff00A6F4, textColor = Color.WHITE;
    private float outRadius, innerRadius;
    private RectF outRect;

    private float valuePer = 50;
    private float targetSweepAngle = 180;

    /**
     * 设置百分比
     *
     * @param percent
     */
    public void setTargetPercent(float percent) {
        valuePer = percent;
        if (percent > MAX_PERCENT) {
            targetSweepAngle = 360.f;
        } else {
            targetSweepAngle = percent / MAX_PERCENT * 360.f;
        }
        invalidate();
    }


    public UIZCirclePercent(Context context) {
        super(context);
        init();
    }

    public UIZCirclePercent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UIZCirclePercent);
        circleColor = typedArray.getColor(R.styleable.UIZCirclePercent_uizCircleColor, 0xff00A6F4);
        textColor = typedArray.getColor(R.styleable.UIZCirclePercent_uizTextColor, Color.WHITE);
        strokeColor = typedArray.getColor(R.styleable.UIZCirclePercent_uizPrecentColor, 0xff00A6F4);
        typedArray.recycle();
        init();
    }

    public UIZCirclePercent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(dpToPx(DEFUALT_VIEW_WIDTH), widthSize);
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = dpToPx(DEFUALT_VIEW_WIDTH);
        } else {
            width = dpToPx(DEFUALT_VIEW_WIDTH);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(dpToPx(DEFUALT_VIEW_HEIGHT), heightSize);
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            height = dpToPx(DEFUALT_VIEW_HEIGHT);
        } else {
            height = dpToPx(DEFUALT_VIEW_HEIGHT);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2.0f;
        centerY = h / 2.0f;
        outRadius = Math.min(centerX, centerY) - dpToPx(3);
        innerRadius = outRadius - dpToPx(gap);
        if (innerRadius < outRadius * 0.8f) {
            innerRadius = outRadius * 0.8f;
        }
        outRect = new RectF();
        outRect.left = centerX - outRadius;
        outRect.top = centerY - outRadius;
        outRect.right = centerX + outRadius;
        outRect.bottom = centerY + outRadius;

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * 绘制外侧百分比圆弧和中心圆
     *
     * @param canvas
     */
    private void drawStrokeAndCircle(Canvas canvas) {
        /**
         * 绘制圆环线
         */

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dpToPx(2f));
        mPaint.setColor(strokeColor);
        canvas.drawArc(outRect, -90, targetSweepAngle, false, mPaint);

        /**
         * 绘制中心圆
         */
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(circleColor);
        canvas.drawCircle(centerX, centerY, innerRadius, mPaint);


        /**
         * 绘制百分比
         */

        mPaint.setTextSize(dpToPx(20));
        mPaint.setColor(textColor);
        String valueText = String.format("%.0f%%", valuePer);
        float textW = mPaint.measureText(valueText);
        float textH = getFontHeight(mPaint);
        canvas.drawText(valueText, centerX - textW / 2, centerY + textH / 3, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStrokeAndCircle(canvas);
    }

    protected int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    //  获取字体高度
    protected int getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }

}
