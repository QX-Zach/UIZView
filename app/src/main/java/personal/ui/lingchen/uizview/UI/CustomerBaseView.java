package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ozner_67 on 2016/9/9.
 */
public class CustomerBaseView extends View{
    public CustomerBaseView(Context context) {
        super(context);
    }

    public CustomerBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
