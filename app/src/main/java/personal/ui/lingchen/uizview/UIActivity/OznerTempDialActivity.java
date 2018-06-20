package personal.ui.lingchen.uizview.UIActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import personal.ui.lingchen.uizview.R;
import personal.ui.lingchen.uizview.UI.SignalView;
import personal.ui.lingchen.uizview.UI.SlideAdjustView;
import personal.ui.lingchen.uizview.UI.UIZOznerTempDial;

public class OznerTempDialActivity extends AppCompatActivity {
    private static final String TAG = "OznerTempDialActivity";
    @BindView(R.id.uiz_OznerTempDial)
    UIZOznerTempDial uizOznerTempDial;
    @BindView(R.id.svSingal)
    SignalView svSingal;
    @BindView(R.id.sav_adjust)
    SlideAdjustView savAdjust;
    @BindView(R.id.tvSildeResult)
    TextView tvSildeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozner_temp_dial);
        ButterKnife.bind(this);
        savAdjust.setListener(new SlideAdjustView.SlideAdjustListener() {
            @Override
            public void onMove(final int value) {
                Log.e(TAG, "onMove: " + value);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvSildeResult.setText(String.valueOf(value));
                    }
                });
            }

            @Override
            public void onFinished(final int value) {
                Log.e(TAG, "onFinished: 结束滑动--->>>" + value);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvSildeResult.setText(String.valueOf(value));
                    }
                });
            }
        });
    }


    private int currtentValue = 0;
    private int currSignal = 0;

    @OnClick({R.id.btn_add, R.id.btn_minus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:

                if (currSignal < 5) {
                    currSignal++;
                } else {
                    currSignal = 0;
                }
                svSingal.setSingalStrength(currSignal);
                if (currtentValue < 100) {
                    currtentValue += 5;
                }
                uizOznerTempDial.setValue(currtentValue);
                break;
            case R.id.btn_minus:

                if (currSignal > 0) {
                    currSignal--;
                } else {
                    currSignal = 5;
                }
                svSingal.setSingalStrength(currSignal);
                if (currtentValue > 0) {
                    currtentValue -= 5;
                }
                uizOznerTempDial.setValue(currtentValue);
                break;
        }
    }
}
