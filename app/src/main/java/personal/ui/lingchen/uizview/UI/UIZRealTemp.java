package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;

/**
 * Created by ozner_67 on 2017/9/4.
 * 邮箱：xinde.zhang@cftcn.com
 * 浩泽智能保温杯实时水温UI
 */

public class UIZRealTemp extends UIZBaseView {
    private static final String TAG = "UIZRealTemp";
    private static final int START_ANGLE = 185;
    private static final int SWEEP_ANGLE = 170;
    private static final int GrayColor = 0xff808080;
    private Paint linePaint, valuePaint, textPaint;
    private int targetTemp = 0;
    private int tempPrecent = 0;
    private int lineWidth = dpToPx(8);
    private int lineColor = 0xffe1f7fe, textColor = 0xffffffff, valueColor = 0xffffac28;
    private int circleX, circleY, circleRadius;
    private int valueTextSize = dpToPx(45), titleSize = dpToPx(13), bottomSize = dpToPx(15);
    private int titleHight = 0, valueHight = 0, bottomHeight = 0;
    private Paint.FontMetrics titleMetrics, valueMetrics, bottomMetrics;
    private String bottomText = "适中";
    private String titleText = "水温";

    public UIZRealTemp(Context context) {
        super(context);
        initView();
    }

    public UIZRealTemp(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public UIZRealTemp(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(lineWidth);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setColor(lineColor);
        linePaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setColor(valueColor);
        valuePaint.setStyle(Paint.Style.FILL);


        textPaint.setTextSize(titleSize);
        titleMetrics = textPaint.getFontMetrics();
        titleHight = getFontHeight(textPaint);
        textPaint.setTextSize(bottomSize);
        bottomMetrics = textPaint.getFontMetrics();
        bottomHeight = getFontHeight(textPaint);
        valuePaint.setTextSize(valueTextSize);
        valueMetrics = valuePaint.getFontMetrics();
        Rect valueRect = new Rect();
        valuePaint.getTextBounds("100", 0, "100".length(), valueRect);
        valueHight = valueRect.height();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        circleRadius = Math.min(mWidth / 2, mHeight) - lineWidth * 2;
        circleX = mWidth / 2;
        circleY = mHeight;

    }

    private void drawCircle(Canvas canvas) {
        linePaint.setMaskFilter(null);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(lineColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(circleX - circleRadius, circleY - circleRadius, circleX + circleRadius, circleY + circleRadius
                    , START_ANGLE, SWEEP_ANGLE, false, linePaint);
        } else {
            canvas.drawArc(new RectF(circleX - circleRadius, circleY - circleRadius, circleX + circleRadius, circleY + circleRadius)
                    , START_ANGLE, SWEEP_ANGLE, false, linePaint);
        }
    }

    private void drawHoldCircle(Canvas canvas, int precent) {
        double degree = START_ANGLE + precent * SWEEP_ANGLE / 100.f;
        float x = (float) (circleX + circleRadius * Math.cos(degree * Math.PI / 180));
        float y = (float) (circleY + circleRadius * Math.sin(degree * Math.PI / 180));
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(GrayColor);
        linePaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
        canvas.drawCircle(x, y, lineWidth * 1.1f, linePaint);
        linePaint.setColor(lineColor);
        linePaint.setMaskFilter(null);
        canvas.drawCircle(x, y, lineWidth * 1.1f, linePaint);
    }

    /**
     * 绘制静态文本
     *
     * @param canvas
     */
    private void drawStaticText(Canvas canvas) {
        textPaint.setTextSize(bottomSize);
        int bottomWidth = getStringWidth(textPaint, bottomText);
        canvas.drawText(bottomText, circleX - bottomWidth / 2, circleY - bottomMetrics.bottom + bottomMetrics.leading, textPaint);

        textPaint.setTextSize(titleSize);
        int titleWidth = getStringWidth(textPaint, titleText);
        canvas.drawText(titleText, circleX - titleWidth / 2, circleY - bottomHeight - valueHight - titleHight - titleMetrics.bottom, textPaint);
    }

    /**
     * 绘制温度值
     *
     * @param canvas
     * @param value
     */
    private void drawValue(Canvas canvas, int value) {
        valuePaint.setTextSize(valueTextSize);
        int valueWidth = getStringWidth(valuePaint, String.valueOf(value));
        int offset = dpToPx(2);
        int tempCircleRadius = dpToPx(3);
        valuePaint.setStyle(Paint.Style.FILL);
        canvas.drawText(String.valueOf(value), circleX - valueWidth / 2, circleY - bottomHeight - valueMetrics.bottom + valueMetrics.leading, valuePaint);
        //绘制度的小圈
        valuePaint.setStyle(Paint.Style.STROKE);
        valuePaint.setStrokeWidth(dpToPx(2));
        int left = circleX + valueWidth / 2 + offset;
        int top = (int) (circleY - bottomHeight - valueMetrics.bottom + valueMetrics.ascent + dpToPx(8));
        int right = left + 2 * tempCircleRadius;
        int bottom = top + 2 * tempCircleRadius;
        int cx = (left + right) / 2;
        int cy = (top + bottom) / 2;

        canvas.drawCircle(cx, cy, tempCircleRadius, valuePaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        drawHoldCircle(canvas, tempPrecent);
        drawStaticText(canvas);
        drawValue(canvas, targetTemp);
    }

    public void setTemp(int temp) {
        if (temp < 0) {
            temp = 0;
        }
        if (temp > 100) {
            temp = 100;
        }
        this.targetTemp = temp;
        postInvalidate();
    }

    public void setPrecent(int precent) {
        if (precent < 0) {
            precent = 0;
        }
        if (precent > 100) {
            precent = 100;
        }
        this.tempPrecent = precent;
        invalidate();
    }

    public void setTempDesc(String desc) {
        this.bottomText = desc;
        postInvalidate();
    }

    public void setTempDesc(@StringRes int resId) {
        this.bottomText = getContext().getString(resId);
        postInvalidate();
    }
}
