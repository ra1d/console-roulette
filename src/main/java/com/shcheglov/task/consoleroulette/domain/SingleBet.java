package com.shcheglov.task.consoleroulette.domain;

public class SingleBet extends Bet {

    private final int chosenNumber;

    public SingleBet(final Player player,
                     final int chosenNumber,
                     final long amount) {
        super(player, amount);
        this.chosenNumber = chosenNumber;
    }

    @Override
    public String getBetType() {
        return String.valueOf(chosenNumber);
    }

    @Override
    public boolean isWinning(final int winningNumber) {
        return chosenNumber == winningNumber;
    }

    @Override
    public long getWinningAmount() {
        return getAmount() * 36;
    }

}
