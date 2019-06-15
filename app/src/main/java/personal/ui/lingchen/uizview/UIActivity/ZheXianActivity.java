package personal.ui.lingchen.uizview.UIActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import personal.ui.lingchen.uizview.R;
import personal.ui.lingchen.uizview.UI.PolygonalChart.UIZPolygonalChart2;

public class ZheXianActivity extends AppCompatActivity {
    UIZPolygonalChart2 uizPolygonalChart2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhe_xian);
        uizPolygonalChart2 = (UIZPolygonalChart2) findViewById(R.id.uizOil);
        uizPolygonalChart2.setCurrentMonth(5).apply();
    }
}
