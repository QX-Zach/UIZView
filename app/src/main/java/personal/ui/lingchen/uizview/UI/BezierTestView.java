package personal.ui.lingchen.uizview.UI;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by ozner_67 on 2016/8/17.
 * 贝赛尔曲线测试
 */
public class BezierTestView extends View {
    private Paint mPaint;
    private Path mPath;
    private Point startPoint;
    private Point endPoint;
    //辅助点
    private Point assistPoint;
    private int mWidth, mHeight;

    ValueAnimator shockYAnim, shockYAnimR;

    private boolean isRunning = false;

    public BezierTestView(Context context) {
        super(context);
        initView();
    }

    public BezierTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BezierTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //设置防抖
        mPaint.setDither(true);
        mPaint.setColor(Color.CYAN);
        mPaint.setStrokeWidth(dp2px(getContext(), 5));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPath = new Path();
    }

    private void initData() {
        startPoint = new Point(dp2px(getContext(), 10), mHeight / 2);
        endPoint = new Point(mWidth - dp2px(getContext(), 10), mHeight / 2);
        assistPoint = new Point(mWidth / 2, mHeight / 4);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        initData();
    }

    private void drawBezier2(Canvas canvas) {
        mPath.reset();
        mPath.moveTo(startPoint.x, startPoint.y);
        mPath.quadTo(assistPoint.x, assistPoint.y, endPoint.x, endPoint.y);
        canvas.drawPath(mPath, mPaint);
        canvas.drawCircle(assistPoint.x, assistPoint.y, dp2px(getContext(), 2), mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBezier2(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                assistPoint.x = (int) event.getX();
                assistPoint.y = (int) event.getY();
                invalidate();
                break;
        }
        return true;
    }

    /**
     * 震荡动画
     */
    private void shockAnimFc() {
        shockYAnim = ValueAnimator.ofInt(mHeight / 4, mHeight / 2, mHeight * 3 / 4);
        shockYAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                assistPoint.y = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        shockYAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        shockYAnim.setDuration(1000);
        shockYAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isRunning)
                    shockAnimFcR();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        shockYAnim.start();
    }

    private void shockAnimFcR() {
        shockYAnimR = ValueAnimator.ofInt(mHeight * 3 / 4, mHeight / 2, mHeight / 4);
        shockYAnimR.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                assistPoint.y = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        shockYAnimR.setInterpolator(new AccelerateDecelerateInterpolator());
        shockYAnimR.setDuration(1000);
        shockYAnimR.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isRunning)
                    shockAnimFc();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        shockYAnimR.start();
    }

    public void startAnim() {
        if (!isRunning) {
            isRunning = true;
            shockAnimFc();
        }
    }

    public void stopAnim() {
        if (isRunning) {
            isRunning = false;
            if (shockYAnim != null && shockYAnim.isRunning()) {
                shockYAnim.cancel();
            }
            if(shockYAnimR!=null&&shockYAnimR.isRunning()){
                shockYAnimR.cancel();
            }
            initData();
            invalidate();
        }
    }

    private int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
