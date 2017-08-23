package personal.ui.lingchen.uizview.UIActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import personal.ui.lingchen.uizview.R;
import personal.ui.lingchen.uizview.UI.UIZOznerTempDial;

public class OznerTempDialActivity extends AppCompatActivity {

    @InjectView(R.id.uiz_OznerTempDial)
    UIZOznerTempDial uizOznerTempDial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozner_temp_dial);
        ButterKnife.inject(this);
    }

    private int currtentValue = 0;

    @OnClick({R.id.btn_add, R.id.btn_minus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                if (currtentValue < 100) {
                    currtentValue += 5;
                }
                uizOznerTempDial.setValue(currtentValue);
                break;
            case R.id.btn_minus:
                if (currtentValue > 0) {
                    currtentValue -= 5;
                }
                uizOznerTempDial.setValue(currtentValue);
                break;
        }
    }
}
