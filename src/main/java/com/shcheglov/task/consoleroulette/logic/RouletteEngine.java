package com.shcheglov.task.consoleroulette.logic;

import com.shcheglov.task.consoleroulette.domain.Bet;
import com.shcheglov.task.consoleroulette.domain.BetOutcome;
import org.springframework.stereotype.Component;

@Component
public class RouletteEngine {

    public BetOutcome play(final Bet bet,
                           final int winningNumber) {
        return winningNumber == 0 ? new BetOutcome(bet, BetOutcome.Type.LOSE, 0)
                : bet.isWinning(winningNumber) ? new BetOutcome(bet, BetOutcome.Type.WIN, bet.getWinningAmount())
                        : new BetOutcome(bet, BetOutcome.Type.LOSE, 0);
    }

}
