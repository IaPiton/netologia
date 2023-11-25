package org.example;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class NasaBot extends TelegramLongPollingBot {
    private String url = "https://api.nasa.gov/planetary/apod?api_key=ojmVIFTUdRRm8kDaqPcHZKBEGVwsgIn6IaLOFt9B";
    private String botUsername;
    private String botToken;

    public NasaBot(String botUsername, String botToken) throws TelegramApiException {
        this.botUsername = botUsername;
        this.botToken = botToken;
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {// Create a SendMessage object with mandatory fields
            Long chatId = update.getMessage().getChatId();
            String[] action = update.getMessage().getText().split(" ");
            switch (action[0]) {
                case "/start":
                case "/image":
                    sendMessage(NasaBuilder.urlImage(url), chatId);
                    break;
                case "/help":
                    sendMessage("Вас приветствует бот NasaImage." +
                            "Ведите /start или /image для показа фото на сегодняшнюю дату или по" +
                            "запросу /date yyyy-mm-dd выберите дату", chatId);
                    break;
                case "/date":
                    if (isValidFormat(action[1])) {
                        sendMessage(NasaBuilder.urlImage(url + "&date=" + action[1]), chatId);
                    } else {
                        sendMessage("Вы ввели неверный формат даты", chatId);
                    }
                    break;
                default:
                    sendMessage("Вы ввели неверный запрос", chatId);
                    break;
            }


        }

    }

    public void sendMessage(String msg, Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(msg);
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            System.out.println("Не удалось отправить сообщение");
            ;
        }
    }

    public boolean isValidFormat(String date) {
        try {
            date = date.replace("-", " ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
            LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }


    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }
}
