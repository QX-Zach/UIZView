package personal.ui.lingchen.uizview.UI.RulerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.VelocityTracker;
import android.view.View;

/**
 * Created by ozner_67 on 2017/10/19.
 * 邮箱：xinde.zhang@cftcn.com
 *
 */

public class RulerView extends View {
    private static final String TAG = "RulerView";
    /**
     * 尺子高度
     */
    private int rulerHeight = 30;

    /**
     * 尺子和屏幕顶部以及结果之间的高度
     */
    private int rulerToResultgap = rulerHeight/4;

    /**
     * 刻度平分多少份
     */
    private int scaleCount = 10;
    /**
     * 刻度间距
     */
    private int scaleGap = 20;
    /**
     * 刻度最小值
     */
    private int minScale = 0;
    /**
     * 第一次显示的刻度
     */
    private float firstScale = 50f;
    /**
     * 刻度最大值
     */
    private int maxScale = 100;
    /**
     * 背景颜色
     */
    private int bgColor = 0xfffcfffc;
    /**
     * 中刻度颜色
     */
    private int midScaleColor = 0xff666666;
    /**
     * 小刻度颜色
     */
    private int smallScaleColor = 0xff999999;
    /**
     * 大刻度颜色
     */
    private int largeScaleColor = 0xff50b586;
    /**
     * 刻度颜色
     */
    private int scaleNumColor = 0xff333333;
    /**
     * 结果值颜色
     */
    private int resultNumColor = 0xff50b586;
    /**
     * 单位
     */
    private String unit = "kg";
    /**
     * 单位颜色
     */
    private int unitColor = 0xff50b586;
    /**
     * 小刻度粗细大小
     */
    private int smallScaleStroke = 1;
    /**
     * 中刻度粗细大小
     */
    private int midScaleStroke = 2;
    /**
     * 大刻度粗细大小
     */
    private int largeScaleStroke = 3;
    /**
     * 结果字体大小
     */
    private int resultNumTextSize = 20;
    /**
     * 刻度字体大小
     */
    private int scaleNumTextSize = 16;
    /**
     * 单位字体大小
     */
    private  int unitTextSize = 13;

    /**
     * 是否显示刻度结果
     */
    private boolean showScaleResult = true;
    /**
     * 是否背景显示圆角
     */
    private boolean isBgRoundRect = true;

    /**
     * 结果回调
     */
    private onChooseResultListener onChooseResulterListener;
    private VelocityTracker velocityTracker = VelocityTracker.obtain();


    /**
     * 结果回调接口
     */
    public interface onChooseResultListener{
        void onEndResult(String result);
        void onScrollResult(String reuslt);
    }
    public RulerView(Context context) {
        super(context);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
