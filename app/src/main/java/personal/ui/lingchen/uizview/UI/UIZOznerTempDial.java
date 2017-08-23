package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import personal.ui.lingchen.uizview.R;

/**
 * description: UIZOznerTempDial,洗碗机温度UI
 * autour: ozner_67
 * date: 2017/8/22 14:37
 * e-mail: xinde.zhang@cftcn.com
 */

public class UIZOznerTempDial extends UIZBaseView {
    private static final String TAG = "UIZOznerTempDial";
    private static final int START_ANGLE = 130;
    private static final int SWEEP_ANGLE = 280;
    private static final int PAINT_TITLE_SIZE = 25;
    private static final int PAINT_VALUE_SIZE = 50;
    private static final int PAINT_UNIT_SIZE = 25;
    private Paint circlePaint, holdPaint, bgPaint;
    private Paint textPaint;
    private int centerX, centerY;
    private float colorCircleRadius, bgCircleRadius, holdCircleRadius;
    private int[] mCircleColors = new int[]{0xff8cdefa, 0xff14c0fa, 0xff24e5ce, 0xff0df614, 0xfff8c12e, 0xfff6530b, 0xfff6b99f};
    private float[] mcolorPoints = new float[]{0.1f, 0.25f, 0.325f, 0.425f, 0.6f, 0.75f, 0.9f};
    private float lineWidth = 10;//dp
    private int targetValue = 0;
    private int maxValue = 100;
    private Matrix mMatrix = new Matrix();
    private int holdColor = mCircleColors[0];
    private int titleWidth, titleHeight, unitWidth, unitHeight, valueHeight, valueWidth;
    private int titleSize, unitSize, valueSize;
    private String title = "当前温度";
    private String unit = "℃";

    public UIZOznerTempDial(Context context) {
        super(context);
        initView();
    }

