import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "VPWeatherBot";
    }

    @Override
    public String getBotToken() {
        return "5101676375:AAH59cn1NcMUqAaoahM4CIYWfPB_xgGFLWM";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String inputMessage = update.getMessage().getText();
            String recipient = update.getMessage().getChatId().toString();
            if (inputMessage.equals("/start")) {
                sendMsg(recipient, "Greetings!!! I'm Weather Bot. Enter the city name, and I'll show You the its daily weather forecast");
            } else {
                WeatherInfo weatherInfo = new WeatherInfo(inputMessage);
                sendMsg(recipient, weatherInfo.getDailyForecast());
            }
        }
    }

    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
