package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author：zach
 * @Date: 2019-09-16 15:48
 * @Email: xinde.zhang@cftcn.com
 * @ProjectName: UIZView
 * @PackageName: personal.ui.lingchen.uizview.UI
 * @Description:
 */
public class SweepView extends View {
    private Paint mPaint;
    private int color = 0xffDBF1FE;
    private int[] mCircleColors = new int[]{0xff8cdefa, 0xff14c0fa, 0xff24e5ce, 0xff0df614, 0xfff8c12e, 0xfff6530b, 0xfff6b99f};
    private float[] mcolorPoints = new float[]{0.1f, 0.25f, 0.325f, 0.425f, 0.6f, 0.75f, 0.9f};
    public SweepView(Context context) {
        super(context);
        init();
    }

    public SweepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SweepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        mPaint = new Paint();
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    private int widht;
    private int height;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        widht = w;
        height = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        SweepGradient sweepGradient = new SweepGradient(widht/2,height/2,mCircleColors,mcolorPoints);
        mPaint.setShader(sweepGradient);
        canvas.drawArc(0,0,widht,height,180,180,false,mPaint);
    }
}
