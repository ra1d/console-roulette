package com.shcheglov.task.consoleroulette.validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.shcheglov.task.consoleroulette.converter.InputToBetConverter.BET_TYPE_EVEN;
import static com.shcheglov.task.consoleroulette.converter.InputToBetConverter.BET_TYPE_ODD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class BetAmountValidatorTest {

    private final BetAmountValidator validator = new BetAmountValidator();

    private static Stream<Arguments> testDataSource_HappyPath() {
        return Stream.of(
                arguments(List.of("X", "7", "1")),
                arguments(List.of("MrSmith", BET_TYPE_ODD, "12345")),
                arguments(List.of("T_T", BET_TYPE_EVEN, "1000000000"))
        );
    }

    private static Stream<Arguments> testDataSource_UnhappyPath() {
        return Stream.of(
                arguments(List.of("X", "1", "")),
                arguments(List.of("Mr_Smith", BET_TYPE_ODD, "-1")),
                arguments(List.of("Jane", BET_TYPE_EVEN, "0")),
                arguments(List.of("007", "13", "1000000001")),
                arguments(List.of("b0b", BET_TYPE_ODD, "1,000,000")),
                arguments(List.of("l33t_h4ck3r", "9", "99.9"))
        );
    }

    @ParameterizedTest
    @MethodSource("testDataSource_HappyPath")
    void shouldAcceptInputWhen3Tokens(final List<String> inputTokens) {
        // when
        validator.accept(inputTokens.toArray(new String[0]));
    }

    @ParameterizedTest
    @MethodSource("testDataSource_UnhappyPath")
    void shouldFailWhenNot3Tokens(final List<String> inputTokens) {
        // when
        final IllegalArgumentException thrownException = assertThrows(
                IllegalArgumentException.class,
                () -> validator.accept(inputTokens.toArray(new String[2]))
        );

        // then
        assertThat(thrownException.getMessage()).contains(inputTokens.get(2));
    }

}