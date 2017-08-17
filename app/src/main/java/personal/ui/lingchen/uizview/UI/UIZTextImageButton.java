package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import personal.ui.lingchen.uizview.R;

/** 
* description: UIZTextImageButton,自定义组合按钮，图片+文字
* autour: ozner_67
* date: 2017/8/17 17:29 
* e-mail: xinde.zhang@cftcn.com
*/
public class UIZTextImageButton extends LinearLayout {
    private ImageView mImageView;
    private TextView mTextView;
    private boolean bTextBefore = false;//文字在图片前
    private ColorStateList textColorStateList;//, textBgColor;
    private float textSize, textWidth, textHeight, space, imageWidth, imageHeight;
    private String textValue;
    private Drawable imageBg, imageSrc;
    private boolean isSelected = false;

    public UIZTextImageButton(Context context) {
        super(context);
        initView(context);
    }

    public UIZTextImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UIZTextImageSytle);
        bTextBefore = typedArray.getBoolean(R.styleable.UIZTextImageSytle_uiz_textBefore, false);
        textColorStateList = typedArray.getColorStateList(R.styleable.UIZTextImageSytle_uiz_text_color);
        imageBg = typedArray.getDrawable(R.styleable.UIZTextImageSytle_uiz_image_bg);
        imageSrc = typedArray.getDrawable(R.styleable.UIZTextImageSytle_uiz_image_src);
        textValue = typedArray.getString(R.styleable.UIZTextImageSytle_uiz_text);
        textSize = typedArray.getDimension(R.styleable.UIZTextImageSytle_uiz_text_size, 14f);
        textWidth = typedArray.getDimension(R.styleable.UIZTextImageSytle_uiz_text_width, -1);
        textHeight = typedArray.getDimension(R.styleable.UIZTextImageSytle_uiz_text_height, -1);
        imageWidth = typedArray.getDimension(R.styleable.UIZTextImageSytle_uiz_image_width, -1);
        imageHeight = typedArray.getDimension(R.styleable.UIZTextImageSytle_uiz_image_height, -1);
        space = typedArray.getDimension(R.styleable.UIZTextImageSytle_uiz_space, -1);
        typedArray.recycle();
        initView(context);
    }


    public void initView(Context context) {
        mImageView = new ImageView(context);
        mTextView = new TextView(context);
        if (imageBg != null) {
            mImageView.setBackground(imageBg);
        }
        if (imageSrc != null) {
            mImageView.setImageDrawable(imageSrc);
        }
        if (textValue != null) {
            mTextView.setText(textValue);
        }
        if (textColorStateList != null) {
            mTextView.setTextColor(textColorStateList);
        }
        mTextView.setTextSize(textSize);
        LayoutParams textParms;
        LayoutParams imageParms;
        if (textWidth > 0) {
            if (textHeight > 0) {
                textParms = new LayoutParams((int) textWidth, (int) textHeight);
            } else {
                textParms = new LayoutParams((int) textWidth, LayoutParams.WRAP_CONTENT);
            }
        } else {
            if (textHeight > 0) {
                textParms = new LayoutParams(LayoutParams.WRAP_CONTENT, (int) textHeight);
            } else {
                textParms = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            }
        }

        if (imageWidth > 0) {
            if (imageHeight > 0) {
                imageParms = new LayoutParams((int) imageWidth, (int) imageHeight);
            } else {
                imageParms = new LayoutParams((int) imageWidth, LayoutParams.WRAP_CONTENT);
            }
        } else {
            if (imageHeight > 0) {
                imageParms = new LayoutParams(LayoutParams.WRAP_CONTENT, (int) imageHeight);
            } else {
                imageParms = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            }
        }

        int pxSpace = 10;
        if (space > 0) {
            pxSpace = (int) space;
        }

        if (bTextBefore) {
            if (getOrientation() == LinearLayout.HORIZONTAL) {
                imageParms.gravity = Gravity.CENTER_VERTICAL;
                textParms.gravity = Gravity.CENTER_VERTICAL;
                imageParms.setMargins(pxSpace, 0, 0, 0);
            } else {
                imageParms.gravity = Gravity.CENTER_HORIZONTAL;
                textParms.gravity = Gravity.CENTER_HORIZONTAL;
                imageParms.setMargins(0, pxSpace, 0, 0);
            }
            addView(mTextView, textParms);
            addView(mImageView, imageParms);
        } else {
            if (getOrientation() == LinearLayout.HORIZONTAL) {
                imageParms.gravity = Gravity.CENTER_VERTICAL;
                textParms.gravity = Gravity.CENTER_VERTICAL;
                textParms.setMargins(pxSpace, 0, 0, 0);
            } else {
                imageParms.gravity = Gravity.CENTER_HORIZONTAL;
                textParms.gravity = Gravity.CENTER_HORIZONTAL;
                textParms.setMargins(0, pxSpace, 0, 0);
            }
            addView(mImageView, imageParms);
            addView(mTextView, textParms);
        }
    }

    /**
     * 设置选中状态
     * @param selected
     */
    public void setSelected(boolean selected) {
        mImageView.setSelected(selected);
        mTextView.setSelected(selected);
        isSelected = selected;
    }

    public boolean isSelected(){
        return isSelected;
    }
}
