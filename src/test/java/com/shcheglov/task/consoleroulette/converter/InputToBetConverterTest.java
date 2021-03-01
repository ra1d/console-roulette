package com.shcheglov.task.consoleroulette.converter;

import com.shcheglov.task.consoleroulette.domain.Bet;
import com.shcheglov.task.consoleroulette.domain.EvenBet;
import com.shcheglov.task.consoleroulette.domain.OddBet;
import com.shcheglov.task.consoleroulette.domain.Player;
import com.shcheglov.task.consoleroulette.domain.SingleBet;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.shcheglov.task.consoleroulette.converter.InputToBetConverter.BET_TYPE_EVEN;
import static com.shcheglov.task.consoleroulette.converter.InputToBetConverter.BET_TYPE_ODD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class InputToBetConverterTest {

    private final InputToBetConverter inputToBetConverter = new InputToBetConverter();

    private static Stream<Arguments> testDataSource() {
        return Stream.of(
                arguments(new String[]{"John", "2", "100"}, new SingleBet(new Player("John"), 2, 100)),
                arguments(new String[]{"Jane", BET_TYPE_ODD, "200"}, new OddBet(new Player("Jane"), 200)),
                arguments(new String[]{"Alex", BET_TYPE_EVEN, "300"}, new EvenBet(new Player("Alex"), 300))
        );
    }

    @ParameterizedTest
    @MethodSource("testDataSource")
    public void shouldCreateBetFromRawInput(final String[] validBetTokens,
                                            final Bet expectedBet) {
        // when
        final Bet actualBet = inputToBetConverter.convert(new Player(validBetTokens[0]), validBetTokens);

        // then
        assertEquals(expectedBet.getPlayer(), actualBet.getPlayer());
        assertEquals(expectedBet.getAmount(), actualBet.getAmount());
        assertEquals(expectedBet.getBetType(), actualBet.getBetType());
    }

}