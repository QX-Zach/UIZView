package personal.ui.lingchen.uizview.UI;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;

/**
 * packageName: personal.ui.lingchen.uizview.UI
 * fileName: SlideAdjustView.java
 * date: 2018/6/20 15:05
 * author：zach
 * Email: xinde.zhang@cftcn.com
 * <p>
 * 描述:
 */
public class SlideAdjustView extends View {
    private static final String TAG = "SlideAdjustView";
    protected int mWidth, mHeight;
    private Paint bgPaint;//背景线画笔
    private Paint dashPaint;//虚线画笔
    private Paint textPaint;//文本画笔
    private Paint numPaint;//数字画笔
    private Paint thumbPaint;//滑块画笔,大背景画笔
    private Paint thumbLinePaint;

    private int numColor = 0xff00A6F4;//数字颜色
    private int textColor = 0xff00A6F4;//文本颜色
    private int dashLineColor = 0xff00A6F4;//虚线颜色
    private int bgLineColor = 0xff3b71df;//背景线颜色
    private int bgColor = Color.WHITE;//大背景颜色
    private String text = "温度调节";
    private int numSize = dpToPx(15);//数字文本大小
    private int textSize = dpToPx(18);//文字文本大小
    private float thumRadius = dpToPx(13);//滑块半径
    private float lineOffset = thumRadius / 2 + dpToPx(10);//每条线的偏移量
    private int numHeight, textHeight;
    private int maskOffset = dpToPx(8);
    private MyCircle circleCenter;
    private int minValue = 40;
    private int maxValue = 99;
    private Point thumbPoint = new Point();
    private Point oldThumbPoint = new Point();
    private double orgAngle;
    private int maxDelX = 0;
    private SlideAdjustListener mListener;
    private boolean isInit = false;
    private int initValue = Integer.MAX_VALUE;

    public void setListener(SlideAdjustListener listener) {
        this.mListener = listener;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
        postInvalidate();
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        postInvalidate();
    }

    /**
     * 设置当前的值
     *
     * @param value
     */
    public void setValue(int value) {
        if (value < minValue) {
            initValue = minValue;
        } else if (value > maxValue) {
            initValue = maxValue;
        } else {
            initValue = value;
        }

        if (isInit) {
            valueToPoint(initValue);
        }
    }

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

        thumbLinePaint = new Paint();
        thumbLinePaint.setAntiAlias(true);
        thumbLinePaint.setColor(bgColor);
        thumbLinePaint.setStyle(Paint.Style.STROKE);
        thumbLinePaint.setStrokeWidth(dpToPx(1));

        dashPaint = new Paint();
        dashPaint.setAntiAlias(true);
        dashPaint.setColor(dashLineColor);
        dashPaint.setStyle(Paint.Style.STROKE);
        dashPaint.setStrokeCap(Paint.Cap.ROUND);
        dashPaint.setStrokeWidth(dpToPx(4));
        dashPaint.setPathEffect(new DashPathEffect(new float[]{dpToPx(0.5f), dpToPx(10)}, 0));

        numHeight = getFontHeight(numPaint);
        textHeight = getFontHeight(textPaint);

        setLayerType(LAYER_TYPE_SOFTWARE, null);

    }
