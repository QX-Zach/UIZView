package personal.ui.lingchen.uizview.UI;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

/**
 * Created by ozner_67 on 2017/10/23.
 * 邮箱：xinde.zhang@cftcn.com
 */

public class UIZRippleCircle extends UIZBaseView {
    private static final String TAG = "UIZRippleCircle";
    private int animatorDuration = 6000;
    private Paint circlePaint, leftRipplePaint, rightRipplePaint, bgPaint, textPaint;
    /**
     * 字体大小
     */
    private int textSize = dpToPx(30);
    private String unit = "L";
    private float value = 0f;

    /**
     * 背景圆半径
     */
    private float circleRadius;
    /**
     * 背景圆中心点
     */
    private int centerX, centerY;
    private int textColor = 0xff168ffe;
    private int circleColor = 0xffe3f9ff, leftRippleColor = 0xfffbfeff, rightRippleColor = 0xffeffbff;
    private Path leftRipplePath, rightRipplePath;
    private Point pOutLeft = new Point(), pControlOutLeft = new Point(), pControlOutRight = new Point();
    private Point pRight = new Point(), pMiddle = new Point(), pControlLeft = new Point(), pControlRight = new Point();
    private int offset = 0;
    private ValueAnimator animator;
    private Bitmap bgCircleBm, rippleBm;

    public UIZRippleCircle(Context context) {
        super(context);
        initView();
    }

    public UIZRippleCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public UIZRippleCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        bgPaint = new Paint();

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(circleColor);
        circlePaint.setStyle(Paint.Style.FILL);

        leftRipplePaint = new Paint();
        leftRipplePaint.setAntiAlias(true);
        leftRipplePaint.setStyle(Paint.Style.FILL);
        leftRipplePaint.setColor(leftRippleColor);

        rightRipplePaint = new Paint();
        rightRipplePaint.setAntiAlias(true);
        rightRipplePaint.setStyle(Paint.Style.FILL);
        rightRipplePaint.setColor(rightRippleColor);

        leftRipplePath = new Path();
        rightRipplePath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        circleRadius = Math.min(mWidth / 2, mHeight / 2);
        Log.e(TAG, "onSizeChanged: width->" + mWidth + " ,height->" + mHeight);
        centerX = mWidth / 2;
        centerY = mWidth / 2;

        pMiddle.x = 0;
        pMiddle.y = mHeight * 7 / 8;
        pRight.x = mWidth;
        pRight.y = mHeight * 7 / 8;

        pControlLeft.x = mWidth * 3 / 8;
        pControlLeft.y = mHeight / 2;
        pControlRight.x = mWidth * 5 / 8;
        pControlRight.y = mHeight * 5 / 4;


        //不在屏幕部分的定位点
        pOutLeft.x = pMiddle.x - mWidth;
        pOutLeft.y = pMiddle.y;
        pControlOutLeft.x = pControlLeft.x - mWidth;
        pControlOutLeft.y = pControlLeft.y;
        pControlOutRight.x = pControlRight.x - mWidth;
        pControlOutRight.y = pControlRight.y;
        makeRipplePath();

        /*
        * 先生成全局的图片，然后用画布平移来实现动画，
        * 解决内存抖动的问题
        * */
        rippleBm = makeRipple();
        bgCircleBm = makeBgCircle();

        if (animator != null && animator.isRunning()) {
            animator.cancel();
            animator = null;
        }

