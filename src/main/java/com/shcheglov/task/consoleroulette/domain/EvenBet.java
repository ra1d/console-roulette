package com.shcheglov.task.consoleroulette.domain;

public class EvenBet extends Bet {

    public EvenBet(final Player player,
                   final long amount) {
        super(player, amount);
    }

    @Override
    public String getBetType() {
        return "EVEN";
    }

    @Override
    public boolean isWinning(final int winningNumber) {
        return winningNumber % 2 == 0;
    }

    @Override
    public long getWinningAmount() {
        return getAmount() * 2;
    }

}
