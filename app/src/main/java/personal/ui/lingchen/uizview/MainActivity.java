package personal.ui.lingchen.uizview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import personal.ui.lingchen.uizview.LoadingUI.SlackLoading.SlackLoadingActivity;
import personal.ui.lingchen.uizview.UIActivity.BezierTestActivity;
import personal.ui.lingchen.uizview.UIActivity.IndicatorProgressActivity;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.btn_indicatorProgress, R.id.btn_bezier,R.id.btn_slackView, R.id.btn_animation})
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

}
