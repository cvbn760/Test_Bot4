package com.telega.test;

import com.google.gson.Gson;
import com.telega.test.model.Language;
import com.telega.test.model.YandexToken;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class CvbnBot extends TelegramLongPollingBot {

    private static final String TOKEN = "1903236047:AAHM59aDE6J0huGfJSaapBGzSjJOgxiCjUw";
    private static final String USER_NAME = "Test_Bot4";

    @Getter
    @Setter
    private static YandexToken yandexToken;

    public CvbnBot() {
    }

    @Getter
    @Setter
    private Language language;

    @Autowired
    private Translate translate;

    @Override
    public String getBotUsername() {
        return USER_NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();


        // Начало бота
        if (message != null && message.getText().equals("/start")) {
            language = Language.not_selected;
            executeCommand(sendInlineKeyBoardMessage(message.getChatId()),message.getChatId());
        }
        // Если была нажата кнопка перевода
        else if (update.hasCallbackQuery()){
            String query = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            switch (query){
                case "RU>EN":
                    language = Language.ru;
                    executeCommand(new SendMessage().setText("Введите текст на русском:"), chatId);
                    break;
                case "EN>RU":
                    language = Language.en;
                    executeCommand(new SendMessage().setText("Введите текст на английском:"), chatId);
                    break;
                default:
                    break;
            }
        }

        // Если требуется перевод текста
        else if (!language.equals(Language.not_selected) && message.hasText()){
            translate = new Translate(new Gson());
            String result = "";
            String text = message.getText();
            long chatId = message.getChatId();

            switch (language.name()){
                // С русского на английский
                case "ru":
                    result = translate.translate(text, yandexToken.getIamToken(), Language.ru, Language.en);
                    executeCommand(new SendMessage().setText(result), chatId);
                    break;
                    // С английского на русский
                case "en":
                    result = translate.translate(text, yandexToken.getIamToken(), Language.en, Language.ru);
                    executeCommand(new SendMessage().setText(result), chatId);
                    break;
                default:
                    break;
            }
            language = Language.not_selected;
            Thread.sleep(1000);
            executeCommand(sendInlineKeyBoardMessage(message.getChatId()),message.getChatId());
        }
    }

    private void executeCommand(SendMessage sendMessage, long chatId) {
        try {
            sendMessage.setChatId(chatId);
            execute(sendMessage);
        }
        catch (TelegramApiException exc){
            exc.printStackTrace();
        }
    }

    public static SendMessage sendInlineKeyBoardMessage(long chatId) {
        List<InlineKeyboardButton> keyLine = new ArrayList<>();
        keyLine.add(new InlineKeyboardButton().setText("RU>EN").setCallbackData("RU>EN"));
        keyLine.add(new InlineKeyboardButton().setText("EN>RU").setCallbackData("EN>RU"));
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyLine);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Перевести текст...").setReplyMarkup(inlineKeyboardMarkup);
    }

}
