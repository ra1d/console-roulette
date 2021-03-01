package com.shcheglov.task.consoleroulette.validator;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Consumer;

import static com.shcheglov.task.consoleroulette.converter.InputToBetConverter.BET_TYPE_EVEN;
import static com.shcheglov.task.consoleroulette.converter.InputToBetConverter.BET_TYPE_ODD;

@Component("betTypeValidator")
public class BetTypeValidator implements Consumer<String[]> {

    @Override
    public void accept(final String[] rawInputTokens) {
        if (!(Objects.equals(rawInputTokens[1], BET_TYPE_ODD) ||
                Objects.equals(rawInputTokens[1], BET_TYPE_EVEN) ||
                isInGivenRange(rawInputTokens[1]))) {
            throw new IllegalArgumentException(String.format("Malformed bet type: [%s]!", rawInputTokens[1]));
        }
    }

    private boolean isInGivenRange(final String rawBetType) {
        final int singleBet;
        try {
            singleBet = Integer.parseInt(rawBetType);
        } catch (final NumberFormatException e) {
            return false;
        }
        return singleBet > 0 && singleBet < 37;
    }

}
