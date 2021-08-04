package com.telega.test.hand;

import com.telega.test.model.State;
import com.telega.test.model.User;
import com.telega.test.repository.JpaUserRepository;
import java.util.Collections;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import static com.telega.test.TelegramUtil.createMessageTemplate;
import java.io.Serializable;
import java.util.List;

@Component
public class StartHandler implements Handler{

    private String botUsername = "Test_Bot4";

    private final JpaUserRepository userRepository;

    public StartHandler(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        SendMessage welcomeMessage = createMessageTemplate(user)
                .setText(String.format(
                        "Hola! I'm *%s*%nI am here to help you learn Java", botUsername
                ));
        // Просим назваться
        SendMessage registrationMessage = createMessageTemplate(user)
                .setText("In order to start our journey tell me your name");
        // Меняем пользователю статус на - "ожидание ввода имени"
        user.setBotState(State.ENTER_NAME);
        userRepository.save(user);

        return List.of(welcomeMessage, registrationMessage);
    }

    @Override
    public State operatedBotState() {
        return State.START;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }
}
