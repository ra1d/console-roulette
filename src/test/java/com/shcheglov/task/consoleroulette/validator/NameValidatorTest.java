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

class NameValidatorTest {

    private final NameValidator validator = new NameValidator();

    private static Stream<Arguments> testDataSource_HappyPath() {
        return Stream.of(
                arguments(List.of("X", "1", "100")),
                arguments(List.of("Li", "36", "1000000000")),
                arguments(List.of("Billy_Bob_Thornton", BET_TYPE_ODD, "200")),
                arguments(List.of("Mary_Smith-Jones", BET_TYPE_EVEN, "1")),
                arguments(List.of("R2D2", BET_TYPE_ODD, "22")),
                arguments(List.of("012345678901234567890123456789", BET_TYPE_EVEN, "300"))
        );
    }

    private static Stream<Arguments> testDataSource_UnhappyPath() {
        return Stream.of(
                arguments(List.of("", "1", "100")),
                arguments(List.of("Li Guangyao", "36", "1000000000")),
                arguments(List.of("=^_^=", BET_TYPE_ODD, "200")),
                arguments(List.of(":-)", BET_TYPE_EVEN, "1")),
                arguments(List.of("C3P0,", BET_TYPE_ODD, "22")),
                arguments(List.of("0123456789012345678901234567890", BET_TYPE_EVEN, "300"))
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
        assertThat(thrownException.getMessage()).contains(inputTokens.get(0));
    }

}