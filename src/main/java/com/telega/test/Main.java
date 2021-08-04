package com.telega.test;

import com.telega.test.CvbnBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@SpringBootApplication
@ImportResource({"classpath:db/springDB.xml"})
@PropertySource("classpath:application.properties")
public class Main {
    public static void main(String...args){
        ApiContextInitializer.init();

        SpringApplication.run(Main.class, args);
//        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
//
//        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
//        botOptions.setProxyHost("localhost");
//        botOptions.setProxyPort(9150);
//
//        CvbnBot cvbnBot = new CvbnBot(botOptions);
//
//        TelegramBotsApi botsApi = new TelegramBotsApi();
//        try {
//            botsApi.registerBot(cvbnBot);
//        } catch (TelegramApiRequestException e) {
//            e.printStackTrace();
//        }
    }
}
