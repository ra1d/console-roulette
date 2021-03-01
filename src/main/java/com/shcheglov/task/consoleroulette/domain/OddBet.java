package com.shcheglov.task.consoleroulette.domain;

public class OddBet extends Bet {

    public OddBet(final Player player,
                  final long amount) {
        super(player, amount);
    }

    @Override
    public String getBetType() {
        return "ODD";
    }

    @Override
    public boolean isWinning(final int winningNumber) {
        return winningNumber % 2 == 1;
    }

    @Override
    public long getWinningAmount() {
        return getAmount() * 2;
    }

}
