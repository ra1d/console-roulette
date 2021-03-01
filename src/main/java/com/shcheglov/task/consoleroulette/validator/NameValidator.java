package com.shcheglov.task.consoleroulette.validator;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("nameValidator")
public class NameValidator implements Consumer<String[]> {

    @Override
    public void accept(final String[] rawInputTokens) {
        if (!rawInputTokens[0].matches("[0-9a-zA-Z\\-_]{1,30}")) {
            throw new IllegalArgumentException(String.format("Malformed name: [%s]!", rawInputTokens[0]));
        }
    }

}
