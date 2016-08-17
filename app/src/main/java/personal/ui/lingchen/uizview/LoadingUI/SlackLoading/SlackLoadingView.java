package personal.ui.lingchen.uizview.LoadingUI.SlackLoading;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ozner_67 on 2016/8/16.
 */
public class SlackLoadingView extends View {

    //静止状态
    private final int STATUS_STILL = 0;
    //加载状态
    private final int STATUS_LOADING = 1;
    //最大间隔时长
    private final int MAX_DURATION = 3000;
    //最小间隔时长
    private final int MIN_DURATION = 500;
    //Canvas起始旋转角度
    private final int CANVAS_ROTATE_ANGLE = 60;
    private Paint mPaint, rectPaint;
    //动画间隔时长
    private int mDuration = MIN_DURATION;
    //圆半径
    private int mCircleRadius;
    //动画当前状态
    private int mStatus = STATUS_STILL;
    //线条最大长度
    private final int MAX_LINE_LENGTH = dp2px(getContext(), 120);
    //线条最短长度
    private final int MIN_LINE_LENGTH = dp2px(getContext(), 40);
    private int mWidth, mHeight;
    private int[] mColors = new int[]{Color.RED, Color.YELLOW, Color.CYAN, Color.GREEN};
    //Canvas旋转角度
    //线条总长度
    private int mEntireLineLength = MIN_LINE_LENGTH;
    private int mCanvasAngle;
    //线条长度
    private float mLineLength;
    //所有动画
    private List<Animator> mAnimList = new ArrayList<>();
    //第几步动画
    private int mStep;
    //半圆Y轴位置
    private float mCircleY;
    private Paint roundPaint;


    public SlackLoadingView(Context context) {
        super(context);
        initView();
    }

