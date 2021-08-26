package com.telega.test;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.telega.test.model.Language;
import com.telega.test.model.MessageEntity;
import org.springframework.stereotype.Component;

@Component
public class Translate {

    private String YANDEX_TRANSLATE_URL = "https://translate.api.cloud.yandex.net/translate/v2/translate";
    private String FOLDER_ID = "b1g5stse22u79qpiqc03";

    private Gson gson;

    public Translate(Gson gson) {
        this.gson = gson;
    }

    public String translate(String text, String token, Enum<Language> sourceLang, Enum<Language> targetLang) throws UnirestException {
        text = text.replaceAll("'","");
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(YANDEX_TRANSLATE_URL)
                .header("Content-Type", "application/json")
                .header("Authorization", String.format("Bearer %s", token))
                .body(String.format("{'texts':['%s'], 'folderId':'%s', 'sourceLanguageCode': '%s', 'targetLanguageCode':'%s'}", text, FOLDER_ID, sourceLang, targetLang))
                .asString();

        MessageEntity translations = gson.fromJson(response.getBody(), MessageEntity.class);
        return translations.getTranslations().get(0).getText();
    }
}
