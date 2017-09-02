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
    private String title;//, unit;
    private int titleWidth, titleHeight, unitHourWidth, unitMinuteWidth, unitHeight, valueWidth, valueHeight;

    private int targetProcess = 0;
    private int remainHour = 0;
    private int remainMinute = 0;

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

        titlePaint.setTextSize(titleSize);
        titleWidth = getStringWidth(titlePaint, title);
        titleHeight = getFontHeight(titlePaint);
        unitPaint.setTextSize(unitSize);
        unitHeight = getFontHeight(unitPaint);
        unitMinuteWidth = getStringWidth(unitPaint, "min");
        textPaint.setTextSize(valueSize);

        targetProcess = (remainHour * 60 + remainMinute) * 100 / processMax;
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

    private void drawProcessLine(Canvas canvas, int value) {
        linePaint.setColor(processLineColor);
        linePaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
        if (value > 100) {
            value = 100;
        }
        float sweep = value / 100 * SWEEP_ANGLE;
        //由于魅族M3s和Oppo等手机在sweep为0时会造成绘制完整圆圈，
        // 故这里判断为0的情况，设置一个大于零又比较小的初值，显示效果没有明显影响
        if (value == 0)
            sweep = 0.01f;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(centerX - outCirlceRadius * 0.875f, centerY - outCirlceRadius * 0.875f
                    , centerX + outCirlceRadius * 0.875f, centerY + outCirlceRadius * 0.875f, START_ANGLE, sweep, false, linePaint);
        } else {
            canvas.drawArc(new RectF(centerX - outCirlceRadius * 0.875f, centerY - outCirlceRadius * 0.875f
                    , centerX + outCirlceRadius * 0.875f, centerY + outCirlceRadius * 0.875f), START_ANGLE, sweep, false, linePaint);
        }
    }

    private void changeTextSize() {
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


        while (unitMinuteWidth < outCirlceRadius * 0.15f) {
            unitSize++;
            unitPaint.setTextSize(unitSize);
            unitMinuteWidth = getStringWidth(unitPaint, "min");
            unitHeight = getFontHeight(unitPaint);
        }

        while (unitMinuteWidth > outCirlceRadius * 0.2f) {
            unitSize--;
            unitPaint.setTextSize(unitSize);
            unitMinuteWidth = getStringWidth(unitPaint, "min");
            unitHeight = getFontHeight(unitPaint);
        }
        unitHourWidth = getStringWidth(unitPaint, "h");
    }

    /**
     * 绘制静态文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {

        titlePaint.setColor(titleColor);
        float titleLetf = centerX - titleWidth / 2;
        float titleTop = centerY - titleHeight;
        canvas.drawText(title, titleLetf, titleTop, titlePaint);

        float hourWidth = 0;
        float minuteWidth = 0;
        String hourStr = String.valueOf(remainHour);
        String minuteStr = String.valueOf(remainMinute);
        float tempValueWidth = getStringWidth(textPaint, "0000");

        while (tempValueWidth + unitHourWidth + unitMinuteWidth > inCircleRadius * 1.8f) {
            valueSize--;
            textPaint.setTextSize(valueSize);
            tempValueWidth = getStringWidth(textPaint, "0000");
        }
        if (remainHour > 0 && remainMinute < 10) {
            minuteStr = "0" + minuteStr;
        }
        valueHeight = getFontHeight(textPaint);
        minuteWidth = getStringWidth(textPaint, minuteStr);
        hourWidth = getStringWidth(textPaint, hourStr);

        float textWidth = hourWidth + minuteWidth + unitHourWidth + unitMinuteWidth + 3 * dpToPx(1);
        float tempHourUnitWidth = 0;

        textPaint.setColor(valueColor);
        unitPaint.setColor(unitColor);
        if (remainHour > 0) {
            tempHourUnitWidth = unitHourWidth;
            //绘制小时
            canvas.drawText(hourStr, centerX - textWidth / 2, centerY + valueHeight*0.5f, textPaint);
            //绘制小时单位
            canvas.drawText("h", centerX - textWidth / 2 + dpToPx(1) + hourWidth, centerY + valueHeight*0.5f, unitPaint);
        }

        canvas.drawText(minuteStr, centerX - textWidth / 2 + dpToPx(2) + hourWidth + tempHourUnitWidth,
                        centerY + valueHeight*0.5f, textPaint);
        canvas.drawText("min", centerX - textWidth / 2 + dpToPx(3) + hourWidth + tempHourUnitWidth + minuteWidth,
                        centerY + valueHeight*0.5f, unitPaint);
    }

    /**
     * 绘制数字
     *
     * @param canvas
     * @param value
     */
    private void drawValueText(Canvas canvas, String value) {

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
        drawText(canvas);
//        drawValueText(canvas, String.valueOf(remainHour * 60 + remainMinute));
        drawProcessLine(canvas, targetProcess);
    }


    /**
     * 设置数据
     *
     * @param hour
     * @param minute
     */
    public void setRemainTime(int hour, int minute) {
        if (hour < 0 || minute < 0) {
            return;
        }
        this.remainHour = hour;
        this.remainMinute = minute;
        this.targetProcess = (remainHour * 60 + remainMinute) * 100 / processMax;
        postInvalidate();
    }

    /**
     * 设置进度最大值
     *
     * @param maxMinute
     */
    public void setProcessMax(int maxMinute) {
        this.processMax = maxMinute;
    }
}
