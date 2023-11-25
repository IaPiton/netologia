package org.example;


import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new NasaBot("myBot_piton_kukushkinia_bot",
                    "6769822138:AAGCSS5lNO6NTf0FkjAqMEX51hVAV5qUtek"));

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }
}
