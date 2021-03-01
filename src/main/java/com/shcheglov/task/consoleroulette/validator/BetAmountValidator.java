package com.shcheglov.task.consoleroulette.validator;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("betAmountValidator")
public class BetAmountValidator implements Consumer<String[]> {

    @Override
    public void accept(final String[] rawInputTokens) {
        if (!isInGivenRange(rawInputTokens[2])) {
            throw new IllegalArgumentException(String.format("Malformed bet amount: [%s]!", rawInputTokens[2]));
        }
    }

    private boolean isInGivenRange(final String rawBetType) {
        final int singleBet;
        try {
            singleBet = Integer.parseInt(rawBetType);
        } catch (final NumberFormatException e) {
            return false;
        }
        return singleBet > 0 && singleBet < 1_000_000_001;
    }

}
