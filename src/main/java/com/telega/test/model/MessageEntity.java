package com.telega.test.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class MessageEntity {

    @JsonValue
    private List<Translations> translations;

}
