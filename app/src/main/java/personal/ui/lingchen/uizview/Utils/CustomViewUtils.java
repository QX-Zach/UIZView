package personal.ui.lingchen.uizview.Utils;

import android.content.Context;
import android.graphics.Paint;
import android.util.TypedValue;

/**
 * Created by ozner_67 on 2018/1/15.
 * 邮箱：xinde.zhang@cftcn.com
 */

public class CustomViewUtils {
    protected int dpToPx(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
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
