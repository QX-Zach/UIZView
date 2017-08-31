package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import personal.ui.lingchen.uizview.R;

/**
 * Created by ozner_67 on 2017/8/25.
 * 邮箱：xinde.zhang@cftcn.com
 */

public class UIZTwoInfoText extends LinearLayout {
    private TextView mLeftText, mRightText;

    private float leftSize, rightSize;
    private int leftColor, rightColor;
    private CharSequence leftValue, rightValue;

    public UIZTwoInfoText(Context context) {
        super(context);
        initView(context);
    }

    public UIZTwoInfoText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.UIZTwoInfoTextStyle);
        leftSize = array.getDimensionPixelSize(R.styleable.UIZTwoInfoTextStyle_uiz_left_text_size, dpToPx(14));
        leftColor = array.getColor(R.styleable.UIZTwoInfoTextStyle_uiz_left_text_color, 0xff333333);
        rightSize = array.getDimensionPixelSize(R.styleable.UIZTwoInfoTextStyle_uiz_right_text_size, dpToPx(14));
        rightColor = array.getColor(R.styleable.UIZTwoInfoTextStyle_uiz_right_text_color, 0xff333333);
        leftValue = array.getString(R.styleable.UIZTwoInfoTextStyle_uiz_left_text_value);
        rightValue = array.getString(R.styleable.UIZTwoInfoTextStyle_uiz_right_text_value);
        array.recycle();

        initView(context);
        mLeftText.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftSize);
        mLeftText.setTextColor(leftColor);
        mRightText.setTextSize(TypedValue.COMPLEX_UNIT_PX,rightSize);
        mRightText.setTextColor(rightColor);
        if (leftValue != null) mLeftText.setText(leftValue);
        if (rightValue != null) mRightText.setText(rightValue);

    }

    public UIZTwoInfoText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.layout_uiz_two_info_item, this);
        mLeftText = (TextView) view.findViewById(R.id.tv_left);
        mRightText = (TextView) view.findViewById(R.id.tv_right);
    }

    protected int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    public TextView leftTextView() {
        return mLeftText;
    }

    public TextView rightTextView() {
        return mRightText;
    }
}
