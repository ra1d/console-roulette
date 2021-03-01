package com.shcheglov.task.consoleroulette.domain;

import lombok.Value;

@Value
public class BetOutcome {

    Bet bet;

    Type type;

    long winningAmount;

    public void updatePlayerTotal() {
        bet.getPlayer().setTotalWin(bet.getPlayer().getTotalWin() + winningAmount);
        bet.getPlayer().setTotalBet(bet.getPlayer().getTotalBet() + bet.getAmount());
    }

    public enum Type {
        WIN,
        LOSE
    }

}
