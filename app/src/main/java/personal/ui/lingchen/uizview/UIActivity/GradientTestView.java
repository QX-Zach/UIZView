package personal.ui.lingchen.uizview.UIActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;

import personal.ui.lingchen.uizview.UI.UIZBaseView;

/**
 * Created by ozner_67 on 2018/1/15.
 * 邮箱：xinde.zhang@cftcn.com
 */

public class GradientTestView extends UIZBaseView {
    private static final int FIRST_COLOR = 0xff415cec;
    private static final int SECOND_COLOR = 0xff6f6fef;
    private static final int[] OTHER_COLORS = new int[]{0xffe985f7, 0xfff0c170, 0xffec4141};
    public static int[] colors = new int[]{0xff415cec,0xff6f6fef,0xffe985f7, 0xfff0c170, 0xffec4141};
    //    private int mWidth, mHight;
    private Paint mPaint;

    public GradientTestView(Context context) {
        super(context);
        init();
    }

    public GradientTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradientTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        mWidth = w;
//        mHeight = h;
//    }

    private void init() {
//        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
//        Shader linShader = new

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setShader(new LinearGradient(0, 0, mWidth, 0, colors, null, Shader.TileMode.CLAMP));
        canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
    }
}
