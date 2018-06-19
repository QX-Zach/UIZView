package personal.ui.lingchen.uizview.UIActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import personal.ui.lingchen.uizview.R;
import personal.ui.lingchen.uizview.UI.SignalView;
import personal.ui.lingchen.uizview.UI.UIZOznerTempDial;

public class OznerTempDialActivity extends AppCompatActivity {

    @BindView(R.id.uiz_OznerTempDial)
    UIZOznerTempDial uizOznerTempDial;
    @BindView(R.id.svSingal)
    SignalView svSingal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozner_temp_dial);
        ButterKnife.bind(this);

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
