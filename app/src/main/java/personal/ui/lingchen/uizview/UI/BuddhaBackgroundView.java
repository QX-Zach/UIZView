package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * author：zach
 * date: 2018/9/21 15:24
 * ProjectName: UIZView
 * PackageName: personal.ui.lingchen.uizview.UI
 * Description: 佛性光环UI
 */
public class BuddhaBackgroundView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "BuddhaBackgroundView";
    private final int START_COLOR = 0xff0196ff;//表盘起始颜色
    private final int MIDDLE_COLOR = 0xffb159ed;//中间颜色
    private final int END_COLOR = 0xffef3e6e;//结束颜色
    private final int ROTATE_STEP = 10;//两条线间隔角度
    private final int ANIM_DURIATION = 5000;//动画时间
    private final int START_ROTATE = -10;//起始角度
    private final int END_ROTATE = 200;//结束角度
    private int lineNum = 20;
    private final float LINE_WIDTH = 7f;//单位dp
    float lineLenght = 3.0f;//线长，单位dp
    float radius;//刻度盘最外侧半径值
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private boolean mIsDrawing;
    private Paint linePaint;//刻度画笔
    private Paint shaderPaint;
    private RectF canvasRect;//画布范围
    private Shader shader;
    private PointF center;//圆心
    private int curLineNum = 0;
    private Timer mTimer;
    private boolean isFore = true;

    public BuddhaBackgroundView(Context context) {
        super(context);
        initView();
    }

    public BuddhaBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BuddhaBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    protected int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(dp2px(getContext(), LINE_WIDTH));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        shaderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lineNum = (180 - START_ROTATE * 2) / ROTATE_STEP;
        if (START_ROTATE + lineNum * ROTATE_STEP < END_ROTATE) {
            lineNum += 1;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        }, 0, ANIM_DURIATION / lineNum);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        canvasRect = new RectF(0, 0, width, height);
        caclueProperties();
    }

    /**
     * 计算相关属性
     */
    private void caclueProperties() {
        //计算线长
        radius = canvasRect.width() / 2 * 0.8f;
        //刻度圆心
        center = new PointF(canvasRect.width() / 2, canvasRect.width() / 2);
        lineLenght = radius * 0.15f;
        shader = new LinearGradient(0, 0, canvasRect.right, 0, new int[]{START_COLOR, MIDDLE_COLOR, END_COLOR}
                , new float[]{0, 0.5f, 1}, Shader.TileMode.CLAMP);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
        mTimer.cancel();
        mTimer = null;
    }

    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas(null);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            if (isFore) {
                curLineNum++;
                if (curLineNum == lineNum) {
                    isFore = false;
                }
            } else {
                curLineNum--;
                if (curLineNum == 0) {
                    isFore = true;
                }
            }


            drawDial(mCanvas);

        } catch (Exception ex) {

        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    /**
     * 绘制直线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas, float lineLen, float rotate) {
        canvas.rotate(-rotate, center.x, center.y);
        canvas.drawLine(center.x + radius, center.y, center.x + radius - lineLen, center.y, linePaint);
        canvas.rotate(rotate, center.x, center.y);
    }

    /**
     * 绘制表盘
     */
    private void drawDial(Canvas canvas) {
        Bitmap srcBm = makeSrc((int) canvasRect.width(), (int) canvasRect.height());
        BitmapShader bs = new BitmapShader(srcBm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        ComposeShader cs = new ComposeShader(bs, shader, PorterDuff.Mode.SRC_ATOP);
        shaderPaint.setShader(cs);
        canvas.drawRect(new Rect(0, 0, (int) canvasRect.width(), (int) canvasRect.height()), shaderPaint);
    }

    /**
     * 创建刻度图像
     *
     * @param w
     * @param h
     * @return
     */
    private Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        drawLines(c);
        return bm;

    }

    private void drawLines(Canvas canvas) {
        for (int i = 0; i <= curLineNum; i++) {
            if (i % 2 == 0) {
                drawLine(canvas, lineLenght, END_ROTATE - i * ROTATE_STEP);
            } else {
                drawLine(canvas, lineLenght * 0.55f, END_ROTATE - i * ROTATE_STEP);
            }
        }
    }
}
