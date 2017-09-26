package personal.ui.lingchen.uizview.Animation;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.HashMap;

/**
 * Created by ozner_67 on 2017/9/5.
 * 邮箱：xinde.zhang@cftcn.com
 * 参考地址：http://blog.csdn.net/lzyzsd/article/details/39255341
 */

public class TadaAnimatorUtil {
    private static TadaAnimatorUtil mInstance;
    private static float shakeFactor = 1f;
    private PropertyValuesHolder pvhScaleX;
    private PropertyValuesHolder pvhScaleY;
    private PropertyValuesHolder pvhRotate;

    private HashMap<Integer, ObjectAnimator> animatorMap = new HashMap<>();


    private TadaAnimatorUtil() {
        pvhScaleX = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
                                                    Keyframe.ofFloat(0f, 1f),
                                                    Keyframe.ofFloat(.1f, .9f),
                                                    Keyframe.ofFloat(.2f, .9f),
                                                    Keyframe.ofFloat(.3f, 1.1f),
                                                    Keyframe.ofFloat(.4f, 1.1f),
                                                    Keyframe.ofFloat(.5f, 1.1f),
                                                    Keyframe.ofFloat(.6f, 1.1f),
                                                    Keyframe.ofFloat(.7f, 1.1f),
                                                    Keyframe.ofFloat(.8f, 1.1f),
                                                    Keyframe.ofFloat(.9f, 1.1f),
                                                    Keyframe.ofFloat(1f, 1f)
        );
        pvhScaleY = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                                                    Keyframe.ofFloat(0f, 1f),
                                                    Keyframe.ofFloat(.1f, .9f),
                                                    Keyframe.ofFloat(.2f, .9f),
                                                    Keyframe.ofFloat(.3f, 1.1f),
                                                    Keyframe.ofFloat(.4f, 1.1f),
                                                    Keyframe.ofFloat(.5f, 1.1f),
                                                    Keyframe.ofFloat(.6f, 1.1f),
                                                    Keyframe.ofFloat(.7f, 1.1f),
                                                    Keyframe.ofFloat(.8f, 1.1f),
                                                    Keyframe.ofFloat(.9f, 1.1f),
                                                    Keyframe.ofFloat(1f, 1f)
        );
        pvhRotate = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                                                    Keyframe.ofFloat(0f, 0f),
                                                    Keyframe.ofFloat(.1f, -3f * shakeFactor),
                                                    Keyframe.ofFloat(.2f, -3f * shakeFactor),
                                                    Keyframe.ofFloat(.3f, 3f * shakeFactor),
                                                    Keyframe.ofFloat(.4f, -3f * shakeFactor),
                                                    Keyframe.ofFloat(.5f, 3f * shakeFactor),
                                                    Keyframe.ofFloat(.6f, -3f * shakeFactor),
                                                    Keyframe.ofFloat(.7f, 3f * shakeFactor),
                                                    Keyframe.ofFloat(.8f, -3f * shakeFactor),
                                                    Keyframe.ofFloat(.9f, 3f * shakeFactor),
                                                    Keyframe.ofFloat(1f, 0)
        );
    }

    public static void setShakeFactor(float shakeFactor) {
        mInstance = null;
        TadaAnimatorUtil.shakeFactor = shakeFactor;
    }

    public static TadaAnimatorUtil getInstance() {
        if (mInstance == null) {
            mInstance = new TadaAnimatorUtil();
        }
        return mInstance;
    }


    /**
     * 水平抖动
     *
     * @param view
     * @param duration
     */
    public void startTadaHorizontal(View view, int duration) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX, pvhScaleY, pvhRotate).
                setDuration(duration);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        animatorMap.put(view.getId(), objectAnimator);
        objectAnimator.start();
    }

    /**
     * 透明动画
     * @param view
     * @param duration
     */
    public void startFade(View view, int duration) {
        ObjectAnimator objectAnimator;

        if (!animatorMap.containsKey(view.getId())) {
            objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
            objectAnimator.setDuration(duration);
            objectAnimator.setInterpolator(new DecelerateInterpolator());
            objectAnimator.setRepeatCount(ValueAnimator.INFINITE);//设置动画重复次数，无限次
            objectAnimator.setRepeatMode(ValueAnimator.REVERSE);//设置动画循环模式
            animatorMap.put(view.getId(), objectAnimator);
        } else {
            objectAnimator = animatorMap.get(view.getId());
        }
        if (!objectAnimator.isRunning())
            objectAnimator.start();
    }

    /**
     * 结束动画
     *
     * @param view
     */
    public void endAnimator(View view) {
        if (animatorMap.containsKey(view.getId())) {
            animatorMap.get(view.getId()).end();
            animatorMap.remove(view.getId());
        }
    }

    /**
     * 结束全部动画
     */
    public void endAll() {
        for (int id : animatorMap.keySet()) {
            animatorMap.get(id).end();
            animatorMap.remove(id);
        }
    }
}
