package personal.ui.lingchen.uizview.UI;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

/**
 * Created by ozner_67 on 2016/9/9.
 */
public class OznerDialView extends CustomerBaseView {
    private final int COLOR_LINE_BASE = 0xffffffff;//线条颜色
    private final int TRANS_ALPHA = 0x50;
    private final float LINE_WIDTH_DP = 1.5f;//线宽，单位dp，需要转成px
    private final int LINE_LENGHT_DP = 10;//线长，单位dp，需要转成px
    private final int ROTATE_STEP = 4;//两条线间隔角度

    private int ROTATE_START_ANGRAL = 50;
    private int ROTATE_LINE_SWEEP = 50;

    private float ROTATE_LINE_WITTH_DP = 4f;//旋转线的线宽
    private float rotate = 0;//旋转的角度
    private Paint rotateLinePaint;
    private final int ANIM_DURIATION = 1200;
    private int transLineNum, lineNum;//半透明线数，总数
    private Paint linePaint;
    private int lineLenght, lineWidth;
    private int canvasWidth, canvasHeight;//画布宽和高
    private int outerRadius;//线所占半径
    private int transStep = 0;
    private ValueAnimator rotateAnim, rotateLineAnima;
    private int curRotateAngle = ROTATE_START_ANGRAL;

    public OznerDialView(Context context) {
        super(context);
        initView();
    }

    public OznerDialView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OznerDialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasWidth = w;
        canvasHeight = h;
        initData();
        startRotateAnima();
        startRotateLineAnima();
    }

    private void initView() {
        lineLenght = dp2px(getContext(), LINE_LENGHT_DP);
        lineWidth = dp2px(getContext(), LINE_WIDTH_DP);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(COLOR_LINE_BASE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeWidth(lineWidth);

        rotateLinePaint = new Paint();
        rotateLinePaint.setAntiAlias(true);
        rotateLinePaint.setStyle(Paint.Style.STROKE);
        rotateLinePaint.setStrokeCap(Paint.Cap.ROUND);
        rotateLinePaint.setStrokeWidth(dp2px(getContext(), ROTATE_LINE_WITTH_DP));
        rotateLinePaint.setColor(COLOR_LINE_BASE);


        transLineNum = 55 / ROTATE_STEP;
        lineNum = 180 / ROTATE_STEP;
    }

    private void initData() {
        if (2 * canvasHeight > canvasWidth) {
            outerRadius = canvasWidth / 2;
        } else {
            outerRadius = canvasHeight;
        }
        outerRadius -= dp2px(getContext(), LINE_WIDTH_DP) / 2;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defalutWidth = 600;
        int defaultHeight = 300;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.max(defalutWidth, widthSize);
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = defalutWidth;
        } else {
            width = defalutWidth;
        }
//        if (heightMode == MeasureSpec.EXACTLY) {
//            height = heightSize;
//        } else if (heightMode == MeasureSpec.AT_MOST) {
//            height = Math.min(defaultHeight, heightSize);
//        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
//            height = defaultHeight;
//        } else {
//            height = defaultHeight;
//        }

        height = width / 2;

        setMeasuredDimension(width, height);
    }

    /**
     * 获取半透明部分的颜色
     *
     * @param index
     *
     * @return
     */
    private int getTransColor(int index) {
        int baseAlpha = Color.alpha(COLOR_LINE_BASE);
        int diffValue = baseAlpha - TRANS_ALPHA;
        int stepDif = diffValue / transLineNum;
        int alpha = TRANS_ALPHA + stepDif * Math.abs(index - transStep);
        return Color.argb(alpha, Color.red(COLOR_LINE_BASE), Color.green(COLOR_LINE_BASE), Color.blue(COLOR_LINE_BASE));
    }

    private void drawLine(Canvas canvas, float startX, float startY, float endX, float endY, Paint paint, int rotate) {
        canvas.rotate(-rotate, canvasWidth / 2, canvasHeight);
        canvas.drawLine(startX, startY, endX, endY, paint);
        canvas.rotate(rotate, canvasWidth / 2, canvasHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 1; i < lineNum; i++) {
            if (i < transStep + transLineNum / 2 && i > transStep - transLineNum / 2) {
                linePaint.setColor(getTransColor(i));
            } else {
                linePaint.setColor(COLOR_LINE_BASE);
            }
            drawLine(canvas, canvasWidth / 2 + outerRadius, canvasHeight, canvasWidth / 2 + outerRadius - lineLenght, canvasHeight, linePaint, i * ROTATE_STEP);
        }

        drawArcLine(canvas);
    }

    private void drawArcLine(Canvas canvas) {
        canvas.drawArc(new RectF(lineLenght + dp2px(getContext(), ROTATE_LINE_WITTH_DP) * 1.5f
                , lineLenght + dp2px(getContext(), ROTATE_LINE_WITTH_DP) * 1.5f
                , canvasWidth - lineLenght - dp2px(getContext(), ROTATE_LINE_WITTH_DP) * 1.5f
                , canvasWidth - lineLenght - dp2px(getContext(), ROTATE_LINE_WITTH_DP) * 1.5f), curRotateAngle, ROTATE_LINE_SWEEP, false, rotateLinePaint);
        canvas.drawArc(new RectF(lineLenght + dp2px(getContext(), ROTATE_LINE_WITTH_DP) * 1.5f
                , lineLenght + dp2px(getContext(), ROTATE_LINE_WITTH_DP) * 1.5f
                , canvasWidth - lineLenght - dp2px(getContext(), ROTATE_LINE_WITTH_DP) * 1.5f
                , canvasWidth - lineLenght - dp2px(getContext(), ROTATE_LINE_WITTH_DP) * 1.5f), -180 - ROTATE_LINE_SWEEP - curRotateAngle, ROTATE_LINE_SWEEP, false, rotateLinePaint);

    }

    private void startRotateLineAnima() {
        if (rotateLineAnima == null) {
            rotateLineAnima = ValueAnimator.ofInt(ROTATE_START_ANGRAL, ROTATE_START_ANGRAL - 360 + ROTATE_LINE_SWEEP);
            rotateLineAnima.setDuration(ANIM_DURIATION * 2);
            rotateLineAnima.setRepeatCount(Animation.INFINITE);
            rotateLineAnima.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    curRotateAngle = (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
        }
        rotateLineAnima.start();
    }


    private void startRotateAnima() {
        if (rotateAnim == null) {
            rotateAnim = ValueAnimator.ofInt(lineNum, 0);
            rotateAnim.setDuration(ANIM_DURIATION);
            rotateAnim.setInterpolator(new LinearInterpolator());
            rotateAnim.setRepeatCount(Animation.INFINITE);
            rotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    transStep = (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
        }

        rotateAnim.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (rotateAnim != null) {
            rotateAnim.cancel();
            rotateAnim = null;
        }

        if (rotateLineAnima != null) {
            rotateLineAnima.cancel();
            rotateLineAnima = null;
        }
    }
}
