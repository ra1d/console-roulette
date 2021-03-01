package com.shcheglov.task.consoleroulette.service;

import com.shcheglov.task.consoleroulette.converter.InputToBetConverter;
import com.shcheglov.task.consoleroulette.dao.BetDao;
import com.shcheglov.task.consoleroulette.dao.PlayerDao;
import com.shcheglov.task.consoleroulette.domain.Bet;
import com.shcheglov.task.consoleroulette.domain.Player;
import com.shcheglov.task.consoleroulette.domain.SingleBet;
import com.shcheglov.task.consoleroulette.validator.BetAmountValidator;
import com.shcheglov.task.consoleroulette.validator.BetTypeValidator;
import com.shcheglov.task.consoleroulette.validator.GeneralInputValidator;
import com.shcheglov.task.consoleroulette.validator.NameValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BetServiceTest {

    @Mock
    private GeneralInputValidator generalInputValidator;

    @Mock
    private NameValidator nameValidator;

    @Mock
    private BetTypeValidator betTypeValidator;

    @Mock
    private BetAmountValidator betAmountValidator;

    @Mock
    private InputToBetConverter inputToBetConverter;

    @Mock
    private PlayerDao playerDao;

    @Mock
    private BetDao betDao;

    @InjectMocks
    private BetServiceImpl betService;

    @Test
    public void shouldCreateBetFromRawInputAndStoreIt() {
        // given
        final String playerName = "John";
        final Player player = new Player(playerName);
        final SingleBet expectedBet = new SingleBet(player, 2, 100);
        final String[] rawInputTokens = {playerName, "2", "100"};
        when(playerDao.getByName(playerName)).thenReturn(Optional.of(player));
        when(inputToBetConverter.convert(player, rawInputTokens)).thenReturn(expectedBet);
        when(betDao.save(expectedBet)).thenReturn(expectedBet);

        // when
        final Bet actualBet = betService.createBet("John 2 100");

        // then
        verify(generalInputValidator).accept(rawInputTokens);
        verify(nameValidator).accept(rawInputTokens);
        verify(betTypeValidator).accept(rawInputTokens);
        verify(betAmountValidator).accept(rawInputTokens);
        verify(betDao).save(expectedBet);

        assertEquals(expectedBet, actualBet);
    }

    @Test
    public void shouldFailCreatingBetWhenNoSuchPlayer() {
        // given
        final String playerName = "John";
        when(playerDao.getByName(anyString())).thenReturn(Optional.empty());

        // when
        final IllegalArgumentException thrownException = assertThrows(
                IllegalArgumentException.class,
                () -> betService.createBet("John 2 200")
        );

        // then
        verifyNoInteractions(inputToBetConverter);
        verifyNoInteractions(betDao);

        assertTrue(thrownException.getMessage().contains(playerName));
    }

    @Test
    public void shouldFailCreatingBetWhenNoInput() {
        // when
        final IllegalArgumentException thrownException = assertThrows(
                IllegalArgumentException.class,
                () -> betService.createBet(null)
        );

        // then
        verifyNoInteractions(playerDao);
        verifyNoInteractions(inputToBetConverter);
        verifyNoInteractions(betDao);

        assertTrue(thrownException.getMessage().contains("No input provided"));
    }

}