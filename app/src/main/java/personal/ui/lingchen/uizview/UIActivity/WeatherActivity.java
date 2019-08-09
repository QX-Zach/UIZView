package personal.ui.lingchen.uizview.UIActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import personal.ui.lingchen.uizview.R;
import personal.ui.lingchen.uizview.UI.WeatherWidget.WeatherView;

public class WeatherActivity extends AppCompatActivity {
    WeatherView wvWeather;
    private int currentHour = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        wvWeather = (WeatherView) findViewById(R.id.wvWeather);
        addData(10, currentHour);
        findViewById(R.id.btnAddData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHour -= 3;
                addData(5, currentHour);
            }
        });
    }


    private void addData(int num, int startHour) {

        Random random = new Random();

        List<WeatherView.WeatherBean> data = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            WeatherView.WeatherBean weatherBean = new WeatherView.WeatherBean();
            weatherBean.setSD(random.nextInt(100));
            weatherBean.setFS(random.nextFloat());
            weatherBean.setWD(random.nextInt(20) + 10);
            weatherBean.setYL(random.nextInt(100));
            if (i % 2 == 0) {
                weatherBean.setTime(String.format("%2d:00", i / 2 + startHour));
            } else {
                weatherBean.setTime(String.format("%2d:30", i / 2 + startHour));
            }
            data.add(weatherBean);
        }

//        WeatherView.WeatherBean weatherBean1 = new WeatherView.WeatherBean();
//        weatherBean1.setSD(73);
//        weatherBean1.setYL(5f);
//        weatherBean1.setWD(18);
//        weatherBean1.setTime("07:00");
//        weatherBean1.setFS(0.2f);
//        data.add(weatherBean1);
//
//        WeatherView.WeatherBean weatherBean2 = new WeatherView.WeatherBean();
//        weatherBean2.setSD(64);
//        weatherBean2.setYL(20f);
//        weatherBean2.setWD(20);
//        weatherBean2.setTime("07:30");
//        weatherBean2.setFS(0.4f);
//        data.add(weatherBean2);
//
//        WeatherView.WeatherBean weatherBean3 = new WeatherView.WeatherBean();
//        weatherBean3.setSD(67);
//        weatherBean3.setYL(18f);
//        weatherBean3.setWD(22);
//        weatherBean3.setTime("08:00");
//        weatherBean3.setFS(0.5f);
//        data.add(weatherBean3);
//
//        WeatherView.WeatherBean weatherBean4 = new WeatherView.WeatherBean();
//        weatherBean4.setSD(78);
//        weatherBean4.setYL(12f);
//        weatherBean4.setWD(15);
//        weatherBean4.setTime("08:30");
//        weatherBean4.setFS(0.3f);
//        data.add(weatherBean4);
//
//
//        WeatherView.WeatherBean weatherBean5 = new WeatherView.WeatherBean();
//        weatherBean5.setSD(89);
//        weatherBean5.setYL(16f);
//        weatherBean5.setWD(17);
//        weatherBean5.setTime("09:00");
//        weatherBean5.setFS(0.7f);
//        data.add(weatherBean5);

//        WeatherView.WeatherBean[] weatherBeans = new WeatherView.WeatherBean[5];
//        data.toArray(weatherBeans);
//        wvWeather.setData(weatherBeans);
        wvWeather.setData(data);
    }
}
