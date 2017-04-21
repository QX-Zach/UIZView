package personal.ui.lingchen.uizview.UI.Snowflake;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ozner_67 on 2017/1/10.
 * 邮箱：xinde.zhang@cftcn.com
 */

public class FollingParticleView extends View {
    private static final String TAG = "FollingParticleView";
    List<Particle> particles;
    List<Particle> animParticleList;
    private Paint particlePaint;
    private CircleParticleFacotry circleParticleFacotry;
    private ValueAnimator animator;
    private Rect bound;
//    private Particle particle;

    public FollingParticleView(Context context) {
        super(context);
        init();
    }

    public FollingParticleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FollingParticleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        particlePaint = new Paint();
        circleParticleFacotry = new CircleParticleFacotry();
        particles = new ArrayList<>();
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e(TAG, "onAnimationUpdate: " + animation.getAnimatedValue());
                float value = (float) animation.getAnimatedValue();
                if (value < 1 && value > 0) {
                    invalidate();
                } else {
                    if (animParticleList != null) {
                        animParticleList.clear();
                    }
                    animParticleList = particles;
                }
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e(TAG, "onAnimationStart: ");
                if (animParticleList != null) {
                    animParticleList.clear();
                }
                animParticleList = particles;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e(TAG, "onAnimationEnd: ");
//                animator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.e(TAG, "onAnimationCancel: ");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(3000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bound = new Rect();
        getDrawingRect(bound);
        Log.e(TAG, "onSizeChanged: left:" + bound.left + " , top:" + bound.top + " ,width:" + bound.width() + " ,height:" + bound.height());
        particles = circleParticleFacotry.generateParticles(10, bound);
        Log.e(TAG, "onSizeChanged: size:" + particles.size());
        if (animator != null) {
            if (animator.isRunning()) {
                animator.cancel();
            }
            animator.start();
        }
    }

    private void drawParticles(Canvas canvas) {
        for (Particle p : particles) {
//            p.advance(canvas, particlePaint, (float) animator.getAnimatedValue());
            p.advance(canvas, particlePaint, 0.5f);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

//        canvas.drawCircle(getHeight() / 2, getHeight() / 2, 50, particlePaint);
        drawParticles(canvas);
//        particle.advance(canvas,particlePaint,0.5f);
        super.onDraw(canvas);
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
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(defaultHeight, heightSize);
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            height = defaultHeight;
        } else {
            height = defaultHeight;
        }

        height = width / 2;

        setMeasuredDimension(width, height);
    }
}
