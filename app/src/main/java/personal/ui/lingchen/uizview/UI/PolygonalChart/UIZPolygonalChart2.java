package personal.ui.lingchen.uizview.UI.PolygonalChart;

import android.content.Context;
import android.content.res.TypedArray;
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

import personal.ui.lingchen.uizview.R;

/**
 * @author：zach
 * @Date: 2019-06-15 13:35
 * @Email: xinde.zhang@cftcn.com
 * @ProjectName: UIZView
 * @PackageName: personal.ui.lingchen.uizview.UI.PolygonalChart
 * @Description:
 * 使用顺序：
 * 0、准备：调用设置参数方法，设置各个颜色值，并调用apply方法刷新
 * 1、调用setCurrentMonth设置当前月份，从1开始；
 * 2、调用setValueInfo设置数据
 */
public class UIZPolygonalChart2 extends View {
    //设置默认的宽和高
    private final int DEFUALT_VIEW_WIDTH = 300;
    private final int DEFUALT_VIEW_HEIGHT = 100;
    private int mWidth, mHeight;
    private Paint gridPaint, valuePaint, textPaint, valueLinePaint;
    private int valueLineColor = 0;
    private int gridColor = 0xFF7A90B6;
    private int dateColor = 0xFF7A90B6;
    private int defaultTagColor = 0xFF7A90B6;
    private int graintStartColor = 0x22ff0000;
    private int graintEndColor = 0x1100ff00;
    private int dayCount = 31;
    private int maxValue = 100;
    private List<TagInfo> tagInfos;
    private float tagMaxWidth = 0;
    private Calendar currentMonth;
    private Path valuePath;
    private List<ValueInfo> valueInfos;
    private List<PointF> valuePoints;
    private LinearGradient linearGradient;
    private float dateTextSize = dpToPx(13);
    private float tagTextSize = dpToPx(12);
    private final float defaultSpace = dpToPx(2);
    /**
     * 网格基线左边x坐标
     */
    private float mBaseLineLeftX;
    private float mBaseLineY;
    /**
     * 网格竖线总宽度
     */
    private float verticalLineSpanWidth;

    /**
     * 日期字体高度
     */
    private float dateFontHeight;
    private float tagFontHeight;

    private float firstDateWidth;
    private float dateStepWidth;
    private boolean showValueLine = false;

    public UIZPolygonalChart2 setGridColor(int color) {
        this.gridColor = color;
        return this;
    }

    public UIZPolygonalChart2 setDateColor(int color) {
        this.dateColor = color;
        return this;
    }

    public UIZPolygonalChart2 setGraintStartColor(int color) {
        this.graintStartColor = color;
        return this;
    }

    public UIZPolygonalChart2 setGraintEndColor(int color) {
        this.graintEndColor = color;
        return this;
    }

    public UIZPolygonalChart2 setValueLineColor(int color) {
        this.valueLineColor = color;
        return this;
    }

    /**
     * 设置最大值
     *
     * @param max
     */
    public UIZPolygonalChart2 setMaxValue(int max) {
        this.maxValue = max;
        return this;
    }

    public UIZPolygonalChart2 setCurrentMonth(int month) {
        if (month < 1) {
            month = 1;
        }
        if (month > 12) {
            month = 12;
        }

        currentMonth.set(Calendar.MONTH, month - 1);
        dayCount = getCurrentMonthDayCount(currentMonth);
        return this;
    }

    public void apply() {
        this.invalidate();
    }

    /**
     * 设置标签信息
     *
     * @param infos
     */
    public void setTagInfo(List<TagInfo> infos) {
        if (infos == null || infos.isEmpty()) {
            return;
        }
        if (null == tagInfos) {
            tagInfos = new ArrayList<>();
        }
        tagInfos.clear();
        tagInfos.addAll(infos);
        invalidate();
    }

    public void setValueInfos(List<ValueInfo> infos) {
        if (null == valueInfos) {
            valueInfos = new ArrayList<>();
        }
        valueInfos.clear();
        if (null != infos && !infos.isEmpty()) {
            valueInfos.addAll(infos);
        }
        invalidate();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        textPaint.setTextSize(dateTextSize);
        dateFontHeight = getFontHeight(textPaint);
        textPaint.setTextSize(tagTextSize);
        tagFontHeight = getFontHeight(textPaint);
        mBaseLineY = mHeight - getPaddingBottom() - dateFontHeight;
    }

    private float valueStepHeight;

