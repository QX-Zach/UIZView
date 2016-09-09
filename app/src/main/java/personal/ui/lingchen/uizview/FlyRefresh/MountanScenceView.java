package personal.ui.lingchen.uizview.FlyRefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ozner_67 on 2016/8/4.
 */
public class MountanScenceView extends View {
    private float mScaleX = 1;
    private float mScaleY = 1;
    private final float WIDTH = 1;
    private final float HEIGHT = 1;

    public MountanScenceView(Context context) {
        super(context);
    }

    public MountanScenceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MountanScenceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final float width = getMeasuredWidth();
        final float height = getMeasuredHeight();
        mScaleX = width / WIDTH;
        mScaleY = height / HEIGHT;
    }

    private void updateMountainPath(float factor){

    }
}
