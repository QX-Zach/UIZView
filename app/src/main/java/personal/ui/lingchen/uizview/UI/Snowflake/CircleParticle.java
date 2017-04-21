package personal.ui.lingchen.uizview.UI.Snowflake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by ozner_67 on 2017/1/10.
 * 邮箱：xinde.zhang@cftcn.com
 */

public class CircleParticle extends Particle {
    float radius;//粒子半径
    float alpha = 1.0f;//基础透明度
    Rect mBound;//控件边框
    static Random random = new Random();


    /**
     * @param color
     * @param x
     * @param y
     * @param bound
     */
    public CircleParticle(int color, float x, float y, Rect bound) {
        super(color, x, y);
        mBound = bound;
        radius = random.nextInt(10);
//        radius = 50;
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * alpha));//防止透明颜色变成黑色
        canvas.drawCircle(cx, cy, radius, paint);
    }

    /**
     * 粒子位置由此控制
     *
     * @param factor
     */
    @Override
    protected void caculate(float factor) {
//        cx = cx + factor * random.nextInt(mBound.width()) * (random.nextFloat() - 0.5f);
//        cy = cy + factor * random.nextInt(mBound.height() / 2);
        cx += factor * (mBound.width() / 2 - cx);
        cy += factor * (mBound.height() - cy);


        alpha = (1f - factor) * (1 + random.nextFloat());
    }
}