    /**
     * 绘制标签,并根据字体高度
     *
     * @param canvas
     */
    private void drawTagAndLine(Canvas canvas) {
        float halfHeight = tagFontHeight / 3.0f;
        textPaint.setTextSize(tagTextSize);
        for (int i = 0; i < tagInfos.size(); i++) {
            float width = textPaint.measureText(tagInfos.get(i).tag);
            tagInfos.get(i).tagTextWidth = width;
            if (tagMaxWidth < width) {
                tagMaxWidth = width;
            }
        }

        mBaseLineLeftX = tagMaxWidth + getPaddingLeft() + defaultSpace;
        float zeroTagWidth = textPaint.measureText("0");
        textPaint.setColor(defaultTagColor);
        canvas.drawText("0", mBaseLineLeftX - defaultSpace - zeroTagWidth
                , mBaseLineY + halfHeight, textPaint);
        valueStepHeight = (mBaseLineY - halfHeight) / maxValue;
        for (int i = 0; i < tagInfos.size(); i++) {
            float lineY = mBaseLineY - tagInfos.get(i).tagPercent * valueStepHeight;
            textPaint.setColor(tagInfos.get(i).valueColor == 0 ? defaultTagColor : tagInfos.get(i).valueColor);
            canvas.drawText(tagInfos.get(i).tag, mBaseLineLeftX - defaultSpace - tagInfos.get(i).tagTextWidth
                    , lineY + halfHeight, textPaint);
            textPaint.setColor(tagInfos.get(i).lineColor == 0 ? gridColor : tagInfos.get(i).lineColor);
            canvas.drawLine(mBaseLineLeftX, lineY, mWidth - getPaddingRight(), lineY, textPaint);
        }
    }

    /**
     * 绘制网格
     *
     * @param canvas
     */
    private void drawGride(Canvas canvas) {
        gridPaint.setColor(gridColor);
        canvas.drawLine(mBaseLineLeftX, mBaseLineY, mWidth - getPaddingRight(), mBaseLineY, gridPaint);
        String firstDate = String.format(Locale.CHINA, "%d月%d", currentMonth.get(Calendar.MONTH) + 1, 1);
        textPaint.setColor(dateColor);
        textPaint.setTextSize(dateTextSize);
        firstDateWidth = textPaint.measureText(firstDate);
        verticalLineSpanWidth = mWidth - getPaddingRight() - mBaseLineLeftX - firstDateWidth * 2;
        dateStepWidth = verticalLineSpanWidth / (dayCount - 1);
        boolean showDate = true;
        if (dateStepWidth < textPaint.measureText("30")) {
            firstDateWidth = dpToPx(5);
            verticalLineSpanWidth = mWidth - getPaddingRight() - mBaseLineLeftX - firstDateWidth * 2;
            dateStepWidth = verticalLineSpanWidth / (dayCount - 1);
            showDate = false;
        }

        for (int i = 0; i < dayCount; i++) {
            float x = mBaseLineLeftX + firstDateWidth + dateStepWidth * i;
            canvas.drawLine(x, getPaddingTop(), x, mBaseLineY, gridPaint);
            if (showDate) {
                float halfNumWidth = textPaint.measureText(String.valueOf(i + 1)) / 2.0f;
                if (0 == i) {
                    canvas.drawText(firstDate, mBaseLineLeftX + halfNumWidth, mHeight - getPaddingBottom(), textPaint);
                } else {
                    canvas.drawText(String.valueOf(i + 1), x - halfNumWidth, mHeight - getPaddingBottom(), textPaint);
                }
            }
        }
    }


    /**
     * 计算每个点的位置
     *
     * @param listValues
     */
    private void caculateValuePos2(List<ValueInfo> listValues) {
        valuePoints.clear();
        for (int i = 0; i < listValues.size(); i++) {
            ValueInfo valueInfo = listValues.get(i);
            PointF pointF = new PointF();
            pointF.x = mBaseLineLeftX + firstDateWidth + dateStepWidth * valueInfo.dayOfMonth;
            pointF.y = mBaseLineY - valueInfo.value * valueStepHeight;
            valuePoints.add(pointF);
        }
    }