        begainAinmation();
    }

    private void begainAinmation() {
        animator = ValueAnimator.ofInt(0, mWidth);
        animator.setDuration(animatorDuration);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(Animation.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offset = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 设置图层,不设置图层没有预览效果
         */
        int layer = canvas.saveLayer(0, 0, mWidth, mHeight, bgPaint, Canvas.ALL_SAVE_FLAG);
        drawBg(canvas);
        canvas.restoreToCount(layer);
        drawValueText(canvas);
    }

    private void drawValueText(Canvas canvas) {
        String text = "";
        if (value < 1000) {
            text = String.format("%.1f%s", value, unit);
        } else {
            text = String.format("%d%s", (int) value, unit);
        }
        int textWidth = getStringWidth(textPaint, text);
        int textHeight = getFontHeight(textPaint);
        canvas.drawText(text, centerX - textWidth / 2, centerY + textHeight * 5 / 16, textPaint);
    }


    private void drawBg(Canvas canvas) {
        canvas.drawBitmap(bgCircleBm, 0, 0, bgPaint);
        bgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        /*
        * 这步平移是将两倍于屏幕宽的图片从-mWidth位置绘制，
        * 保证动画从左向右开始时，左边有图形可以展示
        * */
        canvas.translate(offset, 0);
        canvas.drawBitmap(rippleBm, -mWidth, 0, bgPaint);
        bgPaint.setXfermode(null);
    }

    /**
     * 创建背景圆
     *
     * @return
     */
    private Bitmap makeBgCircle() {
        Bitmap bm = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        c.drawCircle(centerX, centerX, circleRadius, circlePaint);
        return bm;
    }

    public void setValue(float value) {
        if (value < 0.1) {
            value = 0;
        }
        this.value = value;
        invalidate();
    }

    public void setUint(String unit) {
        this.unit = unit;
        invalidate();
    }


//    /**
//     * 本方法弃用，留着只是为了记录思路
//     * 绘制左右波纹
//     * 这个方法直接在onDraw中调用，平移绘制路径，但是会造成内存抖动
//     */
//    private Bitmap makeRipple() {
//        Bitmap bm = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(bm);
//
//        rightRipplePath.reset();
//        rightRipplePath.moveTo(pOutLeft.x + offset, pOutLeft.y);
//        rightRipplePath.cubicTo(pControlOutLeft.x + offset, pOutLeft.y * 2 - pControlOutLeft.y, pControlOutRight.x + offset, pMiddle.y * 2 - pControlOutRight.y, pMiddle.x + offset, pMiddle.y);
//        rightRipplePath.moveTo(pMiddle.x + offset, pMiddle.y);
//        rightRipplePath.cubicTo(pControlLeft.x + offset, pMiddle.y * 2 - pControlLeft.y, pControlRight.x + offset, pRight.y * 2 - pControlRight.y, pRight.x + offset, pRight.y);
//        rightRipplePath.lineTo(pRight.x + offset, mHeight);
//        rightRipplePath.lineTo(pOutLeft.x + offset, mHeight);
//        rightRipplePath.lineTo(pOutLeft.x + offset, pOutLeft.y);
//        c.drawPath(rightRipplePath, rightRipplePaint);
//
//        leftRipplePath.reset();
//        leftRipplePath.moveTo(pOutLeft.x + offset, pOutLeft.y);
//        leftRipplePath.cubicTo(pControlOutLeft.x + offset, pControlOutLeft.y, pControlOutRight.x + offset, pControlOutRight.y, pMiddle.x + offset, pMiddle.y);
//        leftRipplePath.moveTo(pMiddle.x + offset, pMiddle.y);
//        leftRipplePath.cubicTo(pControlLeft.x + offset, pControlLeft.y, pControlRight.x + offset, pControlRight.y, pRight.x + offset, pRight.y);
//        leftRipplePath.lineTo(pRight.x + offset, mHeight);
//        leftRipplePath.lineTo(pOutLeft.x + offset, mHeight);
//        leftRipplePath.lineTo(pOutLeft.x + offset, pOutLeft.y);
//        c.drawPath(leftRipplePath, leftRipplePaint);
//        return bm;
//    }


    /**
     * 创建波纹路径
     */
    private void makeRipplePath() {
        rightRipplePath.reset();
        rightRipplePath.moveTo(pOutLeft.x, pOutLeft.y);
        rightRipplePath.cubicTo(pControlOutLeft.x, pOutLeft.y * 2 - pControlOutLeft.y, pControlOutRight.x, pMiddle.y * 2 - pControlOutRight.y, pMiddle.x, pMiddle.y);
        rightRipplePath.moveTo(pMiddle.x, pMiddle.y);
        rightRipplePath.cubicTo(pControlLeft.x, pMiddle.y * 2 - pControlLeft.y, pControlRight.x, pRight.y * 2 - pControlRight.y, pRight.x, pRight.y);
        rightRipplePath.lineTo(pRight.x, mHeight);
        rightRipplePath.lineTo(pOutLeft.x, mHeight);
        rightRipplePath.lineTo(pOutLeft.x, pOutLeft.y);

        leftRipplePath.reset();
        leftRipplePath.moveTo(pOutLeft.x, pOutLeft.y);
        leftRipplePath.cubicTo(pControlOutLeft.x, pControlOutLeft.y, pControlOutRight.x, pControlOutRight.y, pMiddle.x, pMiddle.y);
        leftRipplePath.moveTo(pMiddle.x, pMiddle.y);
        leftRipplePath.cubicTo(pControlLeft.x, pControlLeft.y, pControlRight.x, pControlRight.y, pRight.x, pRight.y);
        leftRipplePath.lineTo(pRight.x, mHeight);
        leftRipplePath.lineTo(pOutLeft.x, mHeight);
        leftRipplePath.lineTo(pOutLeft.x, pOutLeft.y);
    }


    /**
     * 绘制左右波纹
     * <p>
     * 先生成全局的图片，然后用画布平移来实现动画，
     * 解决内存抖动的问题
     */
    private Bitmap makeRipple() {
        Bitmap riBm = Bitmap.createBitmap(mWidth * 2, mHeight, Bitmap.Config.ARGB_8888);
        Canvas rippleCanvas = new Canvas(riBm);
        /*
        * 这步平移是将本来应该绘制在mWidth宽度的屏幕外的图绘制在新建的bitmap上
        * 保证所有应画的图形都可见
        * */
        rippleCanvas.translate(mWidth, 0);
        rippleCanvas.drawPath(rightRipplePath, rightRipplePaint);
        rippleCanvas.drawPath(leftRipplePath, leftRipplePaint);
        return riBm;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }
}
