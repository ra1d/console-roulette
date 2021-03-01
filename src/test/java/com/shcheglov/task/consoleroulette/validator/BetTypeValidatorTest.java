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

class BetTypeValidatorTest {

    private final BetTypeValidator validator = new BetTypeValidator();

    private static Stream<Arguments> testDataSource_HappyPath() {
        return Stream.of(
                arguments(List.of("X", "1", "100")),
                arguments(List.of("Mr_Smith", "17", "12345")),
                arguments(List.of("Li", "36", "1000000000")),
                arguments(List.of("R2D2", BET_TYPE_ODD, "22")),
                arguments(List.of("012345678901234567890123456789", BET_TYPE_EVEN, "300"))
        );
    }

    private static Stream<Arguments> testDataSource_UnhappyPath() {
        return Stream.of(
                arguments(List.of("X", "", "100")),
                arguments(List.of("Li", "0", "1000000000")),
                arguments(List.of("Li", "37", "1000000000")),
                arguments(List.of("R2D2", "ODDD", "22")),
                arguments(List.of("C3P0", "odd", "33")),
                arguments(List.of("012345678901234567890123456789", "eVEN", "300")),
                arguments(List.of("012345678901234567890123456789", "EVE N", "300")),
                arguments(List.of("012345678901234567890123456789", "SPLIT", "300"))
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
                () -> validator.accept(inputTokens.toArray(new String[1]))
        );

        // then
        assertThat(thrownException.getMessage()).contains(inputTokens.get(1));
    }

}