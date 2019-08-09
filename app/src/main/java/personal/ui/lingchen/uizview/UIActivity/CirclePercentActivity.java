package personal.ui.lingchen.uizview.UIActivity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

import personal.ui.lingchen.uizview.R;
import personal.ui.lingchen.uizview.UI.CustomViewGroup;
import personal.ui.lingchen.uizview.UI.SpecialMaskView;

public class CirclePercentActivity extends AppCompatActivity {
    //    CustomViewGroup cvgContainer;
    SpecialMaskView specialMaskView;
    private int index = 0;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_percent);
        specialMaskView = (SpecialMaskView) findViewById(R.id.smvContainer);
//        cvgContainer = (CustomViewGroup) findViewById(R.id.cvgContainer);
//        TextView textView = new TextView(this);
//        textView.setTextColor(Color.BLUE);
//        textView.setBackgroundColor(Color.RED);
//        textView.setText("测试");
//        CustomViewGroup.LayoutParams lp = new CustomViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        textView.setLayoutParams(lp);
//        cvgContainer.addView(textView);
        random = new Random();

        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specialMaskView.addString("测试标题" + specialMaskView.getChildCount());

            }
        });
        findViewById(R.id.btnMinus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (specialMaskView.getChildCount() > 0) {
                    specialMaskView.remove(specialMaskView.getChildCount() - 1);
                }
            }
        });

        findViewById(R.id.btnChingeColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specialMaskView.setTextColor(Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            }
        });

        findViewById(R.id.btnChingeColum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specialMaskView.setColumCount(random.nextInt(4) + 1);
            }
        });
    }
}
