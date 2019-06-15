package personal.ui.lingchen.uizview.UI.PolygonalChart;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * @author：zach
 * @Date: 2019-06-13 17:08
 * @Email: xinde.zhang@cftcn.com
 * @ProjectName: UIZView
 * @PackageName: personal.ui.lingchen.uizview.UI.UIZPolygonalChart
 * @Description:
 */
public class UIZPolygonalChart extends View {
    //设置默认的宽和高
    private static final int DEFUALT_VIEW_WIDTH = 300;
    private static final int DEFUALT_VIEW_HEIGHT = 100;
    //标签空间
    private int DATE_HEIGHT = dpToPx(15);
    private int mWidth, mHeight;
    private Paint gridPaint, valuePaint;
    private int gridColor = Color.BLUE;
    private int tagColor = Color.BLUE;
    private int graintStartColor = 0xee00ffff;
    private int graintEndColor = 0x55ffffff;
    private int dayCount = 31;
    private int maxValue = 100;
    private List<TagInfo> tagInfos;
    private float tagMaxWidth = 0;
    private Calendar currentMonth = Calendar.getInstance();
    private Path valuePath;
    private List<ValueInfo> valueInfos;
    private List<PointF> valuePoints;
    private LinearGradient linearGradient;

    /**
     * 数据对象，绘制折线图使用
     */
    public static final class ValueInfo {
        public int dayOfMonth;
        public float value;
    }

    /**
     * 标签及线条信息
     */
    public static final class TagInfo {
        public String tag;
        public int tagPercent;
        public int valueColor;
        public int lineColor;
    }

    /**
     * 设置最大值
     *
     * @param max
     */
    public void setMaxValue(int max) {
        maxValue = max;
        invalidate();
    }

    public void setMonth(int month) {
        if (month > 11) {
            return;
        }

        currentMonth.set(Calendar.MONTH, month);
        dayCount = getCurrentMonthDayCount(currentMonth);
        invalidate();
    }

    /**
     * 设置标签信息
     *
     * @param infos
     */
    public void setTagInfo(List<TagInfo> infos) {
        if (null == tagInfos) {
            tagInfos = new ArrayList<>();
        }
        tagInfos.clear();
        tagInfos.addAll(infos);
        invalidate();
    }

    public UIZPolygonalChart(Context context) {
        super(context);
        init();
    }

    public UIZPolygonalChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UIZPolygonalChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private int getCurrentMonthDayCount(Calendar calendar) {
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }


    private void init() {
        valuePath = new Path();
        valueInfos = new ArrayList<>();
        tagInfos = new ArrayList<>();
        valuePoints = new ArrayList<>();
        initDefaultInfo();
        dayCount = getCurrentMonthDayCount(currentMonth);
        gridPaint = new Paint();
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(1);
        gridPaint.setTextSize(dpToPx(13));
        gridPaint.setStrokeCap(Paint.Cap.ROUND);
        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setStrokeWidth(dpToPx(1));
        valuePaint.setStrokeCap(Paint.Cap.ROUND);
        DATE_HEIGHT = getFontHeight(gridPaint);
        test();
    }

    private void test() {
        Random random = new Random();
        for (int i = 0; i < dayCount; i++) {
            ValueInfo info = new ValueInfo();
            info.value = random.nextInt(60) + 30;
            info.dayOfMonth = i;
            valueInfos.add(info);
        }
    }


