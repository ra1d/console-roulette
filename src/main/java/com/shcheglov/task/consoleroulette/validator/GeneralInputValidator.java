package com.shcheglov.task.consoleroulette.validator;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Consumer;

@Component("generalInputValidator")
public class GeneralInputValidator implements Consumer<String[]> {

    @Override
    public void accept(final String[] rawInputTokens) {
        if (rawInputTokens.length != 3) {
            throw new IllegalArgumentException(String.format("Malformed input: [%s]!", Arrays.toString(rawInputTokens)));
        }
    }

}
