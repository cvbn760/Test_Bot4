package com.telega.test;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.telega.test.model.YandexToken;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class AuthYandex implements Runnable{

    private String getIAMTokenURL= "https://iam.api.cloud.yandex.net/iam/v1/tokens";
    private String oAuthToken = "AQAAAAAiKt5KAATuwR0NwTL4ikzFiSVOIbmAllI";
    private boolean canGetToken = true; // Пока что всегда true

    private Gson gson;

    public AuthYandex(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void run() {
        try {
            while (canGetToken) {
                Unirest.setTimeouts(0, 0);
                HttpResponse<String> response = Unirest.post(getIAMTokenURL)
                        .header("Content-Type", "text/plain")
                        .body(String.format("{\"yandexPassportOauthToken\" : \"%s\"}", oAuthToken))
                        .asString();
                
                YandexToken yandexToken = gson.fromJson(response.getBody(), YandexToken.class);
                CvbnBot.setYandexToken(yandexToken);
                Thread.sleep(21600000);
            }
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
    }
}
