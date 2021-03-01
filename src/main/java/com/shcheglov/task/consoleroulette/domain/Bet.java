package com.shcheglov.task.consoleroulette.domain;

import com.shcheglov.task.consoleroulette.dao.BetIdGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Bet {

    @EqualsAndHashCode.Include
    @ToString.Exclude
    private final long id = BetIdGenerator.next();

    private final Player player;

    private final long amount;

    public Bet(final Player player,
               final long amount) {
        this.player = player;
        this.amount = amount;
    }

    public abstract String getBetType();

    public abstract boolean isWinning(final int winningNumber);

    public abstract long getWinningAmount();

}
