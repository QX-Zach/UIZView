package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * Created by ozner_67 on 2017/8/21.
 * 邮箱：xinde.zhang@cftcn.com
 */

public abstract class UIZBaseView extends View {
    private int defalutSize = 300;
    protected int mWidth, mHeight;
    protected Rect canvasRect;//绘图区域;


    public UIZBaseView(Context context) {
        super(context);
    }

    public UIZBaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public UIZBaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG, "onSizeChanged: ");
        this.mWidth = w;
        this.mHeight = h;
        canvasRect = new Rect(0, 0, w, h);
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
    }

    protected int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    //  获取字体串宽度
    protected int getStringWidth(Paint paint, String str) {
        return (int) paint.measureText(str);
    }

    //  获取字体高度
    protected int getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }

}
