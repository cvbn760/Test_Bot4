package com.telega.test.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class YandexToken {

    @JsonValue
    private String iamToken;

    @JsonValue
    private String expiresAt;

    public YandexToken(String iamToken, String expiresAt) {
        this.iamToken = iamToken;
        this.expiresAt = expiresAt;
    }
    
    public YandexToken(){
    }
}
