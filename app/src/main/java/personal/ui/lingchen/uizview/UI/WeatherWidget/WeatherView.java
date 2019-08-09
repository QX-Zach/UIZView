package personal.ui.lingchen.uizview.UI.WeatherWidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import personal.ui.lingchen.uizview.R;

/**
 * author：zach
 * date: 2018/12/16 14:19
 * ProjectName: UIZView
 * PackageName: personal.ui.lingchen.uizview.UI
 * Description:
 */
public class WeatherView extends View {
    private static final String TAG = "WeatherView";
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
    /**
     * 数据最大列数
     */
    private int COLUM_COUNT_SHOW = 5;

    //行高百分比
//    private int[] rowHeightPer = {1, 1, 2, 2, 2, 3};
//    private float minRowHeight = 0;
    private List<WeatherBean> weatherData;// = new WeatherBean[5];
    private RangeValue rangeValue;//天气数据上下限
    /**
     * 标签背景
     */
//    Bitmap labelBitmap;

    /**
     * 滑动偏移量，默认滑动到最右边为0,向左滑动值减小
     */
    private float scrollOffset = 0;
    private float minScrollX = 0;
    /**
     * 第一个数据点索引
     */
    private float defIndex = 0;

    private GestureDetector mGestureDetector;


    private String[] leftLabel = {"时间", "温度", "湿度", "雨量", "风速"};
//    private Bitmap[] weatherBitmaps;

//    BitmapFactory.Options options = new BitmapFactory.Options();

    public WeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void clear() {
        if (weatherData != null) {
            weatherData.clear();
        }
        this.invalidate();
    }


    public void setData(List<WeatherBean> data) {
        if (weatherData.size() == 0 && data.size() < COLUM_COUNT_SHOW) {
            throw new RuntimeException("天气数据最少需要" + COLUM_COUNT_SHOW + "个");
        }
        weatherData.addAll(0, data);
        //设置默认第一个数据点索引
        defIndex = weatherData.size() - COLUM_COUNT_SHOW;
        calculateRangeFromArray(data);
        this.postInvalidate();
    }

    /**
     * 加载天气图片
     */
//    private void loadWeatherBitmaps() {
//        if (weatherBitmaps == null) {
//            weatherBitmaps = new Bitmap[5];
//        }
//
//        for (int i = 0; i < 5; i++) {
//            if (weatherBitmaps[i] != null && !weatherBitmaps[i].isRecycled()) {
//                weatherBitmaps[i].recycle();
//            }
//            weatherBitmaps[i] = BitmapFactory.decodeResource(getResources(), weatherData[i].getWeatherIconResId(), options);
//        }
//    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        for (int i = 0; i < weatherBitmaps.length; i++) {
//            if (!weatherBitmaps[i].isRecycled()) {
//                weatherBitmaps[i].recycle();
//            }
//        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        calculateData();
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
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg();

        drawSeparator(canvas);

