import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WeatherInfo {
    private final String API_CALL_TEMPLATE = "http://api.openweathermap.org/data/2.5/weather?";
    private final String API_KEY_TEMPLATE = "bad4092dcb7625876d4f8721366d5e9c";
    private String city = "Kyiv";

    public WeatherInfo(String city) {
        this.city = city;
    }

    public WeatherInfo() {}

    public JSONObject getWeatherForecast() {
        StringBuilder stringBuilder = new StringBuilder();
        URL url = null;
        JSONObject json = null;
        try {
            url = new URL(API_CALL_TEMPLATE + "q=" + city + "&" + "APPID=" + API_KEY_TEMPLATE);
            URLConnection urlcon = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
            String line;
            while((line = bufferedReader.readLine())!= null){
                stringBuilder.append(line);
            }
            json = new JSONObject(stringBuilder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

}
