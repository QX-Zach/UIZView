package personal.ui.lingchen.uizview.UI;


import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * description: MarqueeView跑马灯
 * autour: ozner_67
 * date: 2018/4/24 10:10
 * e-mail: xinde.zhang@cftcn.com
 */
public class MarqueeView  extends View{
    private Paint paint;

    public MarqueeView(Context context) {
        super(context);
        paint = new Paint();
    }

    public MarqueeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getContentHeight(){
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        return Math.abs((fontMetrics.bottom-fontMetrics.top))/2;
    }

}