    public UIZOznerTempDial(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UIZOznerTempDialStyle);
        title = typedArray.getString(R.styleable.UIZOznerTempDialStyle_uiz_temp_dial_title);
        typedArray.recycle();
        initView();
    }

    public UIZOznerTempDial(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        titleSize = dpToPx(PAINT_TITLE_SIZE);
        unitSize = dpToPx(PAINT_UNIT_SIZE);
        valueSize = dpToPx(PAINT_VALUE_SIZE);
//
        bgPaint = new Paint();
        bgPaint.setColor(0xffffffff);
        bgPaint.setAntiAlias(true);

//        Shader s = new SweepGradient(0, 0, mCircleColors, null);
        //渐变色环初始化
        circlePaint = new Paint();
//        circlePaint.setShader(s);
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeCap(Paint.Cap.ROUND);
        circlePaint.setStyle(Paint.Style.STROKE);

        holdPaint = new Paint();
        holdPaint.setStyle(Paint.Style.FILL);
        holdPaint.setAntiAlias(true);
//        holdPaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.SOLID));


        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bgCircleRadius = Math.min(mWidth / 2, mHeight / 2);
        colorCircleRadius = bgCircleRadius * 0.8f;
        centerX = mWidth / 2;
        centerY = mHeight / 2;
        Log.e(TAG, "onSizeChanged: colorCircleRadius:" + colorCircleRadius + " , lineWidht:" + lineWidth);
        if (lineWidth > colorCircleRadius * 0.05f) {
            lineWidth = colorCircleRadius * 0.05f;
        }
        circlePaint.setStrokeWidth(dpToPx(lineWidth));
        holdCircleRadius = lineWidth * 3f;

        changeTextSize();
        Shader sweepShader = new SweepGradient(centerX, centerY, mCircleColors, mcolorPoints);
        mMatrix.setRotate(90, centerX, centerY);
        sweepShader.setLocalMatrix(mMatrix);
        circlePaint.setShader(sweepShader);
    }

    private void changeTextSize() {
        textPaint.setTextSize(valueSize);
        titleWidth = getStringWidth(textPaint, title);
        while (titleWidth < colorCircleRadius) {
            titleSize++;
            textPaint.setTextSize(titleSize);
            titleWidth = getStringWidth(textPaint, title);
            titleHeight = getFontHeight(textPaint);
        }
        while (titleWidth > colorCircleRadius * 1.1) {
            titleSize--;
            textPaint.setTextSize(titleSize);
            titleWidth = getStringWidth(textPaint, title);
            titleHeight = getFontHeight(textPaint);
        }

        textPaint.setTextSize(valueSize);
        float tempWidth = getStringWidth(textPaint, "100");
        valueHeight = getFontHeight(textPaint);
        while (tempWidth > colorCircleRadius * 1.1f || valueHeight > colorCircleRadius) {
            valueSize--;
            textPaint.setTextSize(valueSize);
            tempWidth = getStringWidth(textPaint, "100");
            valueHeight = getFontHeight(textPaint);
        }

        textPaint.setTextSize(unitSize);
        unitHeight = getFontHeight(textPaint);
        while (unitHeight > valueHeight * 0.5f) {
            unitSize--;
            textPaint.setTextSize(unitSize);
            unitHeight = getFontHeight(textPaint);
        }
        unitWidth = getStringWidth(textPaint, unit);
    }

    private void drawStaticView(Canvas canvas) {
        //绘制背景圆
        canvas.drawCircle(centerX, centerY, bgCircleRadius, bgPaint);

        //绘制色环
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(centerX - colorCircleRadius, centerY - colorCircleRadius, centerX + colorCircleRadius, centerY + colorCircleRadius
                    , START_ANGLE, SWEEP_ANGLE, false, circlePaint);
        } else {
            canvas.drawArc(new RectF(centerX - colorCircleRadius, centerY - colorCircleRadius, centerX + colorCircleRadius, centerY + colorCircleRadius)
                    , START_ANGLE, SWEEP_ANGLE, false, circlePaint);
        }
    }


    /**
     * 绘制圆点
     *
     * @param canvas
     */
    private void drawHoldCircle(Canvas canvas) {
        float degress = START_ANGLE + targetValue * 1f / maxValue * SWEEP_ANGLE;
        double holdX = centerX + colorCircleRadius * Math.cos(degress * Math.PI / 180);
        double holdY = centerY + colorCircleRadius * Math.sin(degress * Math.PI / 180);
        float unit = targetValue * 1f / maxValue;
        holdColor = interpCircleColor(mCircleColors, unit);
        holdPaint.setColor(holdColor);
        holdPaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.SOLID));
        canvas.drawCircle((float) holdX, (float) holdY, holdCircleRadius, holdPaint);
        holdPaint.setColor(0xffffffff);
        holdPaint.setMaskFilter(null);
        canvas.drawCircle((float)holdX,(float)holdY,holdCircleRadius*0.75f,holdPaint);
    }


    private void drawText(Canvas canvas) {
        textPaint.setColor(holdColor);
        //绘制标题
        textPaint.setTextSize(titleSize);
        float titleLeft = centerX - titleWidth / 2;
        float titleTop = centerY - titleHeight;
        canvas.drawText(title, titleLeft, titleTop, textPaint);


        //绘制数字
        textPaint.setTextSize(valueSize);
        valueWidth = getStringWidth(textPaint, String.valueOf(targetValue));
        float valueLeft = centerX - (valueWidth + unitWidth + dpToPx(3)) / 2;
        float valueTop = centerY + valueHeight * 0.5f;
        canvas.drawText(String.valueOf(targetValue), valueLeft, valueTop, textPaint);

        //绘制单位
        textPaint.setTextSize(unitSize);
        float unitLeft = centerX + (valueWidth + unitWidth + dpToPx(3)) / 2 - unitWidth;
        float unitTop = centerY + valueHeight - unitHeight;
        canvas.drawText(unit, unitLeft, unitTop, textPaint);
    }

    /**
     * 获取圆环上颜色
     *
     * @param colors
     * @param unit
     *
     * @return
     */
    protected int interpCircleColor(int colors[], float unit) {
        if (unit <= 0) {
            return colors[0];
        }
        if (unit >= 1) {
            return colors[colors.length - 1];
        }

        float p = unit * (colors.length - 1);
        int i = (int) p;
        p -= i;

        // now p is just the fractional part [0...1) and i is the index
        int c0 = colors[i];
        int c1 = colors[i + 1];
        int a = ave(Color.alpha(c0), Color.alpha(c1), p);
        int r = ave(Color.red(c0), Color.red(c1), p);
        int g = ave(Color.green(c0), Color.green(c1), p);
        int b = ave(Color.blue(c0), Color.blue(c1), p);

        return Color.argb(a, r, g, b);
    }

    protected int ave(int s, int d, float p) {
        return s + Math.round(p * (d - s));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStaticView(canvas);
        drawHoldCircle(canvas);
        drawText(canvas);
    }

    public void setMax(int max) {
        this.maxValue = max;
    }

    public void setValue(int value) {
        this.targetValue = value;
        postInvalidate();
    }
}
