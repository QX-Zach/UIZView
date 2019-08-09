package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：zach
 * @Date: 2019-07-23 17:33
 * @Email: xinde.zhang@cftcn.com
 * @ProjectName: substation_synergy
 * @PackageName: substation.chat.view
 * @Description:
 */
public class SpecialMaskView extends ViewGroup {
    private static final String TAG = "SpecialMaskView";
    private int columCount = 3;
    private int mTextColor = Color.BLUE;
    private float mtextSize = 14.f;//dp

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof TextView) {
                ((TextView) getChildAt(i)).setTextColor(mTextColor);
            }
        }
        requestLayout();
    }

    public void setColumCount(int colCount) {
        if (colCount > 0) {
            this.columCount = colCount;
        } else {
            columCount = 1;
        }
        requestLayout();
    }

    public void setTextSize(float textSize) {
        this.mtextSize = textSize;
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof TextView) {
                ((TextView) getChildAt(i)).setTextSize(textSize);
            }
        }
        requestLayout();
    }

    public SpecialMaskView(Context context) {
        super(context);
        init();
    }

    public SpecialMaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpecialMaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        viewList = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int totalWidth = 0;
        int totalHeight = 0;


        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            if (i % columCount == 0) {
                totalWidth = 0;
                totalHeight += child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            }
            totalWidth += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

        }

//        totalHeight = Math.max(totalHeight, sizeHeight) + getPaddingTop() + getPaddingBottom();
//        totalWidth = Math.max(totalWidth, sizeWidth) + getPaddingLeft() + getPaddingRight();

        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? resolveSize(sizeWidth, widthMeasureSpec) : resolveSize(totalWidth + getPaddingLeft() + getPaddingRight(), widthMeasureSpec)
                , modeHeight == MeasureSpec.EXACTLY ? resolveSize(sizeHeight, heightMeasureSpec) : resolveSize(totalHeight + getPaddingTop() + getPaddingBottom(), heightMeasureSpec));
//        setMeasuredDimension(resolveSize(totalWidth + getPaddingLeft() + getPaddingRight(), widthMeasureSpec), resolveSize(totalHeight + getPaddingTop() + getPaddingBottom(), heightMeasureSpec));
//        setMeasuredDimension(resolveSize(sizeWidth,widthMeasureSpec),resolveSize(sizeHeight,heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e(TAG, "onLayout: ");
        layoutCustom(l, t, r, b);


    }

    private void layoutCustom(int left, int top, int right, int bottom) {
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        final int childTotalWidth = right - left - paddingLeft - paddingRight;
        final int childTotalHeight = bottom - top - paddingTop - paddingBottom;
        final int count = getChildCount();

        if (count == 0) {
            return;
        }

        int rowCount = count / columCount;

        if (0 != count % columCount) {
            rowCount += 1;
        }

        final int childDefaultW = childTotalWidth / columCount;
        final int childDefaultH = childTotalHeight / rowCount;

        int childTop = paddingTop;
        int childLeft = paddingLeft;
        int rowMaxBottomMargin = 0;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            if (child != null && child.getVisibility() != GONE) {
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                if (i % columCount == 0) {
                    childLeft = paddingLeft;
                    if (i != 0) {
                        childTop += childDefaultH + rowMaxBottomMargin;
                    }
                    rowMaxBottomMargin = 0;
                }

                rowMaxBottomMargin = Math.max(rowMaxBottomMargin, lp.bottomMargin);

                setChildFrame(child, childLeft + lp.leftMargin, childTop + lp.topMargin
                        , Math.min(childDefaultW, child.getMeasuredWidth())
                        , Math.min(childDefaultH, child.getMeasuredHeight()));
                childLeft += childDefaultW;

            }
        }
    }

    public void addString(String String) {
        TextView textView = new TextView(getContext());
        textView.setTextColor(mTextColor);
        textView.setText(String);
        textView.setTextSize(mtextSize);
        MarginLayoutParams marginLayoutParams = new MarginLayoutParams(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
        textView.setLayoutParams(marginLayoutParams);
        this.addView(textView);
    }

    public void
    remove(int pos) {
        if (this.getChildAt(pos) != null) {
            this.removeViewAt(pos);
        }
    }

    public void clear() {
        if (this.getChildCount() > 0) {
            this.removeAllViews();
        }
    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(this.getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

}
