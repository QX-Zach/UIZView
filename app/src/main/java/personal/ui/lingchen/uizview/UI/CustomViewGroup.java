package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import personal.ui.lingchen.uizview.R;

/**
 * @author：zach
 * @Date: 2019-07-24 09:22
 * @Email: xinde.zhang@cftcn.com
 * @ProjectName: UIZView
 * @PackageName: personal.ui.lingchen.uizview.UI
 * @Description:
 */
public class CustomViewGroup extends ViewGroup {
    private int mHSpacing;
    private int mVSpacing;
    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomViewGroup);
        mHSpacing = array.getDimensionPixelOffset(R.styleable.CustomViewGroup_horizontalSpacing,20);
        mVSpacing = array.getDimensionPixelOffset(R.styleable.CustomViewGroup_verticalSpacing,20);
        array.recycle();
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        int width = getPaddingLeft();
        int height = getPaddingTop();
        for (int i=0;i<count;i++){
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            LayoutParams lp = (LayoutParams)child.getLayoutParams();

            if(lp.mSettingPaddingLeft!=0){
                width+= lp.mSettingPaddingLeft;
            }
            if(lp.mSettingPaddingTop!=0){
                height+= lp.mSettingPaddingTop;
            }
            lp.x = width;
            lp.y = height;

            width += mHSpacing+child.getMeasuredWidth();
            height+= mVSpacing +child.getMeasuredHeight();
        }

        width+= getPaddingRight() - mHSpacing;
        height+= getPaddingBottom() -mVSpacing;

        setMeasuredDimension(resolveSize(width,widthMeasureSpec),resolveSize(height,heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for(int i=0;i<count;i++){
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams)child.getLayoutParams();
            child.layout(lp.x,lp.y,lp.x+child.getMeasuredWidth(),lp.y+child.getMeasuredHeight());
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(this.getContext(),attrs);
    }

    public static class LayoutParams extends MarginLayoutParams{
        int x;
        int y;
        int mSettingPaddingLeft;
        int mSettingPaddingTop;


        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray array = c.obtainStyledAttributes(attrs,R.styleable.CustomViewGroup_LayoutParams);
            mSettingPaddingLeft = array.getDimensionPixelOffset(R.styleable.CustomViewGroup_LayoutParams_layout_paddingLeft,15);
            mSettingPaddingTop = array.getDimensionPixelOffset(R.styleable.CustomViewGroup_LayoutParams_layout_paddingTop,15);
            array.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

}
