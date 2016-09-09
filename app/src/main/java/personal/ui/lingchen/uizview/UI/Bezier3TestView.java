package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ozner_67 on 2016/8/17.
 */
public class Bezier3TestView extends View {
    private Paint mPaint;
    private Path mPath;


    public Bezier3TestView(Context context) {
        super(context);
        initView();
    }

    public Bezier3TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public Bezier3TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPath = new Path();

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setTextSize(25);
        mPaint.setColor(Color.parseColor("#ee0000"));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defalutWidth = 450;
        int defaultHeight = 450;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(defalutWidth, widthSize);
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

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.moveTo(5, 405);
        mPath.cubicTo(135, 5, 265, 405, 405, 5);
        canvas.drawPath(mPath, mPaint);
        canvas.drawCircle(5, 405, 3, mPaint);
        canvas.drawCircle(135, 5, 3, mPaint);
        canvas.drawCircle(265, 405, 3, mPaint);
        canvas.drawCircle(405, 5, 3, mPaint);
    }
}
