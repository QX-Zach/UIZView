package personal.ui.lingchen.uizview.UIActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import personal.ui.lingchen.uizview.R;
import personal.ui.lingchen.uizview.UI.WeatherWidget.WeatherBean;
import personal.ui.lingchen.uizview.UI.WeatherWidget.WeatherView;

public class WeatherActivity extends AppCompatActivity {
    WeatherView wvWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        wvWeather = (WeatherView) findViewById(R.id.wvWeather);
        initData();
    }

    private void initData() {
        List<WeatherBean> data = new ArrayList<>();
        WeatherBean weatherBean1 = new WeatherBean();
        weatherBean1.setHumidity(73);
        weatherBean1.setRainfall(5f);
        weatherBean1.setTemperature(18);
        weatherBean1.setTime("11/28");
        weatherBean1.setWindDirection("东风");
        weatherBean1.setWindForce("3-4级");
        weatherBean1.setWeatherIconResId(R.drawable.img_0);

        data.add(weatherBean1);
        WeatherBean weatherBean2 = new WeatherBean();
        weatherBean2.setHumidity(73);
        weatherBean2.setRainfall(5f);
        weatherBean2.setTemperature(19);
        weatherBean2.setTime("11/29");
        weatherBean2.setWindDirection("东风");
        weatherBean2.setWindForce("3-4级");
        weatherBean2.setWeatherIconResId(R.drawable.img_1);

        data.add(weatherBean2);

        WeatherBean weatherBean3 = new WeatherBean();
        weatherBean3.setHumidity(74);
        weatherBean3.setRainfall(5f);
        weatherBean3.setTemperature(17);
        weatherBean3.setTime("今天");
        weatherBean3.setWindDirection("东风");
        weatherBean3.setWindForce("3-4级");
        weatherBean3.setWeatherIconResId(R.drawable.img_2);

        data.add(weatherBean3);
        WeatherBean weatherBean4 = new WeatherBean();
        weatherBean4.setHumidity(40);
        weatherBean4.setRainfall(5f);
        weatherBean4.setTemperature(20);
        weatherBean4.setTime("12/1");
        weatherBean4.setWindDirection("东风");
        weatherBean4.setWindForce("<3级");
        weatherBean4.setWeatherIconResId(R.drawable.img_3);

        data.add(weatherBean4);
        WeatherBean weatherBean5 = new WeatherBean();
        weatherBean5.setHumidity(25);
        weatherBean5.setRainfall(5f);
        weatherBean5.setTemperature(20);
        weatherBean5.setTime("12/2");
        weatherBean5.setWindDirection("东风");
        weatherBean5.setWindForce("3-4级");
        weatherBean5.setWeatherIconResId(R.drawable.img_4);

        data.add(weatherBean5);

        WeatherBean[] weatherBeans = new WeatherBean[5];
        data.toArray(weatherBeans);
        wvWeather.setData(weatherBeans);
    }
}
