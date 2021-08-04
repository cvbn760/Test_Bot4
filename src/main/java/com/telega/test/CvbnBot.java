package com.telega.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.List;
@Component
public class CvbnBot extends TelegramLongPollingBot {

    private static final String TOKEN = "1903236047:AAHM59aDE6J0huGfJSaapBGzSjJOgxiCjUw";
    private static final String USER_NAME = "Test_Bot4";
    private static final String LOGIN = "cvbn760_bot";
//    @Value("${bot.name}")
//    private String botUsername;
//
//    @Value("${bot.token}")
//    private String token;


    private final UpdateReceiver updateReceiver;

    private DefaultBotOptions botOptions;


    public CvbnBot(UpdateReceiver updateReceiver) {
        this.updateReceiver = updateReceiver;
        botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        botOptions.setProxyHost("localhost");
        botOptions.setProxyPort(9150);
    }

    @Override
    public String getBotUsername() {
        return USER_NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        List<PartialBotApiMethod<? extends Serializable>> messagesToSend = updateReceiver.handle(update);
        if (messagesToSend != null && !messagesToSend.isEmpty()) {
            messagesToSend.forEach(response -> {
                if (response instanceof SendMessage) {
                    executeWithExceptionCheck((SendMessage) response);
                }
            });
        }
    }

    public void executeWithExceptionCheck(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
           e.printStackTrace();
        }
    }
}
