package com.telega.test.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Translations {

    @JsonValue
    private String text;

    @Override
    public String toString() {
        return text;
    }
}
