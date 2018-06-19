package personal.ui.lingchen.uizview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import personal.ui.lingchen.uizview.LoadingUI.SlackLoading.SlackLoadingActivity;
import personal.ui.lingchen.uizview.UI.HexagonDrawable;
import personal.ui.lingchen.uizview.UI.Snowflake.SnowFlokeActivity;
import personal.ui.lingchen.uizview.UI.UIZTextImageButton;
import personal.ui.lingchen.uizview.UI.UIZTimeRemain;
import personal.ui.lingchen.uizview.UIActivity.ArcHeaderViewActivity;
import personal.ui.lingchen.uizview.UIActivity.BezierTestActivity;
import personal.ui.lingchen.uizview.UIActivity.CameraTestActivity;
import personal.ui.lingchen.uizview.UIActivity.GradientTestActivity;
import personal.ui.lingchen.uizview.UIActivity.IndicatorProgressActivity;
import personal.ui.lingchen.uizview.UIActivity.OzerDialActivity;
import personal.ui.lingchen.uizview.UIActivity.OznerTempDialActivity;
import personal.ui.lingchen.uizview.UIActivity.RealTempTestActivity;
import personal.ui.lingchen.uizview.UIActivity.RecyleViewActivity;
import personal.ui.lingchen.uizview.UIActivity.SeekBarActivity;
import personal.ui.lingchen.uizview.Utils.FlashLightManager;
import personal.ui.lingchen.uizview.Utils.OznerColorUtils;
import personal.ui.lingchen.uizview.Utils.WPTempColorUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    @BindView(R.id.btn_indicatorProgress)
    Button btnIndicatorProgress;
    @BindView(R.id.btn_animation)
    Button btnAnimation;
    @BindView(R.id.llay_btn_root)
    LinearLayout llayBtnRoot;
    @BindView(R.id.btn_slackView)
    Button btnSlackView;
    @BindView(R.id.btn_bezier)
    Button btnBezier;
    @BindView(R.id.btn_oznerDial)
    Button btnOznerDial;
    @BindView(R.id.iv_hex)
    ImageView ivHex;
    @BindView(R.id.iv_hex2)
    ImageView ivHex2;
    @BindView(R.id.btn_RecyleView)
    Button btnRecyleView;
    @BindView(R.id.btn_seekBar)
    Button btnSeekBar;
    @BindView(R.id.uizTIBtn_test)
    UIZTextImageButton uizTIBtnTest;
    @BindView(R.id.uiz_timeRemain)
    UIZTimeRemain uizTimeRemain;
    @BindView(R.id.btn_light)
    Button btnLight;
    @BindView(R.id.btnGradient)
    Button btnGradient;

    private boolean isLightOn = false;
    private CameraManager cameraManager;
    private android.hardware.Camera mCamera = null;
    private FlashLightManager flashLightMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        HexagonDrawable hexagonDrawable = new HexagonDrawable();
        hexagonDrawable.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.meizi5));
        ivHex.setImageDrawable(hexagonDrawable);
        HexagonDrawable hexagonDrawable2 = new HexagonDrawable();
        hexagonDrawable2.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.meizi1));
        ivHex2.setImageDrawable(hexagonDrawable2);
        Log.e(TAG, "onCreate: "+roundUpToPowerOf2(5));
        Log.e(TAG, "onCreate: "+roundUpToPowerOf2(9));
        Log.e(TAG, "onCreate: "+roundUpToPowerOf2(8));
        uizTimeRemain.setProcessMax(1440);

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        flashLightMgr = new FlashLightManager(getApplicationContext());
        flashLightMgr.init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                String[] camerList = cameraManager.getCameraIdList();
                for (String str : camerList) {
                    Log.e(TAG, "onCreate: " + str);
                }
            } catch (Exception e) {
                Log.e("error", e.getMessage());
            }
        }

        Log.e(TAG, "onCreate: WPTempColorUtils:" + WPTempColorUtil.getColor(0));
        Log.e(TAG, "onCreate: OznerColorUtils:" + OznerColorUtils.caculateColor(0, 0xff0196ff, 0xffb159ed, new int[]{}));

        Log.e(TAG, "onCreate: 20长度的UUID：" + get20UUID());
    }

    private int roundUpToPowerOf2(int number) {
        // assert number >= 0 : "number must be non-negative";
        return number >= 12 ? 12: (number > 1) ? Integer.highestOneBit((number - 1) << 1) : 1;
    }

    /**
     * 获得20个长度的十六进制的UUID
     *
     * @return UUID
     */
    public static String get20UUID() {

        UUID id = UUID.randomUUID();
        Log.e(TAG, "get20UUID: " + id.toString());
        String[] idd = id.toString().split("-");
        return idd[0] + idd[1] + idd[2] + idd[3];
    }

    @OnClick({R.id.btn_indicatorProgress, R.id.btn_RecyleView, R.id.btn_oznerDial, R.id.btn_bezier,
            R.id.btn_slackView, R.id.btn_animation, R.id.btn_snowflake, R.id.btn_seekBar, R.id.btn_tempDial,
            R.id.btn_WarmCupUI, R.id.btn_ripple_circle, R.id.btn_light, R.id.btnGradient})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_indicatorProgress:
                startActivity(new Intent(MainActivity.this, IndicatorProgressActivity.class));

                break;
            case R.id.btn_animation:
                bounceAnimationView(btnIndicatorProgress);
                break;
            case R.id.btn_slackView:
                startActivity(new Intent(MainActivity.this, SlackLoadingActivity.class));
                break;
            case R.id.btn_bezier:
                startActivity(new Intent(MainActivity.this, BezierTestActivity.class));
                break;
            case R.id.btn_oznerDial:
                startActivity(new Intent(MainActivity.this, OzerDialActivity.class));
                break;
            case R.id.btn_RecyleView:
                startActivity(new Intent(MainActivity.this, RecyleViewActivity.class));
                break;
            case R.id.btn_snowflake:
                startActivity(new Intent(MainActivity.this, SnowFlokeActivity.class));
                break;
            case R.id.btn_seekBar:
                startActivity(new Intent(MainActivity.this, SeekBarActivity.class));
                break;
            case R.id.btn_tempDial:
                startActivity(new Intent(MainActivity.this, OznerTempDialActivity.class));
                break;
            case R.id.btn_WarmCupUI:
                startActivity(new Intent(MainActivity.this, RealTempTestActivity.class));
                break;
            case R.id.btn_ripple_circle:
                startActivity(new Intent(MainActivity.this, ArcHeaderViewActivity.class));
                break;
            case R.id.btn_light:
                startActivity(new Intent(MainActivity.this, CameraTestActivity.class));
