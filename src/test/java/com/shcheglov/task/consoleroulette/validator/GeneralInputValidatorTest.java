package com.shcheglov.task.consoleroulette.validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.shcheglov.task.consoleroulette.converter.InputToBetConverter.BET_TYPE_EVEN;
import static com.shcheglov.task.consoleroulette.converter.InputToBetConverter.BET_TYPE_ODD;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class GeneralInputValidatorTest {

    private final GeneralInputValidator validator = new GeneralInputValidator();

    private static Stream<Arguments> testDataSource_HappyPath() {
        return Stream.of(
                arguments(List.of("John", "1", "100")),
                arguments(List.of("John", "36", "1000000000")),
                arguments(List.of("Jane", BET_TYPE_ODD, "200")),
                arguments(List.of("Jane", BET_TYPE_ODD, "1")),
                arguments(List.of("Alex", BET_TYPE_EVEN, "300"))
        );
    }

    private static Stream<Arguments> testDataSource_UnhappyPath() {
        return Stream.of(
                arguments(List.of("John")),
                arguments(List.of("John", "1")),
                arguments(List.of("John", "1", "100", "2")),
                arguments(List.of("Jane", BET_TYPE_ODD)),
                arguments(List.of("Jane", BET_TYPE_ODD, "1", "2")),
                arguments(List.of("Alex", BET_TYPE_EVEN)),
                arguments(List.of("Alex", BET_TYPE_EVEN, "20", "300"))
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
                () -> validator.accept(inputTokens.toArray(new String[0]))
        );

        // then
        assertTrue(thrownException.getMessage().contains(String.join(", ", inputTokens)));
    }

}