    /**
     * 绘制曲线
     *
     * @param canvas
     */
    private void drawValueLine(Canvas canvas) {
        valuePath.reset();
        if (valueInfos == null || valueInfos.size() < 2) {
            return;
        }
        caculateValuePos2(valueInfos);


        valuePath.moveTo(mBaseLineLeftX, valuePoints.get(0).y);

        float topY = valuePoints.get(0).y;

        for (int i = 0; i < valuePoints.size(); i++) {
            if (topY > valuePoints.get(i).y) {
                topY = valuePoints.get(i).y;
            }
            // TODO: 2019-06-15 绘制三阶贝塞尔曲线
            valuePath.lineTo(valuePoints.get(i).x, valuePoints.get(i).y);

        }
        valuePath.lineTo(mWidth - getPaddingRight(), valuePoints.get(valuePoints.size() - 1).y);
        /**
         * 绘制value曲线
         */
        if (showValueLine) {
            Path linePath = new Path(valuePath);
            if (valueLineColor == 0) {
                valueLineColor = graintStartColor;
            }
            valueLinePaint.setColor(valueLineColor);
            canvas.drawPath(linePath, valueLinePaint);
        }
        //闭合曲线
        valuePath.lineTo(mWidth - getPaddingRight(), mBaseLineY - dpToPx(1));
        valuePath.lineTo(mBaseLineLeftX, mBaseLineY - dpToPx(1));
        valuePath.close();

        valuePaint.setStyle(Paint.Style.FILL);
        valuePaint.setStrokeWidth(1);
        valuePaint.setStrokeJoin(Paint.Join.ROUND);
        linearGradient = new LinearGradient(0, topY, 0, mBaseLineY, graintStartColor, graintEndColor, Shader.TileMode.CLAMP);
        valuePaint.setShader(linearGradient);
        canvas.drawPath(valuePath, valuePaint);
    }


    /**
     * 数据对象，绘制折线图使用
     */
    public static final class ValueInfo {
        /**
         * 从0开始
         */
        public int dayOfMonth;
        /**
         * 当天的值，根据最大值限定
         */
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
        private float tagTextWidth;
    }


    public UIZPolygonalChart2(Context context) {
        super(context);
        init();
    }

    public UIZPolygonalChart2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UIZPolygonalChart2);
        gridColor = typedArray.getColor(R.styleable.UIZPolygonalChart2_uizGridColor, gridColor);
        dateColor = typedArray.getColor(R.styleable.UIZPolygonalChart2_uizDateColor, dateColor);
        defaultTagColor = typedArray.getColor(R.styleable.UIZPolygonalChart2_uizDefaultTagColor, defaultTagColor);
        graintStartColor = typedArray.getColor(R.styleable.UIZPolygonalChart2_uizGradientStartColor, graintStartColor);
        graintEndColor = typedArray.getColor(R.styleable.UIZPolygonalChart2_uizGradientEndColor, graintEndColor);
        maxValue = typedArray.getInt(R.styleable.UIZPolygonalChart2_uizMaxValue, maxValue);
        valueLineColor = typedArray.getColor(R.styleable.UIZPolygonalChart2_uizValueLineColor, valueLineColor);
        showValueLine = typedArray.getBoolean(R.styleable.UIZPolygonalChart2_uizShowValueLine, false);
        typedArray.recycle();
        init();
    }

    public UIZPolygonalChart2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        initDefaultTag();
        currentMonth = Calendar.getInstance();
        dayCount = getCurrentMonthDayCount(currentMonth);

        initPaint();
    }

    private void initPaint() {
        gridPaint = new Paint();
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(dpToPx(1));
        gridPaint.setTextSize(dpToPx(13));
        gridPaint.setStrokeCap(Paint.Cap.ROUND);
        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setStrokeWidth(dpToPx(1));
        valuePaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStrokeWidth(dpToPx(1));
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setTextSize(dpToPx(13));

        valueLinePaint = new Paint(valuePaint);
        valueLinePaint.setStyle(Paint.Style.STROKE);
        valueLinePaint.setStrokeWidth(dpToPx(1));
        valueLinePaint.setStrokeJoin(Paint.Join.ROUND);
    }

    /**
     * 初始化默认标签
     */
    private void initDefaultTag() {
        TagInfo tagInfo50 = new TagInfo();
        tagInfo50.lineColor = 0xFFEFAC1F;
        tagInfo50.valueColor = 0;
        tagInfo50.tagPercent = 50;
        tagInfo50.tag = "50";
        tagInfos.add(tagInfo50);
        TagInfo tagInfo100 = new TagInfo();
        tagInfo100.lineColor = 0xFFEB6D41;
        tagInfo100.valueColor = 0;
        tagInfo100.tagPercent = 100;
        tagInfo100.tag = "100";
        tagInfos.add(tagInfo100);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(dpToPx(DEFUALT_VIEW_WIDTH), widthSize);
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = dpToPx(DEFUALT_VIEW_WIDTH);
        } else {
            width = dpToPx(DEFUALT_VIEW_WIDTH);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(dpToPx(DEFUALT_VIEW_HEIGHT), heightSize);
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            height = dpToPx(DEFUALT_VIEW_HEIGHT);
        } else {
            height = dpToPx(DEFUALT_VIEW_HEIGHT);
        }
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTagAndLine(canvas);
        drawGride(canvas);
        drawValueLine(canvas);
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
