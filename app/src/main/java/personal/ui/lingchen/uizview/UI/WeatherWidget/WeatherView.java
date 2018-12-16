package personal.ui.lingchen.uizview.UI.WeatherWidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.GridLayout;

import java.util.List;

import personal.ui.lingchen.uizview.R;

/**
 * author：zach
 * date: 2018/12/16 14:19
 * ProjectName: UIZView
 * PackageName: personal.ui.lingchen.uizview.UI
 * Description:
 */
public class WeatherView extends View {

    private int separatorLineWidth = dpToPx(2);//分隔线宽度
    private int separatorLineColor = Color.WHITE;//分隔线颜色
    private int brokenLineWidth = dpToPx(2);//折线宽度
    private int brokenLinewColor = Color.WHITE;//折线颜色
    private int textColor = Color.WHITE;//文本颜色
    private Paint mPaint;//画笔
    private float columnWidth = 0;//每一列的宽度
    private float rowHeight = 0;//每一行的高度
    private int pointRadius = dpToPx(5);//数据点半径
    private int mWidth;//整体宽度
    private int mHeight;//整体高度
    private int marginTop = dpToPx(10);//上边距
    private int marginBottom = dpToPx(10);//下边距
    private int textHeight = 0;//字体高度
    private int textWidth = 0;//字体宽度

    //行高百分比
//    private int[] rowHeightPer = {1, 1, 2, 2, 2, 3};
//    private float minRowHeight = 0;
    private WeatherBean[] weatherData;// = new WeatherBean[5];
    private RangeValue rangeValue;//天气数据上下限

    private String[] leftLabel = {"时间", "天气", "温度", "湿度", "雨量", "风向", "风力"};
    private Bitmap[] weatherBitmaps;

    BitmapFactory.Options options = new BitmapFactory.Options();

    public WeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void setData(WeatherBean[] data) {
        if (data.length < 5) {
            throw new RuntimeException("天气数据最少需要5个");
        }
        weatherData = data;
        rangeValue = calculateRangeFromArray(weatherData);
        loadWeatherBitmaps();
        this.postInvalidate();
    }

