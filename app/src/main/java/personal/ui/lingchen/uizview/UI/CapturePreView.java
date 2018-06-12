package personal.ui.lingchen.uizview.UI;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by ozner_67 on 2017/12/1.
 * 邮箱：xinde.zhang@cftcn.com
 *
 * http://blog.csdn.net/qiguangyaolove/article/details/53018504
 */

public class CapturePreView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "CapturePreView";
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CapturePreView(Context context) {
        super(context);
        init();
    }

    public CapturePreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CapturePreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.e(TAG, "init: ");
        mCamera = getCameraInstance();

//        List<Camera.Size> sizeList = mCamera.getParameters().getSupportedPreviewSizes();
//        for (Camera.Size s : sizeList) {
//            Log.e(TAG, "surfaceCreated: 预览尺寸->" + s.width + " * " + s.height);
//        }
////        Camera.Parameters parm = mCamera.getParameters();
////        parm.setRotation(0);
////        parm.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
////        mCamera.setParameters(parm);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e(TAG, "surfaceCreated: ");
        try {
            if (mCamera == null) {
                return;
            }
            try{
                mCamera.stopPreview();
            }catch (Exception ex){

            }
            mCamera.setPreviewDisplay(holder);
            Camera.Parameters parameters = mCamera.getParameters();
//            parameters.setPreviewSize();
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();
//            parameters.setRotation(90);
        } catch (Exception ex) {
            Log.d(TAG, "Error setting camera preview: " + ex.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e(TAG, "surfaceChanged: ");
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }
        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
//        List<Camera.Size> sizes = mCamera.getParameters().getSupportedPreviewSizes();
//        Camera.Size size = null;
//        for (Camera.Size s : sizes) {
//            if (s.width == 640) {
//                Log.e(TAG, "surfaceChanged: 重新设置参数");
////                mCamera.getParameters().setPreviewSize(sizes.get(5).width, sizes.get(5).height);
//                size = s;
//            }
//        }
//        Camera.Parameters parm = mCamera.getParameters();
//        if (size != null)
//            parm.setPreviewSize(size.width, size.height);
//        parm.setRotation(90);
//        parm.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

//        mCamera.getParameters().setRotation(90);
//        mCamera.getParameters().setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//        mCamera.setParameters(parm);
        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (null != mCamera) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }


    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}
