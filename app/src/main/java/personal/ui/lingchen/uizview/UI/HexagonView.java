package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import personal.ui.lingchen.uizview.R;

/**
 * Created by ozner_67 on 2016/10/8.
 * 邮箱：xinde.zhang@cftcn.com
 * <p>
 * 正六边形
 */

public class HexagonView extends View {

    private Paint mPaint, testPaint;
    private Path mPath;
    private Bitmap mBitmap;
    private int mWidth,mHeight;

    public HexagonView(Context context) {
        super(context);
        init();
    }

    public HexagonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HexagonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3f);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.aqua));

        testPaint = new Paint();
        testPaint.setAntiAlias(true);
        testPaint.setStyle(Paint.Style.STROKE);
        testPaint.setStrokeWidth(2f);
        testPaint.setColor(ContextCompat.getColor(getContext(), R.color.green));

        mPath = new Path();
        initPath();

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_0);
        BitmapShader bitShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(bitShader);
    }


    public void setBitmap(@NonNull Bitmap bitmap) {
        this.mBitmap = bitmap;
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        initPath();
    }


    private void initPath() {
        float l = (float) (getWidth() / 2);
        float h = (float) (Math.sqrt(3) * l);
        float top = (getHeight() - h) / 2;

        mPath.reset();
        mPath.moveTo(l/2,top);
        mPath.lineTo(0,h/2+top);
        mPath.lineTo(l/2,h+top);
        mPath.lineTo((float) (l*1.5),h+top);
        mPath.lineTo(2*l,h/2+top);
        mPath.lineTo((float) (l*1.5),top);
        mPath.lineTo(l/2,top);
        mPath.close();
    }

    private void drawPath(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPath(canvas);
    }
}
