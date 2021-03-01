package com.shcheglov.task.consoleroulette.converter;

import com.shcheglov.task.consoleroulette.domain.Bet;
import com.shcheglov.task.consoleroulette.domain.EvenBet;
import com.shcheglov.task.consoleroulette.domain.OddBet;
import com.shcheglov.task.consoleroulette.domain.Player;
import com.shcheglov.task.consoleroulette.domain.SingleBet;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("inputToBetConverter")
public class InputToBetConverter {

    public static final String BET_TYPE_EVEN = "EVEN";
    public static final String BET_TYPE_ODD = "ODD";

    public Bet convert(final Player player,
                       final String[] validBetTokens) {
        return Objects.equals(validBetTokens[1], BET_TYPE_EVEN) ? new EvenBet(player, Long.parseLong(validBetTokens[2]))
                : Objects.equals(validBetTokens[1], BET_TYPE_ODD)
                        ? new OddBet(player, Long.parseLong(validBetTokens[2]))
                        : new SingleBet(player, Integer.parseInt(validBetTokens[1]), Long.parseLong(validBetTokens[2]));
    }

}
