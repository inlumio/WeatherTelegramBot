import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class WeatherInfo {
    private final String API_CALL_TEMPLATE = "http://api.openweathermap.org/data/2.5/forecast?";
    private final String API_KEY_TEMPLATE = "bad4092dcb7625876d4f8721366d5e9c";
    private String units;
    private String city = "Kyiv";

    public WeatherInfo(String city) {
        this.city = city;
        this.units = "metric";
    }

    public WeatherInfo() {
    }

    public String getDailyForecast(){
        return getWeatherForecastOnJSON();
    }

    private String getWeatherForecastOnJSON() {
        StringBuffer response = new StringBuffer();
        JSONObject weatherDataJSON;
        try {
            String urlString = API_CALL_TEMPLATE + "q=" + city + "&units=" + units + "&appid=" + API_KEY_TEMPLATE;
            URL urlObject = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == 404) {
                return "No data about this city. Try to enter another location:";
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            weatherDataJSON = new JSONObject(response.toString());
            in.close();
            return getWeatherDataFromJSON(weatherDataJSON);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getWeatherDataFromJSON(JSONObject weatherDataJSON) {
        ArrayList<WeatherDataInTime> data = new ArrayList<>();
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime dayAfterNowDateTime = nowDateTime.plusDays(1);
        try {
            JsonNode weatherList = new ObjectMapper().readTree(weatherDataJSON.toString()).get("list");
            for (final JsonNode timeStampWeather : weatherList) {
                String weatherTimeString = timeStampWeather.get("dt_txt").toString().replaceAll("\"", "");
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime weatherDateTime = LocalDateTime.parse(weatherTimeString, format);
                if (weatherDateTime.isAfter(dayAfterNowDateTime))
                    break;
                JsonNode mainData = new ObjectMapper().readTree(timeStampWeather.toString()).get("main");
                JsonNode windData = new ObjectMapper().readTree(timeStampWeather.toString()).get("wind");
                JSONObject weatherDescData = new JSONArray(new ObjectMapper().readTree(timeStampWeather.toString()).get("weather").toString()).getJSONObject(0);
                double temp = mainData.get("temp").asDouble();
                double tempFeelsLike = mainData.get("feels_like").asDouble();
                double pressure = mainData.get("pressure").asDouble();
                double humidity = mainData.get("humidity").asDouble();
                double windSpeed = windData.get("speed").asDouble();
                double windDir = windData.get("deg").asDouble();
                String weatherDescription = weatherDescData.get("description").toString();
                data.add(new WeatherDataInTime(weatherDateTime, weatherDescription, temp, tempFeelsLike, pressure, humidity, windSpeed, windDir));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "JSON Problems";
        }
        return getWeatherForecastOnString(data);
    }

    private String getWeatherForecastOnString(ArrayList<WeatherDataInTime> allData) {
        StringBuilder weatherInfo = new StringBuilder("");
        weatherInfo.append("Daily forecast for the city " + city + " : \n");
        for (WeatherDataInTime data : allData) {
            weatherInfo.append(data.toString());
        }
        return weatherInfo.toString();
    }

}
