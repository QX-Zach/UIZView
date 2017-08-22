package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import personal.ui.lingchen.uizview.R;

/**
 * Created by ozner_67 on 2017/8/18.
 * 邮箱：xinde.zhang@cftcn.com
 */

public class UIZTimeRemain extends UIZBaseView {
    private static final String TAG = "UIZTimeRemain";
    private static final int START_ANGLE = 105;
    private static final int SWEEP_ANGLE = 330;
    private static final int PAINT_TITLE_SIZE = 23;
    private static final int PAINT_VALUE_SIZE = 50;
    private static final int PAINT_UNIT_SIZE = 12;

    private int bgLineColor, processLineColor, outCircleColor, middleCircleColor, inCircleColor;
    private int titleColor, valueColor, unitColor;
    private float lineWidth;
    private float titleSize, valueSize, unitSize;

    private Paint circlePaint, linePaint, textPaint;
    private Paint titlePaint, unitPaint;
    private float outCirlceRadius, middleCircleRadius, inCircleRadius;
    private float centerX, centerY;
    private String title, unit;
    private int titleWidth, titleHeight, unitWidth, unitHeight, valueWidth, valueHeight;

    private int targetProcess = 0;
    private int targetValue = 0;

    private int processMax = 100;

    public UIZTimeRemain(Context context) {
        super(context);
        initView();
    }

    public UIZTimeRemain(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UIZTimeRemainStyle);
        bgLineColor = typedArray.getColor(R.styleable.UIZTimeRemainStyle_uiz_line_bgColor, 0xff2f2b3e);
        processLineColor = typedArray.getColor(R.styleable.UIZTimeRemainStyle_uiz_line_processColor, 0xfffe8100);
        outCircleColor = typedArray.getColor(R.styleable.UIZTimeRemainStyle_uiz_out_circleColor, 0xff68627c);
        middleCircleColor = typedArray.getColor(R.styleable.UIZTimeRemainStyle_uiz_middle_circleColor, 0xffe3e4ed);
        inCircleColor = typedArray.getColor(R.styleable.UIZTimeRemainStyle_uiz_in_circleColor, 0xffffffff);
        titleColor = typedArray.getColor(R.styleable.UIZTimeRemainStyle_uiz_titleColor, 0xff999999);
        valueColor = typedArray.getColor(R.styleable.UIZTimeRemainStyle_uiz_valueColor, 0xff999999);
        unitColor = typedArray.getColor(R.styleable.UIZTimeRemainStyle_uiz_unitColor, 0xff999999);
        lineWidth = typedArray.getDimension(R.styleable.UIZTextImageSytle_uiz_text_width, 3);
        title = typedArray.getString(R.styleable.UIZTimeRemainStyle_uiz_title);
        unit = typedArray.getString(R.styleable.UIZTimeRemainStyle_uiz_unit);
        titleSize = dpToPx(PAINT_TITLE_SIZE);
        unitSize = dpToPx(PAINT_UNIT_SIZE);
        valueSize = dpToPx(PAINT_VALUE_SIZE);
        typedArray.recycle();

