package personal.ui.lingchen.uizview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import personal.ui.lingchen.uizview.LoadingUI.SlackLoading.SlackLoadingActivity;
import personal.ui.lingchen.uizview.UI.HexagonDrawable;
import personal.ui.lingchen.uizview.UI.Snowflake.SnowFlokeActivity;
import personal.ui.lingchen.uizview.UI.UIZTextImageButton;
import personal.ui.lingchen.uizview.UIActivity.BezierTestActivity;
import personal.ui.lingchen.uizview.UIActivity.IndicatorProgressActivity;
import personal.ui.lingchen.uizview.UIActivity.OzerDialActivity;
import personal.ui.lingchen.uizview.UIActivity.RecyleViewActivity;
import personal.ui.lingchen.uizview.UIActivity.SeekBarActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @InjectView(R.id.btn_indicatorProgress)
    Button btnIndicatorProgress;
    @InjectView(R.id.btn_animation)
    Button btnAnimation;
    @InjectView(R.id.llay_btn_root)
    LinearLayout llayBtnRoot;
    @InjectView(R.id.btn_slackView)
    Button btnSlackView;
    @InjectView(R.id.btn_bezier)
    Button btnBezier;
    @InjectView(R.id.btn_oznerDial)
    Button btnOznerDial;
    @InjectView(R.id.iv_hex)
    ImageView ivHex;
    @InjectView(R.id.iv_hex2)
    ImageView ivHex2;
    @InjectView(R.id.btn_RecyleView)
    Button btnRecyleView;
    @InjectView(R.id.btn_seekBar)
    Button btnSeekBar;
    @InjectView(R.id.uizTIBtn_test)
    UIZTextImageButton uizTIBtnTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        HexagonDrawable hexagonDrawable = new HexagonDrawable();
        hexagonDrawable.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.meizi5));
        ivHex.setImageDrawable(hexagonDrawable);
        HexagonDrawable hexagonDrawable2 = new HexagonDrawable();
        hexagonDrawable2.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.meizi1));
        ivHex2.setImageDrawable(hexagonDrawable2);

    }

    @OnClick({R.id.btn_indicatorProgress, R.id.btn_RecyleView, R.id.btn_oznerDial, R.id.btn_bezier,
            R.id.btn_slackView, R.id.btn_animation, R.id.btn_snowflake, R.id.btn_seekBar})
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

    @OnClick(R.id.uizTIBtn_test)
    public void onViewClicked() {
        uizTIBtnTest.setSelected(!uizTIBtnTest.isSelected());
    }
}
