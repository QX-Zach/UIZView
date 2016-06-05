package personal.ui.lingchen.uizview.UIActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import personal.ui.lingchen.uizview.R;
import personal.ui.lingchen.uizview.UI.IndicatorProgressBar;

public class IndicatorProgressActivity extends AppCompatActivity implements View.OnClickListener {
    IndicatorProgressBar indicatorprogress;
    Button btn_minus, btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_progress);
        initView();
        initListener();
    }

    private void initView() {
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_minus = (Button) findViewById(R.id.btn_minus);
        indicatorprogress = (IndicatorProgressBar) findViewById(R.id.indicatorprogress);
        indicatorprogress.setThumb(R.drawable.filter_status_thumb);
    }

    private void initListener() {
        btn_minus.setOnClickListener(this);
        btn_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                int addvalue = indicatorprogress.getProgress();
                if (addvalue < 100) {
                    addvalue += 5;
                    indicatorprogress.setProgress(addvalue);
                }
                break;
            case R.id.btn_minus:
                int minusValue = indicatorprogress.getProgress();
                if (minusValue > 0) {
                    minusValue -= 5;
                    indicatorprogress.setProgress(minusValue);
                }
                break;
        }
    }
}
