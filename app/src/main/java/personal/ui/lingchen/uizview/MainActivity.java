package personal.ui.lingchen.uizview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import personal.ui.lingchen.uizview.UIActivity.IndicatorProgressActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_indicatorProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //测试编辑
    }

    private void initView() {
        btn_indicatorProgress = (Button) findViewById(R.id.btn_indicatorProgress);

        btn_indicatorProgress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_indicatorProgress:
                startActivity(new Intent(MainActivity.this, IndicatorProgressActivity.class));
                break;
        }
    }
}
