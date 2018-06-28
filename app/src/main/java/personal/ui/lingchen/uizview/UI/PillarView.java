package personal.ui.lingchen.uizview.UI;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import personal.ui.lingchen.uizview.R;

/**
 * author：zach
 * date: 2018/6/26 18:37
 * ProjectName: UIZView
 * PackageName: personal.ui.lingchen.uizview.UI
 * Description: 柱状百分比View
 */
public class PillarView extends View {
    private Paint bgPaint, fontPaint;
    private float circleR;//上下半圆半径
    private int bgColor = 0xffB1ECFE;//背景色
    private int fontColor = 0xff63d7fc;//前景色
    private int boundColor = Color.RED;//边框颜色
    private int currentValue = 0;//当前百分比值
    private int targetValue = 0;//目标值
    private int MAX_VALUE = 100;//默认最大值
    private Path backPath;//前景色前后两个图形路径
    private int ANIMATOR_DURATION = 1000;
    private int offSet = dpToPx(1);
    private RectF canRect;
    private boolean isShowBound = false;//是否显示边框

    private int mWidth, mHeight;

    public PillarView(Context context) {
        super(context);
        init();
    }

    public PillarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PillarView);
        bgColor = typedArray.getColor(R.styleable.PillarView_uiz_background_color, 0xffB1ECFE);
        fontColor = typedArray.getColor(R.styleable.PillarView_uiz_front_color, 0xff63d7fc);
        currentValue = typedArray.getInt(R.styleable.PillarView_uiz_value, 0);
        isShowBound = typedArray.getBoolean(R.styleable.PillarView_uiz_show_bound, false);
        boundColor = typedArray.getColor(R.styleable.PillarView_uiz_bound_color, Color.RED);
        if (currentValue > MAX_VALUE) {
            currentValue = MAX_VALUE;
        }
        typedArray.recycle();
        init();
    }

    public PillarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mHeight = h;
        this.mWidth = w;
        canRect = new RectF();
        canRect.left = offSet;
        canRect.top = offSet;
        canRect.right = mWidth - offSet;
        canRect.bottom = mHeight - offSet;
        this.circleR = Math.min(canRect.width(), canRect.height()) / 2;
        initBgPath();
//        fontPaint.setShader(new LinearGradient(0, 0, mWidth, 0, new int[]{Color.RED, Color.GREEN}, null, Shader.TileMode.REPEAT));
    }

    /**
     * 初始化变量
     */
    private void init() {
        backPath = new Path();

        fontPaint = new Paint();
        fontPaint.setColor(fontColor);
        fontPaint.setDither(true);
        fontPaint.setAntiAlias(true);
        fontPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setDither(true);
        bgPaint.setColor(bgColor);
        bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    private void initBgPath() {
        backPath.moveTo(mWidth / 2 - circleR, canRect.left + circleR);
        backPath.arcTo(new RectF(mWidth / 2 - circleR, canRect.top, mWidth / 2 + circleR, canRect.top + circleR * 2), -180, 180);
        backPath.lineTo(canRect.right, canRect.bottom - circleR);
        backPath.arcTo(new RectF(mWidth / 2 - circleR, canRect.bottom - circleR * 2, mWidth / 2 + circleR, canRect.bottom), 0, 180);
        backPath.close();
    }

    public void setValue(int value) {
        this.targetValue = value;
        if (targetValue > MAX_VALUE) {
            targetValue = MAX_VALUE;
        }
//        postInvalidate();
        startAnimator();
    }

    private void startAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(currentValue, targetValue);
        valueAnimator.setDuration(Math.max((int) (Math.abs(targetValue - currentValue) * 1.0f * ANIMATOR_DURATION / 100), 200));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentValue = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    /**
     * 绘制背景
     *
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        bgPaint.setColor(bgColor);
        bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(backPath, bgPaint);
    }

    /**
     * 绘制边框颜色
     *
     * @param canvas
     */
    private void drawBound(Canvas canvas) {
        bgPaint.setColor(boundColor);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(dpToPx(3));
        canvas.drawPath(backPath, bgPaint);
    }


    private void drawValue(Canvas canvas) {
        int currHeight = (int) ((currentValue * 1.0f / MAX_VALUE) * canRect.height());
        if (currHeight < circleR) {
            // TODO: 2018/6/27 下方半圆内
            double radian = Math.acos((circleR - currHeight) * 1.0f / circleR);
            float angle = (float) (radian / Math.PI * 180);
            canvas.drawArc(new RectF(mWidth / 2 - circleR, canRect.bottom - circleR * 2, mWidth / 2 + circleR, canRect.bottom), 90 - angle, angle * 2, false, fontPaint);
        } else if (currHeight < canRect.height() - circleR) {
            // TODO: 2018/6/27 中间矩形内
            canvas.drawArc(new RectF(mWidth / 2 - circleR, canRect.bottom - circleR * 2, mWidth / 2 + circleR, canRect.bottom), 0, 180, false, fontPaint);

            canvas.drawRect(new RectF(mWidth / 2 - circleR, canRect.bottom - currHeight, mWidth / 2 + circleR, canRect.bottom - circleR), fontPaint);
        } else {
            // TODO: 2018/6/27 上方半圆内
            canvas.drawArc(new RectF(mWidth / 2 - circleR, canRect.bottom - circleR * 2, mWidth / 2 + circleR, canRect.bottom), 0, 180, false, fontPaint);

            canvas.drawRect(new RectF(mWidth / 2 - circleR, canRect.top + circleR, mWidth / 2 + circleR, canRect.bottom - circleR), fontPaint);
            canvas.drawCircle(mWidth / 2, canRect.top + circleR, circleR, fontPaint);

            double radian = Math.acos((circleR - (canRect.height() - currHeight)) * 1.0f / circleR);
            float angle = (float) (radian / Math.PI * 180);
            canvas.drawArc(new RectF(mWidth / 2 - circleR, canRect.top, mWidth / 2 + circleR, canRect.top + circleR * 2), -90 - angle, angle * 2, false, bgPaint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawValue(canvas);
        if (isShowBound) {
            drawBound(canvas);
        }
    }

    protected int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}
