package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author：zach
 * @Date: 2019-07-23 20:35
 * @Email: xinde.zhang@cftcn.com
 * @ProjectName: UIZView
 * @PackageName: personal.ui.lingchen.uizview.UI
 * @Description:
 */
public class SpecialMaskView2 extends ViewGroup {
    private static final String TAG = "SpecialMaskView2";
    private int columCount = 4;

    public SpecialMaskView2(Context context) {
        super(context);
    }

    public SpecialMaskView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SpecialMaskView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int count = getChildCount();
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            if (i % columCount == 0) {
                height += child.getMeasuredHeight();
            }
        }
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutCustom(l, t, r, b);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void layoutCustom(int left, int top, int right, int bottom) {
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();


        final int width = right - left;
        final int height = bottom - top;
        int childRight = width - paddingRight;
        int childHorizontalSpace = width - paddingLeft - paddingRight;
        int childVerticalSpacde = height - paddingTop - paddingBottom;


        final int count = getChildCount();
        if (count == 0) {
            return;
        }
        int rowCount = count / columCount;
        if (0 != count % columCount) {
            rowCount += 1;
        }
        final int childWidth = childHorizontalSpace / columCount;
        final int childHeight = childVerticalSpacde / rowCount;
        int childTop = paddingTop;
        int childLeft = paddingLeft;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE) {


                if (i % columCount == 0) {
                    childLeft = paddingLeft;
                    if (i != 0) {
                        childTop += childHeight;
                    }
                }

                setChildFrame(child, childLeft, childTop, childWidth, Math.min(childHeight,child.getMeasuredHeight()));
                childLeft += childWidth;
            }
        }
    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(this.getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new MarginLayoutParams(p);
    }

}
