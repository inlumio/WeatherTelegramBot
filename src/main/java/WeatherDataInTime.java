import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class WeatherDataInTime {
    private LocalDateTime time;
    private String weatherDescription;
    private double temp;
    private double tempFeelsLike;
    private double pressure;
    private double humidity;
    private double windSpeed;
    private double windDir;

    public WeatherDataInTime(LocalDateTime time, String weatherDescription, double temp, double tempFeelsLike, double pressure, double humidity, double windSpeed, double windDir) {
        this.time = time;
        this.weatherDescription = weatherDescription;
        this.temp = temp;
        this.tempFeelsLike = tempFeelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDir = windDir;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTempFeelsLike() {
        return tempFeelsLike;
    }

    public void setTempFeelsLike(double tempFeelsLike) {
        this.tempFeelsLike = tempFeelsLike;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDir() {
        return windDir;
    }

    public void setWindDir(double windDir) {
        this.windDir = windDir;
    }

    @Override
    public String toString() {
        return "\n" + time.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd yyyy HH:mm:ss", Locale.US))
                + "\n the weather is " + weatherDescription
                + "\n temp: " + temp + "*C"
                + ", feels like: " + tempFeelsLike + "*C"
                + "\n pressure: " + pressure + " hPa"
                + "\n humidity: " + humidity + " %"
                + "\n wind speed: " + windSpeed + " meter/sec"
                + ", wind direction: " + windDir + " degrees"
                + "\n"
                ;
    }
}
