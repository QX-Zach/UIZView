package personal.ui.lingchen.uizview.UIActivity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import personal.ui.lingchen.uizview.R;
import personal.ui.lingchen.uizview.UI.PolygonalChart.UIZPolygonalChart2;

public class ZheXianActivity extends AppCompatActivity {
    UIZPolygonalChart2 uizPolygonalChart2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhe_xian);
        uizPolygonalChart2 = (UIZPolygonalChart2) findViewById(R.id.uizOil);
        setTags();
        loadData();
    }

    public void showDialog(View view){

    }

    private void loadData() {
        Random random = new Random();
        List<UIZPolygonalChart2.ValueInfo> valueInfos = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            UIZPolygonalChart2.ValueInfo valueInfo = new UIZPolygonalChart2.ValueInfo();
            valueInfo.dayOfMonth = i;
            valueInfo.value = random.nextInt(100);
            valueInfos.add(valueInfo);
        }
        uizPolygonalChart2.setCurrentMonth(2);
        uizPolygonalChart2.setValueInfos(valueInfos);
    }

    private void setTags() {
        Random random = new Random();
        List<UIZPolygonalChart2.TagInfo> tagInfos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UIZPolygonalChart2.TagInfo tagInfo = new UIZPolygonalChart2.TagInfo();
            int value = (i + 1) * 10;
            tagInfo.tag = String.valueOf(value);
            tagInfo.tagPercent =value;
            int color = Color.argb(255,random.nextInt(255),random.nextInt(255),random.nextInt(255));
            tagInfo.lineColor = color;
            tagInfo.valueColor = color;
            tagInfos.add(tagInfo);
        }
//        UIZPolygonalChart2.TagInfo tagInfo = new UIZPolygonalChart2.TagInfo();
//        tagInfo.tag = "10";
//        tagInfo.tagPercent = 10;
//        tagInfos.add(tagInfo);
//        UIZPolygonalChart2.TagInfo tagInfo2 = new UIZPolygonalChart2.TagInfo();
//        tagInfo2.tag = "40";
//        tagInfo2.tagPercent = 40;
//        tagInfos.add(tagInfo2);
//        UIZPolygonalChart2.TagInfo tagInfo3 = new UIZPolygonalChart2.TagInfo();
//        tagInfo3.tag = "80";
//        tagInfo3.tagPercent = 80;
//        tagInfos.add(tagInfo3);
//        UIZPolygonalChart2.TagInfo tagInfo4 = new UIZPolygonalChart2.TagInfo();
//        tagInfo4.tag = "100";
//        tagInfo4.tagPercent = 100;
//        tagInfos.add(tagInfo4);
        uizPolygonalChart2.setTagInfo(tagInfos);
    }

}
