package personal.ui.lingchen.uizview.UIActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import personal.ui.lingchen.uizview.R;
import personal.ui.lingchen.uizview.UI.BezierTestView;

public class BezierTestActivity extends AppCompatActivity {

    @InjectView(R.id.bezier_view)
    BezierTestView bezierView;
    @InjectView(R.id.btn_satart)
    Button btnSatart;
    @InjectView(R.id.btn_stop)
    Button btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_test);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.btn_satart, R.id.btn_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_satart:
                bezierView.startAnim();
                break;
            case R.id.btn_stop:
                bezierView.stopAnim();
                break;
        }
    }
}
