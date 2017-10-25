package personal.ui.lingchen.uizview.UIActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import personal.ui.lingchen.uizview.R;
import personal.ui.lingchen.uizview.UI.UIZRippleCircle;

public class ArcHeaderViewActivity extends AppCompatActivity {

    @InjectView(R.id.uizRippleCircle)
    UIZRippleCircle uizRippleCircle;

    private float value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_header_view);
        ButterKnife.inject(this);
//        getActionBar().setTitle("弧形HeaderView");
    }

    @OnClick({R.id.btn_add, R.id.btn_minus, R.id.btn_single})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                if (value < 1000) {
                    value += 2.5;
                }
                uizRippleCircle.setValue(value);
                break;
            case R.id.btn_minus:
                if (value > 0) {
                    value -= 2.5;
                }
                uizRippleCircle.setValue(value);
                break;
            case R.id.btn_single:
                uizRippleCircle.setValue(1789);
                break;
        }
    }
}
