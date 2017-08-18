package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ozner_67 on 2017/8/18.
 * 邮箱：xinde.zhang@cftcn.com
 */

public class UIZTimeRemain extends View {
    private static final String TAG = "UIZTimeRemain";
    private int defalutSize = 300;
    private Paint linePaint;
    private int mWidth, mHeight;

    public UIZTimeRemain(Context context) {
        super(context);
    }

    public UIZTimeRemain(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    public UIZTimeRemain(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaint() {
        linePaint = new Paint();
        linePaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(0xffef3e6e);
        linePaint.setStrokeWidth(15);
        linePaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG, "onSizeChanged: ");
        this.mWidth = w;
        this.mHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defalutSize, defalutSize);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defalutSize, height);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, defalutSize);
        }

//        int width = getMySize(defalutSize, widthMeasureSpec);
//        int height = getMySize(defalutSize, heightMeasureSpec);
//
//        if (width < height) {
//            height = width;
//        } else {
//            width = height;
//        }
//
//        setMeasuredDimension(MeasureSpec.makeMeasureSpec(width,MeasureSpec.getMode(widthMeasureSpec))
//                , MeasureSpec.makeMeasureSpec(height,MeasureSpec.getMode(heightMeasureSpec)));
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidth / 2, mHeight / 2, (int) (Math.min(mWidth / 2, mHeight / 2) * 0.8f), linePaint);
    }
}
