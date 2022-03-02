import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class WeatherInfo {
    private final String API_CALL_TEMPLATE = "http://api.openweathermap.org/data/2.5/forecast?";
    private final String API_KEY_TEMPLATE = "bad4092dcb7625876d4f8721366d5e9c";
    private String city = "Kyiv";
    private JSONObject weatherDataJSON;

    public WeatherInfo(String city) {
        this.city = city;
    }

    public WeatherInfo() {
    }

    public void getWeatherForecastOnJSON() {
        StringBuffer response = new StringBuffer();
        try {
            String urlString = API_CALL_TEMPLATE + "q=" + city + "&appid=" + API_KEY_TEMPLATE;
            URL urlObject = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == 404) {
                throw new IllegalArgumentException();
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            weatherDataJSON = new JSONObject(response.toString());

            in.close();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getDailyForecast() {
        StringBuilder weatherInfo = new StringBuilder("");
        JsonNode weatherList = null;
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime dayAfterNowDateTime = nowDateTime.plusDays(1);
        weatherInfo.append("Daily forecast for the city " + city);
        try {
            weatherList = new ObjectMapper().readTree(weatherDataJSON.toString()).get("list");
            for (final JsonNode timeStampWeather : weatherList) {
                String weatherTimeString = timeStampWeather.get("dt_txt").toString().replaceAll("\"", "");
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime weatherDateTime = LocalDateTime.parse(weatherTimeString,format);
                System.out.println(weatherDateTime);
                if (weatherDateTime.isAfter(dayAfterNowDateTime))
                    break;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

}