    /**
     * 初始化默认标签
     */
    private void initDefaultInfo() {
        TagInfo tagInfo50 = new TagInfo();
        tagInfo50.lineColor = Color.RED;
        tagInfo50.valueColor = Color.RED;
        tagInfo50.tagPercent = 50;
        tagInfo50.tag = "50";
        tagInfos.add(tagInfo50);
        TagInfo tagInfo100 = new TagInfo();
        tagInfo100.lineColor = Color.GREEN;
        tagInfo100.valueColor = Color.GREEN;
        tagInfo100.tagPercent = 100;
        tagInfo100.tag = "100";
        tagInfos.add(tagInfo100);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = measureDimension(DEFUALT_VIEW_WIDTH, widthMeasureSpec);
        int height = measureDimension(DEFUALT_VIEW_HEIGHT, heightMeasureSpec);
        //将计算的宽和高设置进去，保存，最后一步一定要有
        setMeasuredDimension(width, height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    /**
     * @param defualtSize 设置的默认大小
     * @param measureSpec 父控件传来的widthMeasureSpec，heightMeasureSpec
     * @return 结果
     */
    public int measureDimension(int defualtSize, int measureSpec) {
        int result = defualtSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        //1,layout中自定义组件给出来确定的值，比如100dp
        //2,layout中自定义组件使用的是match_parent，但父控件的size已经可以确定了，比如设置的具体的值或者match_parent
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        //layout中自定义组件使用的wrap_content
        else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defualtSize, specSize);//建议：result不能大于specSize
        }
        //UNSPECIFIED,没有任何限制，所以可以设置任何大小
        else {
            result = defualtSize;
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

    }

    /**
     * 网格竖线偏移
     */
    private float verticalLineOffset = 0;
    private float grideBaseLineY = 0;
    private float verticalLineTotalWidth = 0;

    private void cacluteDefaultData() {
        grideBaseLineY = mHeight - DATE_HEIGHT;

    }

    private float step = dpToPx(2);

    /**
     * 绘制网格及日期标签
     *
     * @param canvas
     */
    private void drawGrid(Canvas canvas) {
        gridPaint.setColor(gridColor);
        int offset = dpToPx(20);
        canvas.drawLine(tagMaxWidth + getPaddingLeft(), mHeight - DATE_HEIGHT
                , mWidth - getPaddingRight(), mHeight - DATE_HEIGHT, gridPaint);

        float gridWidth = mWidth - offset * 2 - tagMaxWidth - getPaddingLeft() - getPaddingRight();
        step = gridWidth * 1.0f / (dayCount - 1);
        String firstDate = String.format(Locale.CHINA, "%d月%d", currentMonth.get(Calendar.MONTH) + 1, 1);

        for (int i = 0; i < dayCount; i++) {
            float xOffset = tagMaxWidth + offset + step * i;
            canvas.drawLine(xOffset, DATE_HEIGHT / 2, xOffset, mHeight - DATE_HEIGHT, gridPaint);
            float halfNumWidth = gridPaint.measureText(String.valueOf(i + 1)) / 2.0f;
            if (0 == i) {
                float fistWidth = gridPaint.measureText(firstDate);
                canvas.drawText(firstDate, xOffset - fistWidth + halfNumWidth, mHeight - dpToPx(5), gridPaint);
            } else {
                canvas.drawText(String.valueOf(i + 1), xOffset - halfNumWidth, mHeight - dpToPx(5), gridPaint);
            }
        }
    }


    /**
     * 绘制标签及其水平线
     *
     * @param canvas
     */
    private void drawTagAndLines(Canvas canvas) {
        float halfTagHeight = DATE_HEIGHT / 3.0f;
        float yStartP = mHeight - DATE_HEIGHT;
        float tagStep = (yStartP - DATE_HEIGHT / 2 - dpToPx(10)) * 1.0f / maxValue;
        for(int i=0;i<tagInfos.size();i++){
            float width = gridPaint.measureText(tagInfos.get(i).tag);
            if (tagMaxWidth < width) {
                tagMaxWidth = width;
            }
        }
        for (int i = 0; i < tagInfos.size(); i++) {
            float tagWidth = gridPaint.measureText(tagInfos.get(i).tag);

            float y = yStartP - tagStep * tagInfos.get(i).tagPercent;
            gridPaint.setColor(tagInfos.get(i).valueColor);
            canvas.drawText(tagInfos.get(i).tag, getPaddingLeft() + tagMaxWidth - tagWidth, y + halfTagHeight, gridPaint);
            gridPaint.setColor(tagInfos.get(i).lineColor);
            canvas.drawLine(tagMaxWidth + getPaddingLeft() + dpToPx(3), y, mWidth - getPaddingRight(), y, gridPaint);
        }
    }

    /**
     * 计算每个点的位置
     *
     * @param listValues
     */
    private void caculateValuePos(List<ValueInfo> listValues) {
        float xDefaultOffset = tagMaxWidth + dpToPx(20);
        float valueStep = (mHeight - DATE_HEIGHT * 1.5f - dpToPx(10)) * 1.0f / maxValue;
        float yStartP = mHeight - DATE_HEIGHT;
        valuePoints.clear();
        for (int i = 0; i < listValues.size(); i++) {
            ValueInfo valueInfo = listValues.get(i);
            PointF pointF = new PointF();
            pointF.x = xDefaultOffset + step * valueInfo.dayOfMonth;
            pointF.y = yStartP - valueStep * valueInfo.value;
            valuePoints.add(pointF);
        }
    }

    private void drawLine(Canvas canvas){
        Path path = new Path();
    }

    /**
     * 绘制曲线
     *
     * @param canvas
     */
    private void drawValueLines(Canvas canvas) {
        valuePath.reset();
        if (valueInfos == null || valueInfos.size() < 2) {
            return;
        }
        caculateValuePos(valueInfos);

        valuePath.moveTo(tagMaxWidth + getPaddingLeft() + dpToPx(4), valuePoints.get(0).y);
        float topY = valuePoints.get(0).y;

        for (int i = 0; i < valuePoints.size(); i++) {
            if (topY > valuePoints.get(i).y) {
                topY = valuePoints.get(i).y;
            }
            // TODO: 2019-06-15 绘制三阶贝塞尔曲线
            valuePath.lineTo(valuePoints.get(i).x, valuePoints.get(i).y);
        }

        valuePath.lineTo(mWidth - getPaddingRight(), valuePoints.get(valuePoints.size() - 1).y);

        //闭合曲线
        valuePath.lineTo(mWidth - getPaddingRight(), mHeight - DATE_HEIGHT - dpToPx(1));
        valuePath.lineTo(tagMaxWidth + getPaddingLeft() + dpToPx(4), mHeight - DATE_HEIGHT - dpToPx(1));
        valuePath.close();

        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        valuePaint.setColor(0xff00ffff);
        linearGradient = new LinearGradient(0, topY, 0, mHeight - DATE_HEIGHT, graintStartColor, graintEndColor, Shader.TileMode.CLAMP);
        valuePaint.setShader(linearGradient);

        canvas.drawPath(valuePath, valuePaint);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTagAndLines(canvas);
        drawGrid(canvas);
        drawValueLines(canvas);
    }


    protected int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    //  获取字体高度
    protected int getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }
}
