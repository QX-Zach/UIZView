package personal.ui.lingchen.uizview.UIActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import personal.ui.lingchen.uizview.R;
import personal.ui.lingchen.uizview.UI.UIZRealTemp;

public class RealTempTestActivity extends AppCompatActivity {

    @InjectView(R.id.uizRealtemp)
    UIZRealTemp uizRealtemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_temp_test);
        ButterKnife.inject(this);
    }

    int precent = 0;

    @OnClick({R.id.btn_add, R.id.btn_minus,R.id.btn_desc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                if(precent<100){
                    precent+=10;
                }
                uizRealtemp.setTemp(precent);
                uizRealtemp.setPrecent(precent);
                if(precent<50){
                    uizRealtemp.setTempDesc("偏凉");
                }else if(precent<80){
                    uizRealtemp.setTempDesc("适中");
                }else{
                    uizRealtemp.setTempDesc("偏烫");
                }
                break;
            case R.id.btn_minus:
                if(precent >0){
                    precent-=10;
                }
                uizRealtemp.setTemp(precent);
                uizRealtemp.setPrecent(precent);
                if(precent<50){
                    uizRealtemp.setTempDesc("偏凉");
                }else if(precent<80){
                    uizRealtemp.setTempDesc("适中");
                }else{
                    uizRealtemp.setTempDesc("偏烫");
                }
                break;
            case R.id.btn_desc:

                break;
        }
    }
}
