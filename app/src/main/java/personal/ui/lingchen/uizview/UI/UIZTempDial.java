package personal.ui.lingchen.uizview.UI;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.HashMap;

import personal.ui.lingchen.uizview.R;
import personal.ui.lingchen.uizview.Utils.OznerColorUtils;

/**
 * Created by ozner_67 on 2018/1/15.
 * 邮箱：xinde.zhang@cftcn.com
 * <p>
 * 用于三出水主界面UI
 */

public class UIZTempDial extends UIZBaseView {
    private static final String TAG = "UIZTempDial";
    private static final int FIRST_COLOR = 0xff415cec;
    private static final int SECOND_COLOR = 0xff6f6fef;
    private static final int[] OTHER_COLORS = new int[]{0xffe985f7, 0xfff0c170, 0xffec4141};
    private static final int TEXT_COLOR = 0xff7493ff;
    private static final int START_ANGLE = 120;//起始角度
    private static final int SWEEP_ANGLE = 300;//扫过角度
    private static final int ANGLE_STEP = 5;
    private static final int UINIT_SIZE = 22;//dp
    private static final int TEXT_SIZE = 22;//DP
    private static final int NUM_SIZE = 65;//
    private int maxNum = SWEEP_ANGLE / ANGLE_STEP;//线条个数
    private int circleColor = Color.CYAN;//中间圆颜色
    private int textColor = TEXT_COLOR;//文本颜色
    private Paint linePaint, circlePaint, textPaint;
    private float circleRadius;//圆半径
    private float unitSize = UINIT_SIZE;//dp
    private float textSize = TEXT_SIZE;//dp
    private float numSize = NUM_SIZE;//dp
    private float lineLength;
    private float outDrawRadius;
    private double triHalfAngle = 5 * Math.PI / 180.f;//三角底边两个点与圆心组成夹角的一半
    private float triOutLenght = 15;//三角顶点超过中心圆半径长度
    private Path triPath;
    private int targetProgress = 0;
    private float curProgress = 0;
    private int minValue = 0;
    private int maxValue = 100;
    private int curValue = 0, targetValue = 0;
    private HashMap<Integer, Integer> colorMap = new HashMap<>();
    private ValueAnimator textAnimator, progressAnimator;
    private AnimatorSet animatorSet;

    public UIZTempDial(Context context) {
        super(context);
        initPaint();
    }

