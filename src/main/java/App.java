public class App {
    public static void main(String[] args) {
        /*
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

         */
        WeatherInfo weatherInfo = new WeatherInfo("Lviv");
        System.out.println(weatherInfo.getDailyForecast());

    }
}