        drawPointAndValue(canvas);
        drawLeftLabel(canvas);
    }

    /**
     * 初始化
     */
    private void initView() {
        mGestureDetector = new GestureDetector(getContext(), new GestureListener());
        weatherData = new ArrayList<>();
        rangeValue = new RangeValue();
//        labelBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.weatherbg);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);

        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(brokenLineWidth);
        mPaint.setTextSize(dpToPx(15));
        textHeight = getFontHeight(mPaint);
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
        //绘制标签右边的分割线
        canvas.drawLine(columnWidth, marginTop, columnWidth, mHeight - marginBottom, mPaint);
        calculateLeftSstartX();
        //数据的分割线从第一个数据右边开始,并且增加滑动偏移量
        for (int i = 1; i < weatherData.size(); i++) {
            canvas.drawLine(leftStartX + columnWidth * (i + 1) + scrollOffset, marginTop
                    , leftStartX + columnWidth * (i + 1) + scrollOffset, mHeight - marginBottom
                    , mPaint);
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
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShader(new LinearGradient(0, 0, columnWidth, mHeight, new int[]{0xff775b8a, 0xff78b5d0}, null, Shader.TileMode.CLAMP));
        canvas.drawRect(0, 0, columnWidth, mHeight, mPaint);
        mPaint.setColor(textColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShader(null);
        int orgY = (int) ((rowHeight + textHeight) / 2);
        textWidth = getStringWidth(mPaint, leftLabel[0]);
        int orgX = (int) ((columnWidth - textWidth) / 2);

        for (int i = 0; i < 5; i++) {
            float y = orgY + rowHeight * i;
            canvas.drawText(leftLabel[i], orgX, y, mPaint);
        }

    }

    /**
     * 绘制数据点
     *
     * @param canvas
     */
    private void drawPointAndValue(Canvas canvas) {
        if (weatherData != null && weatherData.size() > 0) {
            drawRowData(canvas);
        }
    }

    private float leftStartX = 0;


    private void calculateLeftSstartX() {
        leftStartX = mWidth - columnWidth * (weatherData.size() + 1);
        minScrollX = leftStartX;
    }

    /**
     * 绘制行数据
     *
     * @param canvas
     */
    private void drawRowData(Canvas canvas) {

        if (weatherData != null && weatherData.size() >= COLUM_COUNT_SHOW) {
            calculateLeftSstartX();
            mPaint.setColor(textColor);
            mPaint.setStyle(Paint.Style.FILL);
            //绘制时间
            int tH = getFontHeight(mPaint);
            int orgY = (int) ((rowHeight + tH) / 2);
            for (int i = weatherData.size() - 1; i >= 0; i--) {
                int tW = getStringWidth(mPaint, weatherData.get(i).getTime());
                canvas.drawText(weatherData.get(i).getTime(), leftStartX + (columnWidth - tW) / 2 + columnWidth * (i + 1) + scrollOffset, orgY, mPaint);
            }

            //绘制天气
            if (rangeValue != null) {
                int dataSize = weatherData.size();
                Path[] linPaths = new Path[4];
                float rangeTem = rangeValue.maxTemperature - rangeValue.minTemperature;
                float rangeHum = rangeValue.maxHumidity - rangeValue.minHumidity;
                float rangeRain = rangeValue.maxRainfall - rangeValue.minRainfall;
                float rangeFeng = rangeValue.maxFengsu - rangeValue.minFengsu;
                //温度最低点绘制y坐标
                float y0 = rowHeight * 2 - tH;
                float rawRowH = rowHeight - pointRadius * 2 - tH;
                for (int i = dataSize - 1; i >= 0; i--) {

                    float cx = columnWidth / 2 + columnWidth * (i + 1);
                    float cy = rowHeight * 1.5f;
                    //绘制温度曲线
                    if (rangeTem != 0) {
                        cy = y0 - (weatherData.get(i).getWD() - rangeValue.minTemperature) * rawRowH / rangeTem;
                    }
                    canvas.drawCircle(leftStartX + cx + scrollOffset, cy, pointRadius, mPaint);
                    String tempText = String.format("%.1f℃", weatherData.get(i).getWD());
                    int widht = getStringWidth(mPaint, tempText);
                    canvas.drawText(tempText, leftStartX + (columnWidth - widht) / 2 + columnWidth * (i + 1) + scrollOffset, cy + tH, mPaint);
                    if (i == dataSize - 1) {
                        linPaths[0] = new Path();
                        linPaths[0].moveTo(leftStartX + cx + scrollOffset, cy);
                    } else {
                        linPaths[0].lineTo(leftStartX + cx + scrollOffset, cy);
                    }

                    //绘制湿度曲线
                    cy = rowHeight * 2.5f;
                    if (rangeHum != 0) {
                        cy = y0 + rowHeight - (weatherData.get(i).getSD() - rangeValue.minHumidity) * rawRowH / rangeHum;
                    }
                    canvas.drawCircle(leftStartX + cx + scrollOffset, cy, pointRadius, mPaint);
                    String humText = String.format("%.1f%%", weatherData.get(i).getSD());
                    int humWidth = getStringWidth(mPaint, humText);
                    canvas.drawText(humText, leftStartX + (columnWidth - humWidth) / 2 + columnWidth * (i + 1) + scrollOffset, cy + tH, mPaint);
                    if (i == dataSize - 1) {
                        linPaths[1] = new Path();
                        linPaths[1].moveTo(leftStartX + cx + scrollOffset, cy);
                    } else {
                        linPaths[1].lineTo(leftStartX + cx + scrollOffset, cy);
                    }


                    //绘制雨量曲线
                    cy = rowHeight * 3.5f;
                    if (rangeRain > 0.001f) {
                        cy = y0 + rowHeight * 2 - (weatherData.get(i).getYL() - rangeValue.minRainfall) * rawRowH / rangeRain;
                    }
                    canvas.drawCircle(leftStartX + cx + scrollOffset, cy, pointRadius, mPaint);
                    String rainText = String.format("%.1fmm", weatherData.get(i).getYL());
                    int rainWidth = getStringWidth(mPaint, rainText);
                    canvas.drawText(rainText, leftStartX + (columnWidth - rainWidth) / 2 + columnWidth * (i + 1) + scrollOffset, cy + tH, mPaint);
                    if (i == dataSize - 1) {
                        linPaths[2] = new Path();
                        linPaths[2].moveTo(leftStartX + cx + scrollOffset, cy);
                    } else {
                        linPaths[2].lineTo(leftStartX + cx + scrollOffset, cy);
                    }

                    //绘制风速
                    cy = rowHeight * 4.5f;
                    if (rangeFeng != 0) {
                        cy = y0 + rowHeight * 3 - (weatherData.get(i).getFS() - rangeValue.minFengsu) * rawRowH / rangeFeng;
                    }
                    canvas.drawCircle(leftStartX + cx + scrollOffset, cy, pointRadius, mPaint);
                    String fengText = String.format("%.1f", weatherData.get(i).getFS());
                    int fengWidth = getStringWidth(mPaint, fengText);
                    canvas.drawText(fengText, leftStartX + (columnWidth - fengWidth) / 2 + columnWidth * (i + 1) + scrollOffset, cy + tH, mPaint);
                    if (i == dataSize - 1) {
                        linPaths[3] = new Path();
                        linPaths[3].moveTo(leftStartX + cx + scrollOffset, cy);
                    } else {
                        linPaths[3].lineTo(leftStartX + cx + scrollOffset, cy);
                    }
                }
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(brokenLinewColor);
                canvas.drawPath(linPaths[0], mPaint);
                canvas.drawPath(linPaths[1], mPaint);
                canvas.drawPath(linPaths[2], mPaint);
                canvas.drawPath(linPaths[3], mPaint);
            }
        }
    }

    /**
     * 折线图数据上下范围
     */
    private class RangeValue {
        float minTemperature = 0.0f;
        float maxTemperature = 0.0f;
        float minHumidity = 0.0f;
        float maxHumidity = 0.0f;
        float minRainfall = 0.0f;
        float maxRainfall = 0.0f;
        float maxFengsu = 0.0f;
        float minFengsu = 0.0f;
    }

    /**
     * 计算折现图上下限
     *
     * @param data
     * @return
     */
    private void calculateRangeFromArray(List<WeatherBean> data) {
        if (data != null && data.size() > 0) {
//            RangeValue rangeValue = new RangeValue();
            if (weatherData.isEmpty()) {
                rangeValue.maxHumidity = data.get(0).getSD();
                rangeValue.minHumidity = data.get(0).getSD();
                rangeValue.minRainfall = data.get(0).getYL();
                rangeValue.maxRainfall = data.get(0).getYL();
                rangeValue.maxTemperature = data.get(0).getWD();
                rangeValue.minTemperature = data.get(0).getWD();
                rangeValue.maxFengsu = data.get(0).getFS();
                rangeValue.minFengsu = data.get(0).getFS();
            }
            for (int i = 1; i < data.size(); i++) {
                if (rangeValue.maxTemperature < data.get(i).getWD()) {
                    rangeValue.maxTemperature = data.get(i).getWD();
                } else if (rangeValue.minTemperature > data.get(i).getWD()) {
                    rangeValue.minTemperature = data.get(i).getWD();
                }

                if (rangeValue.maxRainfall < data.get(i).getYL()) {
                    rangeValue.maxRainfall = data.get(i).getYL();
                } else if (rangeValue.minRainfall > data.get(i).getYL()) {
                    rangeValue.minRainfall = data.get(i).getYL();
                }

                if (rangeValue.maxHumidity < data.get(i).getSD()) {
                    rangeValue.maxHumidity = data.get(i).getSD();
                } else if (rangeValue.minHumidity > data.get(i).getSD()) {
                    rangeValue.minHumidity = data.get(i).getSD();
                }
                if (rangeValue.maxFengsu < data.get(i).getFS()) {
                    rangeValue.maxFengsu = data.get(i).getFS();
                } else if (rangeValue.minFengsu > data.get(i).getFS()) {
                    rangeValue.minFengsu = data.get(i).getFS();
                }
            }
//            return rangeValue;
        }
//        return null;
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

    private class GestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            if (Math.abs(distanceX) > 20) {
//                return true;
//            }

            scrollOffset -= distanceX;

            if (scrollOffset > Math.abs(minScrollX)) {
                scrollOffset = Math.abs(minScrollX);
            } else if (scrollOffset < 0) {
                scrollOffset = 0;
            }
            postInvalidate();

            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            float x = e2.getX() - e1.getX();

            return false;
        }
    }

    /**
     * author：zach
     * date: 2018/12/16 14:35
     * ProjectName: UIZView
     * PackageName: personal.ui.lingchen.uizview.UI.WeatherWidget
     * Description: 天气信息item
     */
    public static class WeatherBean {
        /**
         * 日期
         */
        private String Time;

        /**
         * 温度
         */
        private float WD;
        /**
         * 湿度，返回0-100；
         */
        private float SD;
        /**
         * 雨量,单位mm
         */
        private float YL;
        /**
         * 风力
         */
        private float FS;

        public String getTime() {
//            if (TextUtils.isEmpty(Time) || "null".equals(Time)) {
//                return Time;
//            } else {
//                String timeStr = Time.substring(11,16);
//                String[] timeArr = timeStr.split(":");
//                int hour,min;
//                try{
//                    hour = Integer.parseInt(timeArr[0]);
//                    min = Integer.parseInt(timeArr[1]);
//
//                    if(min>=45){
//                        hour+=1;
//                        min =0;
//                    }else if(min<15) {
//                        min = 0;
//                    }else {
//                        min = 30;
//                    }
//
//                    timeStr = String.format(Locale.CHINA,"%02d:%02d",hour,min);
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }
//                return timeStr;
//            }
            return this.Time;
        }

        public void setTime(String time) {
            this.Time = time;
        }

        public float getWD() {
            return WD;
        }

        public void setWD(float WD) {
            this.WD = WD;
        }

        public float getSD() {
            return SD;
        }

        public void setSD(float SD) {
            this.SD = SD;
        }

        public float getYL() {
            return YL;
        }

        public void setYL(float YL) {
            this.YL = YL;
        }


        public float getFS() {
            return FS;
        }

        public void setFS(float FS) {
            this.FS = FS;
        }

        @Override
        public String toString() {
            return "WeatherBean{" +
                    "Time='" + Time + '\'' +
                    ", WD=" + WD +
                    ", SD=" + SD +
                    ", YL=" + YL +
//                    ", windDirection='" + windDirection + '\'' +
                    ", FS=" + FS +
                    '}';
        }
    }
}
