package com.telega.test;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
@ImportResource({"classpath:context.xml"})
@PropertySource("classpath:application.properties")
public class Main {

    public static void main(String...args){
        ApiContextInitializer.init();
        SpringApplication.run(Main.class, args);
        AuthYandex authYandex = new AuthYandex(new Gson());
        authYandex.run();
    }
}