//                lightSwitch(!isLightOn);
//                if (isLightOn) {
//                    flashLightMgr.turnOff();
//                    isLightOn = false;
//                } else {
//                    flashLightMgr.turnOn();
//                    isLightOn = true;
//                }
                break;
            case R.id.btnGradient:
                startActivity(new Intent(MainActivity.this, GradientTestActivity.class));
                break;

        }
    }

    //    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void lightSwitch(final boolean openLight) {
        Log.e(TAG, "lightSwitch: 设置灯光->" + openLight);
        if (!openLight) {//关闭手电筒
            isLightOn = false;
            btnLight.setText("开启灯光");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    cameraManager.setTorchMode("0", false);
                } catch (Exception ex) {
                    Log.e(TAG, "lightSwitch_On_ex: " + ex.getMessage());
                }
            } else {
                if (mCamera != null) {
                    mCamera.stopPreview();
                    mCamera.release();
                    mCamera = null;
                }
            }
        } else {//打开手电筒
            isLightOn = true;
            btnLight.setText("关闭灯光");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    cameraManager.setTorchMode("0", true);
                } catch (Exception ex) {

                }
            } else {
                final PackageManager pm = getPackageManager();
                final FeatureInfo[] features = pm.getSystemAvailableFeatures();
                for (final FeatureInfo f : features) {
                    if (PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) {
                        if (null == mCamera)
                            mCamera = Camera.open();
                        final Camera.Parameters parmeters = mCamera.getParameters();
                        parmeters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        mCamera.setParameters(parmeters);
                        mCamera.startPreview();
                    }
                }
            }
        }
    }

    /**
     * 晃动动画
     *
     * @param view
     */
    private void bounceAnimationView(View view) {
        Animator swing = ObjectAnimator.ofFloat(view, "rotationX", 0, 30, -20, 0);
        swing.setDuration(500);
        swing.setInterpolator(new AccelerateInterpolator());
        swing.start();

    }
//
//    @OnClick(R.id.uizTIBtn_test)
//    public void onViewClicked() {
//        uizTIBtnTest.setSelected(!uizTIBtnTest.isSelected());
//    }

    int currentProcess = 0;

    @OnClick({R.id.btn_add, R.id.btn_minus, R.id.uizTIBtn_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                if (currentProcess < 1440) {
                    currentProcess += 20;
                }
                uizTimeRemain.setRemainTime(currentProcess / 60, currentProcess % 60);
                break;
            case R.id.btn_minus:
                if (currentProcess > 0) {
                    currentProcess -= 20;
                }
                uizTimeRemain.setRemainTime(currentProcess / 60, currentProcess % 60);
                break;
            case R.id.uizTIBtn_test:
                uizTIBtnTest.setSelected(!uizTIBtnTest.isSelected());
                break;
        }
    }
}
