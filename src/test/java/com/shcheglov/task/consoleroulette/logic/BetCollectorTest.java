package com.shcheglov.task.consoleroulette.logic;

import com.shcheglov.task.consoleroulette.domain.EvenBet;
import com.shcheglov.task.consoleroulette.domain.Player;
import com.shcheglov.task.consoleroulette.domain.SingleBet;
import com.shcheglov.task.consoleroulette.service.BetService;
import com.shcheglov.task.consoleroulette.ui.UIUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static com.shcheglov.task.consoleroulette.logic.BetCollector.COMMAND_QUIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BetCollectorTest {

    @Mock
    private BetService betService;

    @Mock
    private UIUtils uiUtils;

    @InjectMocks
    private BetCollector betCollector;

    @Captor
    private ArgumentCaptor<String> inputCaptor;

    @BeforeEach
    void setUp() {
        when(betService.createBet(anyString()))
                .thenReturn(new EvenBet(new Player("John"), 10),
                        new SingleBet(new Player("Jane"), 27, 20));
    }

    @Test
    void shouldCreate2BetsBasedOnInputAndQuit() throws Exception {
        // given
        final String betRawInput1 = "John EVEN 10";
        final String betRawInput2 = "Jane 27 20";

        // when
        catchSystemExit(() -> withTextFromSystemIn(betRawInput1, betRawInput2, COMMAND_QUIT)
                .execute(() -> betCollector.startTakingBets()));

        // then
        verify(betService, times(2)).createBet(inputCaptor.capture());
        assertThat(inputCaptor.getAllValues()).containsExactly(betRawInput1, betRawInput2);

        verify(uiUtils, times(4)).clearScreen();
    }

    @Test
    void shouldContinueTryingToCreateBetAfterFailure() throws Exception {
        // given
        final String betRawInput1 = "John EVEN 10";
        final String betRawInput2 = "Jane 27 20";
        when(betService.createBet(betRawInput1)).thenThrow(IllegalArgumentException.class);
        when(betService.createBet(betRawInput2)).thenReturn(new SingleBet(new Player("Jane"), 27, 20));

        // when
        catchSystemExit(() -> withTextFromSystemIn(betRawInput1, betRawInput2, COMMAND_QUIT)
                .execute(() -> betCollector.startTakingBets()));

        // then
        verify(betService, times(2)).createBet(inputCaptor.capture());
        assertThat(inputCaptor.getAllValues()).containsExactly(betRawInput1, betRawInput2);

        verify(uiUtils, times(4)).clearScreen();
    }

}