package com.shcheglov.task.consoleroulette.logic;

import com.shcheglov.task.consoleroulette.domain.Bet;
import com.shcheglov.task.consoleroulette.domain.BetOutcome;
import com.shcheglov.task.consoleroulette.domain.EvenBet;
import com.shcheglov.task.consoleroulette.domain.OddBet;
import com.shcheglov.task.consoleroulette.domain.Player;
import com.shcheglov.task.consoleroulette.domain.SingleBet;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class RouletteEngineTest {

    private final RouletteEngine rouletteEngine = new RouletteEngine();

    private static Stream<Arguments> testDataSource() {
        final Player player = new Player("John");
        return Stream.of(
                arguments(new SingleBet(player, 1, 100), 0, BetOutcome.Type.LOSE, 0),
                arguments(new SingleBet(player, 1, 100), 1, BetOutcome.Type.WIN, 100 * 36),
                arguments(new SingleBet(player, 1, 100), 20, BetOutcome.Type.LOSE, 0),
                arguments(new SingleBet(player, 1, 100), 36, BetOutcome.Type.LOSE, 0),
                arguments(new SingleBet(player, 20, 100), 0, BetOutcome.Type.LOSE, 0),
                arguments(new SingleBet(player, 20, 100), 1, BetOutcome.Type.LOSE, 0),
                arguments(new SingleBet(player, 20, 100), 20, BetOutcome.Type.WIN, 100 * 36),
                arguments(new SingleBet(player, 20, 100), 36, BetOutcome.Type.LOSE, 0),
                arguments(new SingleBet(player, 36, 100), 0, BetOutcome.Type.LOSE, 0),
                arguments(new SingleBet(player, 36, 100), 1, BetOutcome.Type.LOSE, 0),
                arguments(new SingleBet(player, 36, 100), 20, BetOutcome.Type.LOSE, 0),
                arguments(new SingleBet(player, 36, 100), 36, BetOutcome.Type.WIN, 100 * 36),

                arguments(new OddBet(player, 100), 0, BetOutcome.Type.LOSE, 0),
                arguments(new OddBet(player, 100), 1, BetOutcome.Type.WIN, 100 * 2),
                arguments(new OddBet(player, 100), 20, BetOutcome.Type.LOSE, 0),
                arguments(new OddBet(player, 100), 36, BetOutcome.Type.LOSE, 0),

                arguments(new EvenBet(player, 100), 0, BetOutcome.Type.LOSE, 0),
                arguments(new EvenBet(player, 100), 1, BetOutcome.Type.LOSE, 0),
                arguments(new EvenBet(player, 100), 20, BetOutcome.Type.WIN, 100 * 2),
                arguments(new EvenBet(player, 100), 36, BetOutcome.Type.WIN, 100 * 2)
        );
    }

    @ParameterizedTest
    @MethodSource("testDataSource")
    public void shouldReturnOutcomeForBetAndWinningNumber(final Bet bet,
                                                          final int winningNumber,
                                                          final BetOutcome.Type outcomeType,
                                                          final long winningAmount) {
        // when
        final BetOutcome actualOutcome = rouletteEngine.play(bet, winningNumber);

        // then
        assertEquals(new BetOutcome(bet, outcomeType, winningAmount), actualOutcome);
    }

}