package personal.ui.lingchen.uizview.UI

import android.content.Context
import android.content.res.TypedArray
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_z_normal_setting_item.view.*
import personal.ui.lingchen.uizview.R

/**
 * 通用设置的Item
 * @author：zach
 * @Date: 2019-09-20 10:00
 * @Email: xinde.zhang@cftcn.com
 * @ProjectName: UIZView
 * @PackageName: personal.ui.lingchen.uizview.UI
 * @Description:
 */
class ZNormalSetItem : LinearLayout {


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()

        val typedArray: TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ZNormalSetItem)
        val leftIcon = typedArray.getResourceId(R.styleable.ZNormalSetItem_leftIcon, 0)
        if (leftIcon != 0) {
            ivLeftIcon.setImageResource(leftIcon)
        }
        val leftBg = typedArray.getResourceId(R.styleable.ZNormalSetItem_leftIconBackground,0)
        if(leftBg!=0){
            ivLeftIcon.setBackgroundResource(leftBg)
        }
        val leftWidth = typedArray.getDimension(R.styleable.ZNormalSetItem_leftIconWidth, 0f)
        val leftHeight = typedArray.getDimension(R.styleable.ZNormalSetItem_leftIconHeight, 0f)
        if (leftHeight > 0 && leftWidth > 0) {
            val leftlayoutParms: ConstraintLayout.LayoutParams = ivLeftIcon.layoutParams as ConstraintLayout.LayoutParams
            leftlayoutParms.height = leftHeight.toInt()
            leftlayoutParms.width = leftWidth.toInt()
            ivLeftIcon.layoutParams = leftlayoutParms
        }

        val rightIcon = typedArray.getResourceId(R.styleable.ZNormalSetItem_rightIcon, 0)
        if (rightIcon != 0) {
            ivRightIcon.setImageResource(rightIcon)
        }
        val rightBg = typedArray.getResourceId(R.styleable.ZNormalSetItem_rightIconBackground,0)
        if(rightBg!=0){
            ivRightIcon.setBackgroundResource(rightBg)
        }
        val rightWidth = typedArray.getDimension(R.styleable.ZNormalSetItem_rightIconWidth, 0f)
        val rightHeight = typedArray.getDimension(R.styleable.ZNormalSetItem_rightIconHeight, 0f)
        if (rightWidth > 0 && rightHeight > 0) {
            val rightlayoutParms: ConstraintLayout.LayoutParams = ivRightIcon.layoutParams as ConstraintLayout.LayoutParams
            rightlayoutParms.height = rightHeight.toInt()
            rightlayoutParms.width = rightWidth.toInt()
            ivRightIcon.layoutParams = rightlayoutParms
        }


        val rightLabelIcon = typedArray.getResourceId(R.styleable.ZNormalSetItem_rightLabelIcon, 0)
        if (rightLabelIcon != 0) {
            ivRightContentLabel.setImageResource(rightLabelIcon)
        }
        val rightLabelBg = typedArray.getResourceId(R.styleable.ZNormalSetItem_rightLabelBackground,0)
        if(rightLabelBg!=0){
            ivRightContentLabel.setBackgroundResource(rightLabelBg)
        }
        val rightLabelWidth = typedArray.getDimension(R.styleable.ZNormalSetItem_rightLabelWidth, 0f)
        val rightLabelHeight = typedArray.getDimension(R.styleable.ZNormalSetItem_rightLabelHeight, 0f)
        if (rightLabelWidth > 0 && rightLabelHeight > 0) {
            val rightLabellayoutParms: ConstraintLayout.LayoutParams = ivRightContentLabel.layoutParams as ConstraintLayout.LayoutParams
            rightLabellayoutParms.height = rightLabelHeight.toInt()
            rightLabellayoutParms.width = rightLabelWidth.toInt()
            ivRightContentLabel.layoutParams = rightLabellayoutParms
        }

        val leftContentText = typedArray.getText(R.styleable.ZNormalSetItem_leftText)
        tvLeftContent.text = leftContentText
        val leftTextColor = typedArray.getColorStateList(R.styleable.ZNormalSetItem_leftTextColor)
        leftTextColor?.let {
            tvLeftContent.setTextColor(it)
        }
        val leftContentSize = typedArray.getDimension(R.styleable.ZNormalSetItem_leftTextSize,0f)
        if(leftContentSize > 0){
            tvLeftContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,leftContentSize)
        }

        val rightContentText = typedArray.getText(R.styleable.ZNormalSetItem_rightText)
        tvRightContent.text = rightContentText
        val rightTextColor = typedArray.getColorStateList(R.styleable.ZNormalSetItem_rightTextColor)
        rightTextColor?.let {
            tvRightContent.setTextColor(it)
        }
        val rightContentSize = typedArray.getDimension(R.styleable.ZNormalSetItem_rightTextSize,0f)
        if(rightContentSize > 0){
            tvRightContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,rightContentSize)
        }

        typedArray.recycle()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.layout_z_normal_setting_item, this)
    }

    fun leftIconView(): ImageView {
        return ivLeftIcon
    }

    fun leftContentView(): TextView {
        return tvLeftContent
    }

    fun rightIconView(): ImageView {
        return ivRightIcon
    }

    fun rightContentView(): TextView {
        return tvRightContent
    }

    fun rightContentLabelImage(): ImageView {
        return ivRightContentLabel
    }
}