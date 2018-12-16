package personal.ui.lingchen.uizview.UI.WeatherWidget;

/**
 * author：zach
 * date: 2018/12/16 14:35
 * ProjectName: UIZView
 * PackageName: personal.ui.lingchen.uizview.UI.WeatherWidget
 * Description: 天气信息item
 */
public class WeatherBean {
    /**
     * 日期
     */
    private String time;
    /**
     * 天气图标的资源id
     */
    private int weatherIconResId;
    /**
     * 温度
     */
    private int temperature;
    /**
     * 湿度，返回0-100；
     */
    private int humidity;
    /**
     * 雨量,单位mm
     */
    private float rainfall;
    /**
     * 风向
     */
    private String windDirection;
    /**
     * 风力
     */
    private String windForce;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWeatherIconResId() {
        return weatherIconResId;
    }

    public void setWeatherIconResId(int weatherIconResId) {
        this.weatherIconResId = weatherIconResId;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getRainfall() {
        return rainfall;
    }

    public void setRainfall(float rainfall) {
        this.rainfall = rainfall;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindForce() {
        return windForce;
    }

    public void setWindForce(String windForce) {
        this.windForce = windForce;
    }

    @Override
    public String toString() {
        return "WeatherBean{" +
                "time='" + time + '\'' +
                ", weatherIconResId=" + weatherIconResId +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", rainfall=" + rainfall +
                ", windDirection='" + windDirection + '\'' +
                ", windForce='" + windForce + '\'' +
                '}';
    }
}