    /**
     * 加载天气图片
     */
    private void loadWeatherBitmaps() {
        if (weatherBitmaps == null) {
            weatherBitmaps = new Bitmap[5];
        }

        for (int i = 0; i < 5; i++) {
            if (weatherBitmaps[i] != null && !weatherBitmaps[i].isRecycled()) {
                weatherBitmaps[i].recycle();
            }
            weatherBitmaps[i] = BitmapFactory.decodeResource(getResources(), weatherData[i].getWeatherIconResId(), options);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        for (int i = 0; i < weatherBitmaps.length; i++) {
            if (!weatherBitmaps[i].isRecycled()) {
                weatherBitmaps[i].recycle();
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        calculateData();
        options.inScaled = true;
        options.outWidth = (int) (columnWidth * 2.0f / 3);
        options.outHeight = (int) (columnWidth * 2.0f / 3);
    }

    /**
     * 计算初始化数据
     */
    private void calculateData() {
        columnWidth = mWidth / 6.0f;
        rowHeight = mHeight / 6.0f;
        int rowPerTotal = 0;
//        for (int i = 0; i < rowHeightPer.length; i++) {
//            rowPerTotal += rowHeightPer[i];
//        }
//
//        minRowHeight = rowPerTotal != 0 ? mHeight / rowPerTotal : mHeight/6.0f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg();
        drawSeparator(canvas);
        drawLeftLabel(canvas);
        drawPointAndValue(canvas);
    }

    /**
     * 初始化
     */
    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);

        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(brokenLineWidth);
        mPaint.setTextSize(dpToPx(15));
        textHeight = getFontHeight(mPaint);

//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.weather);

    }

    /**
     * 绘制背景
     */
    private void drawBg() {

    }

    /**
     * 绘制分隔线
     */
    private void drawSeparator(Canvas canvas) {
        mPaint.setStrokeWidth(3);
        mPaint.setColor(separatorLineColor);
        for (int i = 0; i < 5; i++) {
            canvas.drawLine(columnWidth * (i + 1), marginTop, columnWidth * (i + 1), mHeight - marginBottom, mPaint);
        }

        //水平分隔线
//        for (int i = 0; i < 5; i++) {
//            canvas.drawLine(0, rowHeight * (i + 1), mWidth, rowHeight * (i + 1), mPaint);
//        }
    }

    /**
     * 绘制标签
     */
    private void drawLeftLabel(Canvas canvas) {
        mPaint.setColor(textColor);
        int orgY = (int) ((rowHeight + textHeight) / 2);
        textWidth = getStringWidth(mPaint, leftLabel[0]);
        int orgX = (int) ((columnWidth - textWidth) / 2);
        for (int i = 0; i < 6; i++) {
            float y = orgY + rowHeight * i;
            if (i == 5) {
                y -= textHeight * 4 / 5;
            }
            canvas.drawText(leftLabel[i], orgX, y, mPaint);
        }
        canvas.drawText(leftLabel[6], orgX, orgY + rowHeight * 5 + textHeight * 1 / 5, mPaint);
    }

    /**
     * 绘制数据点
     *
     * @param canvas
     */
    private void drawPointAndValue(Canvas canvas) {
        if (weatherData != null && weatherData.length > 0) {
            drawRowData(canvas);
        }
    }

    /**
     * 绘制行数据
     *
     * @param canvas
     */
    private void drawRowData(Canvas canvas) {
//        int orgX = (int) (columnWidth + columnWidth / 2);
        if (weatherData != null && weatherData.length >= 5) {
            mPaint.setColor(textColor);
            //绘制时间
            int tH = getFontHeight(mPaint);
            int orgY = (int) ((rowHeight + tH) / 2);
            for (int i = 0; i < 5; i++) {
                int tW = getStringWidth(mPaint, weatherData[i].getTime());
                canvas.drawText(weatherData[i].getTime(), (columnWidth - tW) / 2 + columnWidth * (i + 1), orgY, mPaint);
            }

            //绘制天气

            if (rangeValue != null) {

                Path[] linPaths = new Path[3];
                int rangeTem = rangeValue.maxTemperature - rangeValue.minTemperature;
                int rangeHum = rangeValue.maxHumidity - rangeValue.minHumidity;
                float rangeRain = rangeValue.maxRainfall - rangeValue.minRainfall;
                float y0 = rowHeight * 3 - tH;//温度最低点绘制y坐标

                float rawRowH = rowHeight - pointRadius * 2 - tH;
                for (int i = 0; i < 5; i++) {

                    float cx = columnWidth / 2 + columnWidth * (i + 1);
                    //绘制天气图标
//                    Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.weather);
                    if (weatherBitmaps != null) {
                        RectF dstRect = new RectF();
                        dstRect.left = cx - columnWidth / 3;
                        dstRect.bottom = rowHeight * 1.5f + columnWidth / 3;
                        dstRect.right = cx + columnWidth / 3;
                        dstRect.top = rowHeight * 1.5f - columnWidth / 3;
                        canvas.drawBitmap(weatherBitmaps[i], new Rect(0, 0, weatherBitmaps[i].getWidth(), weatherBitmaps[i].getHeight()), dstRect, mPaint);
                    }
                    float cy = rowHeight * 2.5f;
                    //绘制温度曲线
                    if (rangeTem != 0) {
                        cy = y0 - (weatherData[i].getTemperature() - rangeValue.minTemperature) * rawRowH / rangeTem;
                    }
                    canvas.drawCircle(cx, cy, pointRadius, mPaint);
                    String tempText = String.format("%d℃", weatherData[i].getTemperature());
                    int widht = getStringWidth(mPaint, tempText);
                    canvas.drawText(tempText, (columnWidth - widht) / 2 + columnWidth * (i + 1), cy + tH, mPaint);
                    if (i == 0) {
                        linPaths[0] = new Path();
                        linPaths[0].moveTo(cx, cy);
                    } else {
                        linPaths[0].lineTo(cx, cy);
                    }

                    //绘制湿度曲线
                    cy = rowHeight * 3.5f;
                    if (rangeHum != 0) {
                        cy = y0 + rowHeight - (weatherData[i].getHumidity() - rangeValue.minHumidity) * rawRowH / rangeHum;
                    }
                    canvas.drawCircle(cx, cy, pointRadius, mPaint);
                    String humText = String.format("%d%%", weatherData[i].getHumidity());
                    int humWidth = getStringWidth(mPaint, humText);
                    canvas.drawText(humText, (columnWidth - humWidth) / 2 + columnWidth * (i + 1), cy + tH, mPaint);
                    if (i == 0) {
                        linPaths[1] = new Path();
                        linPaths[1].moveTo(cx, cy);
                    } else {
                        linPaths[1].lineTo(cx, cy);
                    }


                    //绘制雨量曲线
                    cy = rowHeight * 4.5f;
                    if (rangeRain > 0.001f) {
                        cy = y0 + rowHeight * 2 - (weatherData[i].getRainfall() - rangeValue.minRainfall) * rawRowH / rangeRain;
                    }
                    canvas.drawCircle(cx, cy, pointRadius, mPaint);
                    String rainText = String.format("%.1fmm", weatherData[i].getRainfall());
                    int rainWidth = getStringWidth(mPaint, rainText);
                    canvas.drawText(rainText, (columnWidth - rainWidth) / 2 + columnWidth * (i + 1), cy + tH, mPaint);
                    if (i == 0) {
                        linPaths[2] = new Path();
                        linPaths[2].moveTo(cx, cy);
                    } else {
                        linPaths[2].lineTo(cx, cy);
                    }

                    //绘制风力风向
                    cy = rowHeight * 5.5f - textHeight * 0.3f;
                    int windDX = getStringWidth(mPaint, weatherData[i].getWindDirection());
                    canvas.drawText(weatherData[i].getWindDirection(), cx - windDX / 2, cy, mPaint);
                    windDX = getStringWidth(mPaint, weatherData[i].getWindForce());
                    canvas.drawText(weatherData[i].getWindForce(), cx - windDX / 2, rowHeight * 5.5f + 0.7f * textHeight, mPaint);
                }
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(brokenLinewColor);
                canvas.drawPath(linPaths[0], mPaint);
                canvas.drawPath(linPaths[1], mPaint);
                canvas.drawPath(linPaths[2], mPaint);
//                mPaint.setStyle(Paint.Style.FILL);
            }
        }
    }

    /**
     * 折线图数据上下范围
     */
    private class RangeValue {
        int minTemperature = 0;
        int maxTemperature = 0;
        int minHumidity = 0;
        int maxHumidity = 0;
        float minRainfall = 0.0f;
        float maxRainfall = 0.0f;
    }

    /**
     * 计算折现图上下限
     *
     * @param data
     * @return
     */
    private RangeValue calculateRangeFromArray(WeatherBean[] data) {
        if (data != null && data.length >= 5) {
            RangeValue rangeValue = new RangeValue();
            rangeValue.maxHumidity = data[0].getHumidity();
            rangeValue.minHumidity = data[0].getHumidity();
            rangeValue.minRainfall = data[0].getRainfall();
            rangeValue.maxRainfall = data[0].getRainfall();
            rangeValue.maxTemperature = data[0].getTemperature();
            rangeValue.minTemperature = data[0].getTemperature();
            for (int i = 1; i < data.length; i++) {
                if (rangeValue.maxTemperature < data[i].getTemperature()) {
                    rangeValue.maxTemperature = data[i].getTemperature();
                } else if (rangeValue.minTemperature > data[i].getTemperature()) {
                    rangeValue.minTemperature = data[i].getTemperature();
                }

                if (rangeValue.maxRainfall < data[i].getRainfall()) {
                    rangeValue.maxRainfall = data[i].getRainfall();
                } else if (rangeValue.minRainfall > data[i].getRainfall()) {
                    rangeValue.minRainfall = data[i].getRainfall();
                }

                if (rangeValue.maxHumidity < data[i].getHumidity()) {
                    rangeValue.maxHumidity = data[i].getHumidity();
                } else if (rangeValue.minHumidity > data[i].getHumidity()) {
                    rangeValue.minHumidity = data[i].getHumidity();
                }
            }
            return rangeValue;
        }
        return null;
    }


    protected int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
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