//    private float sweepAngle;


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        initView();
    }


    /**
     * 初始化基础参数
     */
    private void initView() {
        circleCenter = getCircleCenter(new PointF(0, thumRadius * 5), new PointF(mWidth / 2, thumRadius), new PointF(mWidth, thumRadius * 5));

//        circleCenter = getCircleCenter(new PointF(mWidth / 2, thumRadius), new PointF(0, mHeight), new PointF(mWidth, mHeight));
        Log.e(TAG, "onSizeChanged: " + circleCenter.toString());
//        sweepAngle = calculateAngle(0);
//        startAngle = -sweepAngle - 90;
//        sweepAngle *= 2;

        //初始化滑块的初始位置
        thumbPoint.x = mWidth / 2;
        thumbPoint.y = (int) thumRadius;

        oldThumbPoint.x = thumbPoint.x;
        oldThumbPoint.y = thumbPoint.y;
        orgAngle = Math.acos(mWidth / 2.0f / circleCenter.r);
        maxDelX = (int) (circleCenter.r * Math.cos(orgAngle));
        Log.e(TAG, "initView:最大maxDelX--->>>" + maxDelX);
        isInit = true;
        if (initValue != Integer.MAX_VALUE) {
            valueToPoint(initValue);
        }
    }

    /**
     * 绘制静态背景
     *
     * @param canvas
     */
    private void drawBg(Canvas canvas) {
        canvas.drawCircle(circleCenter.x, circleCenter.y, circleCenter.r, bgPaint);
        thumbPaint.setColor(bgColor);
        canvas.drawCircle(circleCenter.x, circleCenter.y, circleCenter.r + bgPaint.getStrokeWidth(), thumbPaint);
        canvas.drawCircle(circleCenter.x, circleCenter.y, circleCenter.r - lineOffset, dashPaint);
        Paint tempPaint = new Paint();
        tempPaint.setShader(new LinearGradient(0, 0, mWidth, 0, new int[]{0xddffffff, 0x44ffffff, 0x00ffffff, 0x44ffffff, 0xddffffff}, null, Shader.TileMode.CLAMP));
        canvas.drawCircle(circleCenter.x, circleCenter.y, circleCenter.r - lineOffset + dpToPx(5), tempPaint);
    }

    /**
     * 绘制滑块,并使滑块旋转
     *
     * @param canvas
     */
    private void drawThumb(Canvas canvas) {
        thumbPaint.setColor(dashLineColor);
        canvas.drawCircle(thumbPoint.x, thumbPoint.y, thumRadius, thumbPaint);
        canvas.rotate((float) dAngle, thumbPoint.x, thumbPoint.y);
        Path leftPath = new Path();
        leftPath.moveTo(thumbPoint.x - dpToPx(2), thumbPoint.y - dpToPx(3));
        leftPath.lineTo(thumbPoint.x - dpToPx(5), thumbPoint.y);
        leftPath.lineTo(thumbPoint.x - dpToPx(2), thumbPoint.y + dpToPx(3));
        canvas.drawPath(leftPath, thumbLinePaint);

        Path rightPath = new Path();
        rightPath.moveTo(thumbPoint.x + dpToPx(2), thumbPoint.y - dpToPx(3));
        rightPath.lineTo(thumbPoint.x + dpToPx(5), thumbPoint.y);
        rightPath.lineTo(thumbPoint.x + dpToPx(2), thumbPoint.y + dpToPx(3));
        canvas.drawPath(rightPath, thumbLinePaint);
        canvas.rotate(-(float) dAngle, thumbPoint.x, thumbPoint.y);
    }

    private void drawText(Canvas canvas) {
        canvas.drawText(String.valueOf(minValue), thumRadius, mHeight - textHeight / 3, numPaint);
        canvas.drawText(String.valueOf(maxValue), mWidth - getStringWidth(numPaint, String.valueOf(maxValue)) - thumRadius
                , mHeight - textHeight / 3, numPaint);

        canvas.drawText(text, mWidth / 2 - getStringWidth(textPaint, text) / 2, mHeight / 2 + textHeight / 2, textPaint);

    }


    private int pX, pY;
    private int delX = 0;
    private int sX = 0;
    private double dAngle = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //以中间线向两侧计算变化值，只计算X的值，然后转换成角度，间接转换成圆弧上的位置
                if (delX <= maxDelX && delX >= -maxDelX) {
                    delX += ((int) event.getX() - sX);
                    sX = (int) event.getX();
                }
                oldThumbPoint.x = thumbPoint.x;
                oldThumbPoint.y = thumbPoint.y;
                if (delX > maxDelX) {
                    delX = maxDelX;
                }
                if (delX < -maxDelX) {
                    delX = -maxDelX;
                }
                double delAngle = Math.atan2(delX, circleCenter.r);
                pX = (int) (mWidth / 2 + Math.sin(delAngle) * circleCenter.r);
                pY = (int) (thumRadius + circleCenter.r - circleCenter.r * Math.cos(delAngle));
                dAngle = delAngle * 180 / Math.PI;

                thumbPoint.x = pX;
                thumbPoint.y = pY;
                postInvalidate();
                if (mListener != null) {
                    mListener.onMove(deltaToValue());
                }
                break;
            case MotionEvent.ACTION_UP:
                thumbPoint.x = pX;
                thumbPoint.y = pY;
                if (mListener != null) {
                    mListener.onFinished(deltaToValue());
                }
                break;
        }
        return true;
    }


    /**
     * 值转换成滑块位置
     *
     * @param value
     */
    private void valueToPoint(int value) {
        delX = 2 * maxDelX * (value - (maxValue + minValue) / 2) / (maxValue - minValue);
        double delAngle = Math.atan2(delX, circleCenter.r);
        pX = (int) (mWidth / 2 + Math.sin(delAngle) * circleCenter.r);
        pY = (int) (thumRadius + circleCenter.r - circleCenter.r * Math.cos(delAngle));
        dAngle = delAngle * 180 / Math.PI;

        thumbPoint.x = pX;
        thumbPoint.y = pY;

        postInvalidate();
    }

    /**
     * 滑块位置转换成数值
     *
     * @return
     */
    private int deltaToValue() {

        return (int) ((maxValue + minValue) / 2.0f + 1.0f * delX / maxDelX * (maxValue - minValue) / 2.0f);

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
        drawText(canvas);
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

    public interface SlideAdjustListener {
        void onMove(int value);

        void onFinished(int value);
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