    public SlackLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SlackLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColors[0]);
        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setColor(Color.BLACK);
        rectPaint.setStrokeWidth(dp2px(getContext(), 1));
        rectPaint.setTextSize(dp2px(getContext(),14));

        roundPaint = new Paint();
        roundPaint.setAntiAlias(true);
        roundPaint.setColor(Color.BLUE);
        roundPaint.setStyle(Paint.Style.FILL);
        roundPaint.setStrokeCap(Paint.Cap.ROUND);
        roundPaint.setStrokeWidth(dp2px(getContext(),5));

    }

    private void initData() {
        mCanvasAngle = CANVAS_ROTATE_ANGLE;
        mLineLength = mEntireLineLength;
        mCircleRadius = mEntireLineLength / 5;
        mPaint.setStrokeWidth(mCircleRadius * 2);
        mStep = 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        initData();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(mWidth / 2, 0, mWidth / 2, mHeight, rectPaint);
        canvas.drawLine(0, mHeight / 2, mWidth, mHeight / 2, rectPaint);
        canvas.drawText("y", mWidth / 2 - dp2px(getContext(), 10), mHeight - dp2px(getContext(), 10), rectPaint);
        canvas.drawText("x", mWidth - dp2px(getContext(), 10), mHeight / 2 - dp2px(getContext(), 10), rectPaint);

        switch (mStep % 4) {
            case 0:
                for (int i = 0; i < mColors.length; i++) {
                    mPaint.setColor(mColors[i]);
                    drawCRLC(canvas,
                            mWidth / 2 - mEntireLineLength / 2.2f,
                            mHeight / 2 - mLineLength,
                            mWidth / 2 - mEntireLineLength / 2.2f,
                            mHeight / 2 + mEntireLineLength,
                            mPaint, mCanvasAngle + i * 90);
                }
                break;
            case 1:
                for (int i = 0; i < mColors.length; i++) {
                    mPaint.setColor(mColors[i]);
                    drawCR(canvas, mWidth / 2 - mEntireLineLength / 2.2f, mHeight / 2 + mEntireLineLength, mPaint, mCanvasAngle + i * 90);
                }
                break;
            case 2:
                for (int i = 0; i < mColors.length; i++) {
                    mPaint.setColor(mColors[i]);
                    drawCRCC(canvas, mWidth / 2 - mEntireLineLength / 2.2f, mHeight / 2 + mCircleY, mPaint, mCanvasAngle + i * 90);
                }
                break;
            case 3:
                for (int i = 0; i < mColors.length; i++) {
                    mPaint.setColor(mColors[i]);
                    drawLC(canvas,
                            mWidth / 2 - mEntireLineLength / 2.2f,
                            mHeight / 2 + mEntireLineLength,
                            mWidth / 2 - mEntireLineLength / 2.2f,
                            mHeight / 2 + mLineLength,
                            mPaint, mCanvasAngle + i * 90);
                }
                break;
        }
    }

    private void drawCRLC(Canvas canvas, float startX, float startY, float stopX, float stopY, @NonNull Paint paint, int rotate) {
        canvas.rotate(rotate, mWidth / 2, mHeight / 2);//以中心点为原点旋转

        canvas.drawArc(new RectF(startX - mCircleRadius, startY - mCircleRadius, startX + mCircleRadius, startY + mCircleRadius), 180, 180, true, mPaint);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
        canvas.drawArc(new RectF(stopX - mCircleRadius, stopY - mCircleRadius, stopX + mCircleRadius, stopY + mCircleRadius), 0, 180, true, mPaint);


        canvas.drawCircle(startX, startY, dp2px(getContext(), 2), roundPaint);
        canvas.rotate(-rotate, mWidth / 2, mHeight / 2);

    }

    private void drawCR(Canvas canvas, float x, float y, @NonNull Paint paint, int rotate) {
        canvas.rotate(rotate, mWidth / 2, mHeight / 2);
        canvas.drawCircle(x, y, mCircleRadius, paint);
        canvas.rotate(-rotate, mWidth / 2, mHeight / 2);
    }

    private void drawCRCC(Canvas canvas, float x, float y, @NonNull Paint paint, int rotate) {
        canvas.rotate(rotate, mWidth / 2, mHeight / 2);
        canvas.drawCircle(x, y, mCircleRadius, paint);
        canvas.rotate(-rotate, mWidth / 2, mHeight / 2);
    }

    private void drawLC(Canvas canvas, float startX, float startY, float stopX, float stopY, @NonNull Paint paint, int rotate) {
        canvas.rotate(rotate, mWidth / 2, mHeight / 2);
        //下边的半圆
        canvas.drawArc(new RectF(startX - mCircleRadius, startY - mCircleRadius, startX + mCircleRadius, startY + mCircleRadius), 0, 180, true, mPaint);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
        //上边的半圆
        canvas.drawArc(new RectF(stopX - mCircleRadius, stopY - mCircleRadius, stopX + mCircleRadius, stopY + mCircleRadius), 180, 180, true, mPaint);
        canvas.rotate(-rotate, mWidth / 2, mHeight / 2);
    }

    /**
     * Animation1
     * 动画1
     * Canvas Rotate Line Change
     * 画布旋转，线条变化动画
     */
    private void startCRLCAnim() {
        Collection<Animator> animList = new ArrayList<>();
        ValueAnimator canvasRotateAnim = ValueAnimator.ofInt(CANVAS_ROTATE_ANGLE + 0, CANVAS_ROTATE_ANGLE + 360);
        canvasRotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCanvasAngle = (int) animation.getAnimatedValue();
            }
        });
        animList.add(canvasRotateAnim);
        ValueAnimator lineLengthAnim = ValueAnimator.ofFloat(mEntireLineLength, -mEntireLineLength);
        lineLengthAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLineLength = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        animList.add(lineLengthAnim);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(mDuration);
        animatorSet.playTogether(animList);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.addListener(new AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i("@=>", "动画1结束");
                if (mStatus == STATUS_LOADING) {
                    mStep++;
                    startCRAnim();
                }
            }
        });
        animatorSet.start();
        mAnimList.add(animatorSet);
    }

    /**
     * Animation2
     * 动画2
     * Canvas Rotate
     * 画布旋转动画
     */
    private void startCRAnim() {
        ValueAnimator canvasRoteateAnim = ValueAnimator.ofInt(mCanvasAngle, mCanvasAngle + 180);
        canvasRoteateAnim.setDuration(mDuration / 2);
        canvasRoteateAnim.setInterpolator(new LinearInterpolator());
        canvasRoteateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCanvasAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        canvasRoteateAnim.addListener(new AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i("@=>", "动画2结束 ");
                mStep++;
                startCRCCAnim();
            }
        });
        canvasRoteateAnim.start();
        mAnimList.add(canvasRoteateAnim);
    }

    private void startCRCCAnim() {
        Collection<Animator> animList = new ArrayList<>();

        ValueAnimator canvasRotateAnim = ValueAnimator.ofInt(mCanvasAngle, mCanvasAngle + 90, mCanvasAngle + 180);
        canvasRotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCanvasAngle = (int) animation.getAnimatedValue();
            }
        });

        animList.add(canvasRotateAnim);

        ValueAnimator circleYAnim = ValueAnimator.ofFloat(mEntireLineLength, mEntireLineLength / 4, mEntireLineLength);
        circleYAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCircleY = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        animList.add(circleYAnim);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setDuration(mDuration);
        animationSet.playTogether(animList);
        animationSet.setInterpolator(new LinearInterpolator());
        animationSet.addListener(new AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("@=>", "动画3结束");
                if (mStatus == STATUS_LOADING) {
                    mStep++;
                    startLCAnim();
                }
            }
        });
        animationSet.start();

        mAnimList.add(animationSet);
    }

    /**
     * Animation4
     * 动画4
     * Line Change
     * 线条变化动画
     */
    private void startLCAnim() {
        ValueAnimator lineWidthAnim = ValueAnimator.ofFloat(mEntireLineLength, -mEntireLineLength);
        lineWidthAnim.setDuration(mDuration);
        lineWidthAnim.setInterpolator(new LinearInterpolator());
        lineWidthAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLineLength = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        lineWidthAnim.addListener(new AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("@=>", "动画4结束");
                if (mStatus == STATUS_LOADING) {
                    mStep++;
                    startCRLCAnim();
                }
            }
        });
        lineWidthAnim.start();

        mAnimList.add(lineWidthAnim);
    }

    public void reset() {
        if (mStatus == STATUS_LOADING) {
            mStatus = STATUS_STILL;
            for (Animator anim : mAnimList) {
                anim.cancel();
            }
        }
        initData();
        invalidate();
    }

    public void setLinelenght(float scale) {
        mEntireLineLength = (int) (scale * (MAX_LINE_LENGTH - MIN_LINE_LENGTH)) + MIN_LINE_LENGTH;
        reset();
    }

    public void setDuration(float scale) {
        mDuration = (int) (scale * (MAX_DURATION - MIN_DURATION)) + MIN_DURATION;
        reset();
    }

    public void start() {
        if (mStatus == STATUS_STILL) {
            mAnimList.clear();
            mStatus = STATUS_LOADING;
            startCRLCAnim();
        }
    }

    private int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    class AnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}