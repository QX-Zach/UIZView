package personal.ui.lingchen.uizview.UI;

import android.content.Context;
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

    }
}