    public UIZTempDial(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.UIZTempDial);
        circleColor = array.getColor(R.styleable.UIZTempDial_uiztemp_dial_bg_color, Color.CYAN);
        textColor = array.getColor(R.styleable.UIZTempDial_uiztemp_dial_text_color, TEXT_COLOR);
        minValue = array.getInt(R.styleable.UIZTempDial_uiztemp_dial_value_min, 0);
        maxValue = array.getInt(R.styleable.UIZTempDial_uiztemp_dial_value_max, 100);
        array.recycle();
        initPaint();
    }

    public UIZTempDial(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        animatorSet = new AnimatorSet();
        animatorSet.setDuration(600);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeCap(Paint.Cap.ROUND);

        linePaint.setColor(Color.BLUE);

        circlePaint = new Paint(linePaint);
        circlePaint.setColor(circleColor);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        outDrawRadius = Math.min(w, h) / 2 - dpToPx(5);
        circleRadius = outDrawRadius * 0.8f;
        linePaint.setStrokeWidth(circleRadius * 0.03f);
        lineLength = (outDrawRadius - circleRadius) * 0.5f;
        triOutLenght = lineLength * 0.7f;
        targetValue = minValue;
        curValue = targetValue;
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(mWidth / 2, mHeight / 2, circleRadius, circlePaint);
    }

    private void drawDial(Canvas canvas) {

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.rotate(START_ANGLE - 90);
//        int maxNum = SWEEP_ANGLE / ANGLE_STEP;
        for (int i = 0; i <= maxNum; i++) {
            if (!colorMap.containsKey(i)) {
                colorMap.put(i, OznerColorUtils.caculateColor(i * 100.f / maxNum, FIRST_COLOR, SECOND_COLOR, OTHER_COLORS));
            }
            linePaint.setColor(colorMap.get(i));
//            linePaint.setColor(OznerColorUtils.caculateColor(i * 100 / deno, FIRST_COLOR, SECOND_COLOR, OTHER_COLORS));
            if (i % 2 == 1) {
                canvas.drawLine(0, outDrawRadius - lineLength / 3, 0, outDrawRadius - lineLength, linePaint);
            } else {
                canvas.drawLine(0, outDrawRadius, 0, outDrawRadius - lineLength, linePaint);
            }
            canvas.rotate(ANGLE_STEP);
        }

        canvas.restore();
    }

    /**
     * 绘制三角形指示器
     *
     * @param canvas
     * @param curPer 三角指示器旋转百分比，最大值100
     */
    private void drawTriangle(Canvas canvas, float curPer) {
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.rotate(curPer * SWEEP_ANGLE / 100.f + START_ANGLE - 90);
        if (triPath == null) {
            triPath = new Path();
            triPath.moveTo(0, circleRadius + triOutLenght);
            triPath.lineTo(-(float) Math.sin(triHalfAngle) * circleRadius, (float) Math.cos(triHalfAngle) * circleRadius);
            triPath.lineTo((float) Math.sin(triHalfAngle) * circleRadius, (float) Math.cos(triHalfAngle) * circleRadius);
            triPath.close();
        }
        canvas.drawPath(triPath, circlePaint);
        canvas.restore();
    }

    /**
     * 绘制值
     *
     * @param canvas
     * @param value
     */
    private void drawValue(Canvas canvas, int value) {
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        float offset = circleRadius * 0.1f;
        textPaint.setTextSize(dpToPx(unitSize));
        String unit = "℃";
        int unitLenght = getStringWidth(textPaint, unit);
        int unitHeight = getFontHeight(textPaint);
        textPaint.setTextSize(dpToPx(numSize));
        String text = String.valueOf(value);
        int textLenght = getStringWidth(textPaint, text);
        int textHeight = getFontHeight(textPaint);
        textPaint.setTextSize(dpToPx(unitSize));
        canvas.drawText(unit, (textLenght - unitLenght) / 2, 1.7f * unitHeight - textHeight + offset, textPaint);
        textPaint.setTextSize(dpToPx(numSize));
        canvas.drawText(text, -(textLenght + unitLenght) / 2, offset, textPaint);
        String decText = "当前温度";
        textPaint.setTextSize(dpToPx(textSize));
        int decLenght = getStringWidth(textPaint, decText);
        int decHeight = getFontHeight(textPaint);
        canvas.drawText(decText, -decLenght / 2, decHeight + offset, textPaint);
        canvas.restore();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        drawDial(canvas);
        drawTriangle(canvas, curProgress);
        drawValue(canvas, curValue);
    }

    private boolean isShowAnimator = true;

    public void showAnimator(boolean on) {
        isShowAnimator = on;
    }

    /**
     * 设置范围
     *
     * @param minValue
     * @param maxValue
     */
    private void setValueRange(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public void setValue(int value) {
        if (value > maxValue) {
            value = maxValue;
        }
        if (value < minValue) {
            value = minValue;
        }
        targetValue = value;
        targetProgress = (targetValue - minValue) * 100 / (maxValue - minValue);
//        setProgress(per);

        if (isShowAnimator) {
            if (animatorSet.isRunning()) {
                animatorSet.end();
                animatorSet.cancel();
            }
            textAnimator = ValueAnimator.ofInt(curValue, targetValue);
            textAnimator.addUpdateListener(new TextAnimatorListener());
            progressAnimator = ValueAnimator.ofFloat(curProgress, targetProgress);
            progressAnimator.addUpdateListener(new ProgressAnimatorListener());
            animatorSet.playTogether(textAnimator, progressAnimator);
            animatorSet.start();
        } else {
            curProgress = targetProgress;
            curValue = targetValue;
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animatorSet != null && animatorSet.isRunning()) {
            animatorSet.end();
            animatorSet.cancel();
        }
    }

    class TextAnimatorListener implements ValueAnimator.AnimatorUpdateListener {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            curValue = (int) animation.getAnimatedValue();
            postInvalidate();
        }
    }

    class ProgressAnimatorListener implements ValueAnimator.AnimatorUpdateListener {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            curProgress = (float) animation.getAnimatedValue();
            postInvalidate();
        }
    }
}
