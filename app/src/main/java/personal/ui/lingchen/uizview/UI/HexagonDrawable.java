package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by ozner_67 on 2016/10/8.
 * 邮箱：xinde.zhang@cftcn.com
 */

public class HexagonDrawable extends Drawable {
    private static final String TAG = "HexagonDrawable";
    private Paint mPaint, testPaint;
    private Path mPath;
    private Bitmap mBitmap;
    private Rect mRect = new Rect();

    public HexagonDrawable() {
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3f);
//        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.aqua));

        testPaint = new Paint();
        testPaint.setAntiAlias(true);
        testPaint.setStyle(Paint.Style.STROKE);
        testPaint.setStrokeWidth(2f);
//        testPaint.setColor(ContextCompat.getColor(getContext(), R.color.green));

        mPath = new Path();
        initPath();
    }

    private void initPath() {
        float l = (float) (mRect.width() / 2);
        float h = (float) (Math.sqrt(3) * l);
        float top = (mRect.height() - h) / 2;
        mPath.reset();
        mPath.moveTo(l / 2 + mRect.left, top + mRect.top);
        mPath.lineTo(0 + mRect.left, h / 2 + top + mRect.top);
        mPath.lineTo(l / 2 + mRect.left, h + top + mRect.top);
        mPath.lineTo((float) (l * 1.5) + mRect.left, h + top + mRect.top);
        mPath.lineTo(2 * l + mRect.left, h / 2 + top + mRect.top);
        mPath.lineTo((float) (l * 1.5) + mRect.left, top + mRect.top);
        mPath.lineTo(l / 2 + mRect.left, top + mRect.top);
        mPath.close();
    }

    public void setBitmap(@NonNull Bitmap bitmap) {
//        this.mBitmap = bitmap;
        int w = Math.min(bitmap.getWidth(), bitmap.getHeight());
        Log.e(TAG, "setBitmap:width: "+ bitmap.getWidth()+" ,height:"+bitmap.getHeight());
        if (bitmap.getWidth() > bitmap.getHeight()) {
            Log.e(TAG, "setBitmap_1: x:"+(bitmap.getWidth() - w) / 2+" ,w:"+w);
            this.mBitmap = Bitmap.createBitmap(bitmap, (bitmap.getWidth() - w) / 2, 0, w, w);
        } else {
            this.mBitmap = Bitmap.createBitmap(bitmap, 0, (bitmap.getHeight() - w) / 2, w, w);
        }
        BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    public int getIntrinsicWidth() {
        if (mBitmap != null) {
            return Math.max(mBitmap.getHeight(), mBitmap.getWidth());
        } else {
            return super.getIntrinsicWidth();
        }
    }

    @Override
    public int getIntrinsicHeight() {
        if (mBitmap != null) {
            return Math.max(mBitmap.getHeight(), mBitmap.getWidth());
        }
        return super.getIntrinsicHeight();
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mRect.set(left, top, right, bottom);
        initPath();
    }
}
