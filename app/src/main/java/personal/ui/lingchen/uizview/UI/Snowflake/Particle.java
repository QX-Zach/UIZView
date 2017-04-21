package personal.ui.lingchen.uizview.UI.Snowflake;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by ozner_67 on 2017/1/10.
 * 邮箱：xinde.zhang@cftcn.com
 *
 * 粒子基类
 */

public abstract class Particle {
    float cx;
    float cy;
    int color;

    public Particle(int color, float x, float y) {
        this.color = color;
        this.cx = x;
        this.cy = y;
    }

    protected abstract void draw(Canvas canvas, Paint paint);

    protected abstract void caculate(float factor);

    public void advance(Canvas canvas, Paint paint, float factor) {
        caculate(factor);
        draw(canvas, paint);
    }
}
