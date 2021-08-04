package com.telega.test.hand;

import com.telega.test.model.State;
import com.telega.test.model.User;
import java.util.List;
import java.io.Serializable;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

public interface Handler {

    // основной метод, который будет обрабатывать действия пользователя
    List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message);
    // метод, который позволяет узнать, можем ли мы обработать текущий State у пользователя
    State operatedBotState();
    // метод, который позволяет узнать, какие команды CallBackQuery мы можем обработать в этом классе
    List<String> operatedCallBackQuery();
}