        initView();
    }

    public UIZTimeRemain(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        initPaint();
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        if (title == null || title.isEmpty()) {
            title = "预计剩余时间";
        }
        if (unit == null || unit.isEmpty()) {
            unit = "min";
        }

        titlePaint.setTextSize(titleSize);
        titleWidth = getStringWidth(titlePaint, title);
        titleHeight = getFontHeight(titlePaint);
        unitPaint.setTextSize(unitSize);
        unitWidth = getStringWidth(unitPaint, unit);
        unitHeight = getFontHeight(unitPaint);
        textPaint.setTextSize(valueSize);

        targetProcess = targetValue * 100 / processMax;
    }

    private void initPaint() {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setTextSize(valueSize);

        titlePaint = new Paint();
        titlePaint.setAntiAlias(true);
        titlePaint.setStyle(Paint.Style.FILL);
        titlePaint.setStrokeCap(Paint.Cap.ROUND);

        unitPaint = new Paint();
        unitPaint.setAntiAlias(true);
        unitPaint.setStyle(Paint.Style.FILL);
        unitPaint.setStrokeCap(Paint.Cap.ROUND);

        linePaint = new Paint();
        linePaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(dpToPx(lineWidth));
        linePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        outCirlceRadius = Math.min(mWidth / 2, mHeight / 2);
        middleCircleRadius = outCirlceRadius * 0.75f;
        inCircleRadius = outCirlceRadius * 0.6f;
        centerX = mWidth / 2;
        centerY = mHeight / 2;

        //修正最大和最小线宽
        float width = dpToPx(lineWidth);
        if (width > outCirlceRadius * 0.03f) {
            width = outCirlceRadius * 0.03f;
        }
        if (width < 2) {
            width = 2;
        }
        linePaint.setStrokeWidth(width);

        changeTextSize();
    }

    /**
     * 绘制背景圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        circlePaint.setMaskFilter(null);
        circlePaint.setColor(outCircleColor);
        canvas.drawCircle(centerX, centerY, outCirlceRadius, circlePaint);
        circlePaint.setColor(middleCircleColor);
        canvas.drawCircle(centerX, centerY, (int) middleCircleRadius, circlePaint);
        circlePaint.setColor(inCircleColor);
        circlePaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
        canvas.drawCircle(centerX, centerY, (int) inCircleRadius, circlePaint);
    }

    private void drawBgLine(Canvas canvas) {
        linePaint.setColor(bgLineColor);
        linePaint.setMaskFilter(null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(centerX - outCirlceRadius * 0.875f, centerY - outCirlceRadius * 0.875f, centerX + outCirlceRadius * 0.875f, centerY + outCirlceRadius * 0.875f, START_ANGLE, SWEEP_ANGLE, false, linePaint);
        } else {
            canvas.drawArc(new RectF(centerX - outCirlceRadius * 0.875f, centerY - outCirlceRadius * 0.875f, centerX + outCirlceRadius * 0.875f, centerY + outCirlceRadius * 0.875f), START_ANGLE, SWEEP_ANGLE, false, linePaint);
        }
    }

    private void drawProcessLine(Canvas canvas, float value) {
        linePaint.setColor(processLineColor);
        linePaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
        if (value > 100) {
            value = 100;
        }
        float sweep = value / 100 * SWEEP_ANGLE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(centerX - outCirlceRadius * 0.875f, centerY - outCirlceRadius * 0.875f, centerX + outCirlceRadius * 0.875f, centerY + outCirlceRadius * 0.875f, START_ANGLE, sweep, false, linePaint);
        } else {
            canvas.drawArc(new RectF(centerX - outCirlceRadius * 0.875f, centerY - outCirlceRadius * 0.875f, centerX + outCirlceRadius * 0.875f, centerY + outCirlceRadius * 0.875f), START_ANGLE, sweep, false, linePaint);
        }
    }

    private void changeTextSize(){
        //绘制标题
        //标题宽度大于内圆直径的0.8倍,缩小字号
        while (titleWidth > outCirlceRadius * 0.9f) {
            titleSize--;
            titlePaint.setTextSize(titleSize);
            titleWidth = getStringWidth(titlePaint, title);
            titleHeight = getFontHeight(titlePaint);
        }
        while (titleWidth < outCirlceRadius * 0.8f) {
            titleSize++;
            titlePaint.setTextSize(titleSize);
            titleWidth = getStringWidth(titlePaint, title);
            titleHeight = getFontHeight(titlePaint);
        }


        //绘制单位
        while (unitWidth < outCirlceRadius * 0.15f) {
            unitSize++;
            unitPaint.setTextSize(unitSize);
            unitWidth = getStringWidth(unitPaint, unit);
            unitHeight = getFontHeight(unitPaint);
        }

        while (unitWidth > outCirlceRadius * 0.2f) {
            unitSize--;
            unitPaint.setTextSize(unitSize);
            unitWidth = getStringWidth(unitPaint, unit);
            unitHeight = getFontHeight(unitPaint);
        }


        float tempWidth = getStringWidth(textPaint, "100");
        valueHeight = getFontHeight(textPaint);
        while (tempWidth > inCircleRadius * 1.2f || valueHeight > inCircleRadius * 1.1f) {
            valueSize--;
            textPaint.setTextSize(valueSize);
            tempWidth = getStringWidth(textPaint, "100");
            valueHeight = getFontHeight(textPaint);
        }


    }

    /**
     * 绘制静态文字
     *
     * @param canvas
     */
    private void drawStaticText(Canvas canvas) {
//        //绘制标题
//        //标题宽度大于内圆直径的0.8倍,缩小字号
//        while (titleWidth > outCirlceRadius * 0.9f) {
//            titleSize--;
//            titlePaint.setTextSize(titleSize);
//            titleWidth = getStringWidth(titlePaint, title);
//            titleHeight = getFontHeight(titlePaint);
//        }
//        while (titleWidth < outCirlceRadius * 0.8f) {
//            titleSize++;
//            titlePaint.setTextSize(titleSize);
//            titleWidth = getStringWidth(titlePaint, title);
//            titleHeight = getFontHeight(titlePaint);
//        }

        titlePaint.setColor(titleColor);
        float titleLetf = centerX - titleWidth / 2;
        float titleTop = centerY - titleHeight;
        canvas.drawText(title, titleLetf, titleTop, titlePaint);

//        //绘制单位
//        while (unitWidth < outCirlceRadius * 0.15f) {
//            unitSize++;
//            unitPaint.setTextSize(unitSize);
//            unitWidth = getStringWidth(unitPaint, unit);
//            unitHeight = getFontHeight(unitPaint);
//        }
//
//        while (unitWidth > outCirlceRadius * 0.2f) {
//            unitSize--;
//            unitPaint.setTextSize(unitSize);
//            unitWidth = getStringWidth(unitPaint, unit);
//            unitHeight = getFontHeight(unitPaint);
//        }

        unitPaint.setColor(unitColor);
        float unitLeft = centerX + inCircleRadius * 0.75f - unitWidth;
        float unitTop = centerY + inCircleRadius * 0.7f - unitHeight;
        canvas.drawText(unit, unitLeft, unitTop, unitPaint);
    }

    /**
     * 绘制数字
     *
     * @param canvas
     * @param value
     */
    private void drawValueText(Canvas canvas, String value) {

//        float tempWidth = getStringWidth(textPaint, "100");
//        valueHeight = getFontHeight(textPaint);
//        while (tempWidth > inCircleRadius * 1.2f || valueHeight > inCircleRadius * 1.1f) {
//            valueSize--;
//            textPaint.setTextSize(valueSize);
//            tempWidth = getStringWidth(textPaint, "100");
//            valueHeight = getFontHeight(textPaint);
//        }

        valueWidth = getStringWidth(textPaint, value);

        textPaint.setColor(valueColor);
        float valueLeft = centerX - valueWidth * 0.65f;
        float valueTop = centerY + valueHeight * 0.5f;
        canvas.drawText(value, valueLeft, valueTop, textPaint);
    }


    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        drawBgLine(canvas);
        drawStaticText(canvas);
        drawValueText(canvas, String.valueOf(targetValue));
        drawProcessLine(canvas, targetProcess);
    }


    /**
     * 设置数据
     *
     * @param value
     */
    public void setValue(int value) {
        this.targetValue = value;
        this.targetProcess = value * 100 / processMax;
        postInvalidate();
    }

    /**
     * 设置进度最大值
     *
     * @param max
     */
    public void setProcessMax(int max) {
        this.processMax = max;
    }
}
