package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by ozner_67 on 2018/6/19.
 * 邮箱：xinde.zhang@cftcn.com
 */
public class SlideAdjustView extends UIZBaseView {
    private static final String TAG = "SlideAdjustView";
    private Paint bgPaint;//背景线画笔
    private Paint fontPaint;//大背景画笔
    private Paint dashPaint;//虚线画笔
    private Paint textPaint;//文本画笔
    private Paint numPaint;//数字画笔
    private Paint thumbPaint;//滑块画笔

    private int numColor = 0xff5ecffe;//数字颜色
    private int textColor = 0xff5ecffe;//文本颜色
    private int thumColor = 0xff3b71df;//滑块颜色
    private int dashLineColor = 0xff3b71df;//虚线颜色
    private int bgLineColor = 0xff3b71df;//背景线颜色
    private int bgColor = Color.WHITE;//大背景颜色
//    private boolean isInitParmater = false;

    private int numSize = dpToPx(20);//数字文本大小
    private int textSize = dpToPx(14);//文字文本大小
    private float thumRadius = dpToPx(13);//滑块半径
    private float lineOffset = thumRadius / 2 + dpToPx(10);//每条线的偏移量
    private int numHeight, textHeight;
    private int maskOffset = dpToPx(8);
    private MyCircle circleCenter;
    private int minValue;
    private int maxValue;
    private int currentValue;


    public SlideAdjustView(Context context) {
        super(context);
        initPaint();
    }

    public SlideAdjustView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public SlideAdjustView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(bgLineColor);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(4);
        bgPaint.setMaskFilter(new BlurMaskFilter(maskOffset, BlurMaskFilter.Blur.SOLID));

        fontPaint = new Paint();
        fontPaint.setColor(bgColor);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

        numPaint = new Paint();
        numPaint.setAntiAlias(true);
        numPaint.setColor(numColor);
        numPaint.setTextSize(numSize);

        thumbPaint = new Paint();
        thumbPaint.setAntiAlias(true);
        thumbPaint.setColor(dashLineColor);

        dashPaint = new Paint();
        dashPaint.setAntiAlias(true);
        dashPaint.setColor(dashLineColor);
        dashPaint.setStyle(Paint.Style.STROKE);
        dashPaint.setStrokeWidth(dpToPx(4));
        dashPaint.setPathEffect(new DashPathEffect(new float[]{dpToPx(4), dpToPx(4)}, 0));

        numHeight = getFontHeight(numPaint);
        textHeight = getFontHeight(textPaint);

        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    private float startAngle;
    private float sweepAngle;


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = (int) (0.3f * mWidth);
//        circleCenter = getCircleCenter(new PointF(0, mHeight-numHeight-lineOffset),new PointF(mWidth/2,thumRadius),new PointF(mWidth, mHeight-numHeight-lineOffset));
        circleCenter = getCircleCenter(new PointF(0, thumRadius * 5), new PointF(mWidth / 2, thumRadius), new PointF(mWidth, thumRadius * 5));
        Log.e(TAG, "onSizeChanged: " + circleCenter.toString());
        sweepAngle = calculateAngle(0);
        startAngle = -sweepAngle - 90;
        sweepAngle *= 2;
    }


    private void drawBg(Canvas canvas) {
        canvas.drawCircle(circleCenter.x, circleCenter.y, circleCenter.r, bgPaint);
        canvas.drawCircle(circleCenter.x, circleCenter.y, circleCenter.r + bgPaint.getStrokeWidth(), fontPaint);
        canvas.drawCircle(circleCenter.x, circleCenter.y, circleCenter.r - lineOffset, dashPaint);
        Paint tempPaint = new Paint();
        tempPaint.setShader(new LinearGradient(0, 0, mWidth, 0, new int[]{0xddffffff,0x44ffffff,0x00ffffff,0x44ffffff,0xddffffff}, null, Shader.TileMode.CLAMP));
        canvas.drawCircle(circleCenter.x,circleCenter.y,circleCenter.r-lineOffset+dpToPx(5),tempPaint);
    }


    private void drawThumb(Canvas canvas) {
        canvas.drawCircle(mWidth / 2, thumRadius, thumRadius, thumbPaint);
    }


    private float calculateAngle(float startX) {
        if (startX < mWidth / 2) {
            return (float) Math.asin(Math.abs(mWidth / 2 - startX) / circleCenter.r) + 90;
        } else {
            return 90 - (float) Math.asin(Math.abs(mWidth / 2 - startX) / circleCenter.r);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        drawThumb(canvas);
    }

    private MyCircle getCircleCenter(PointF p1, PointF p2, PointF p3)  //求圆心
    {
        float a, b, c, d, e, f;
        MyCircle myCircle = new MyCircle();
        a = 2 * (p2.x - p1.x);
        b = 2 * (p2.y - p1.y);
        c = p2.x * p2.x + p2.y * p2.y - p1.x * p1.x - p1.y * p1.y;
        d = 2 * (p3.x - p2.x);
        e = 2 * (p3.y - p2.y);
        f = p3.x * p3.x + p3.y * p3.y - p2.x * p2.x - p2.y * p2.y;
        myCircle.x = (b * f - e * c) / (b * d - e * a);
        myCircle.y = (d * c - a * f) / (b * d - e * a);
        myCircle.r = (float) Math.sqrt((myCircle.x - p1.x) * (myCircle.x - p1.x) + (myCircle.y - p1.y) * (myCircle.y - p1.y));//半径
        return myCircle;
    }

    /**
     * 圆心参数
     */
    class MyCircle {
        public float x;
        public float y;
        public float r;

        @Override
        public String toString() {
            return "MyCircle{" +
                    "x=" + x +
                    ", y=" + y +
                    ", r=" + r +
                    '}';
        }
    }
}
