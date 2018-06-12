package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by ozner_67 on 2018/4/3.
 * 邮箱：xinde.zhang@cftcn.com
 */

public class SignalView extends UIZBaseView {
    private static final String TAG = "SignalView";
    private Paint mPaint;
    private int lineNum = 5;
    private int mColor = 0xff00A6F4;
    private int mBaseColor = 0xff9fc3f3;
    private int singalStre = 0;
    private int mLineWidth = dpToPx(2);

    private int mStep = 0;
    private int offset = 0;


    public SignalView(Context context) {
        super(context);
        initPaint();
    }

    public SignalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public SignalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * 返回线条数量
     * @return
     */
    public int getLineCount(){
        return lineNum;
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(mBaseColor);
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        offset = mLineWidth / 2;
    }

    /**
     * 设置信号强度
     *
     * @param strength
     */
    public void setSingalStrength(int strength) {
        if (singalStre == strength) {
            return;
        }

        if (lineNum > strength) {
            this.singalStre = strength;
        } else {
            this.singalStre = lineNum;
        }
        this.invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStep = (mWidth - offset * 2) / (lineNum - 1);
    }

    /**
     * 设置线条个数
     *
     * @param lineCount
     */
    public void setLineCount(int lineCount) {
        this.lineNum = lineCount;
        mStep = (mWidth - offset * 2) / (lineNum - 1);
    }

    private void drawLines(Canvas canvas) {
        for (int i = 0; i < lineNum; i++) {
            if (i < singalStre) {
                mPaint.setColor(mColor);
            } else {
                mPaint.setColor(mBaseColor);
            }
            canvas.drawLine(offset + mStep * i, mHeight - offset, offset + mStep * i, mHeight + offset - mHeight * (i + 1) / lineNum, mPaint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLines(canvas);
    }
}
