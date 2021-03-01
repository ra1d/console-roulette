package com.shcheglov.task.consoleroulette.logic;

import com.shcheglov.task.consoleroulette.dao.BetDao;
import com.shcheglov.task.consoleroulette.dao.PlayerDao;
import com.shcheglov.task.consoleroulette.domain.Bet;
import com.shcheglov.task.consoleroulette.domain.BetOutcome;
import com.shcheglov.task.consoleroulette.domain.EvenBet;
import com.shcheglov.task.consoleroulette.domain.OddBet;
import com.shcheglov.task.consoleroulette.domain.Player;
import com.shcheglov.task.consoleroulette.domain.SingleBet;
import com.shcheglov.task.consoleroulette.ui.UIUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static com.shcheglov.task.consoleroulette.logic.SpinTask.COL_BET;
import static com.shcheglov.task.consoleroulette.logic.SpinTask.COL_OUTCOME;
import static com.shcheglov.task.consoleroulette.logic.SpinTask.COL_PLAYER;
import static com.shcheglov.task.consoleroulette.logic.SpinTask.COL_TOTAL_BET;
import static com.shcheglov.task.consoleroulette.logic.SpinTask.COL_TOTAL_WIN;
import static com.shcheglov.task.consoleroulette.logic.SpinTask.COL_WINNINGS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpinTaskTest {

    @Mock
    private BetDao betDao;

    @Mock
    private PlayerDao playerDao;

    @Mock
    private RouletteEngine rouletteEngine;

    @Mock
    private UIUtils uiUtils;

    @InjectMocks
    private SpinTask spinTask;

    @Captor
    private ArgumentCaptor<Object[]> outcomeColumnHeadersCaptor;

    @Captor
    private ArgumentCaptor<BetOutcome> betOutcomeCaptor;

    @Captor
    private ArgumentCaptor<Object[]> balanceColumnHeadersCaptor;

    @Captor
    private ArgumentCaptor<Player> playerCaptor;

    private final Player player1 = new Player("John", 5, 20);
    private final Player player2 = new Player("Jane", 0, 10);

    private final Bet bet1 = new SingleBet(player1, 13, 10);
    private final Bet bet2 = new EvenBet(player2, 20);
    private final Bet bet3 = new OddBet(player1, 30);

    private final BetOutcome outcome1 = new BetOutcome(bet1, BetOutcome.Type.LOSE, 0);
    private final BetOutcome outcome2 = new BetOutcome(bet2, BetOutcome.Type.LOSE, 0);
    private final BetOutcome outcome3 = new BetOutcome(bet3, BetOutcome.Type.WIN, 60);

    @BeforeEach
    void setUp() {
        when(betDao.getAll()).thenReturn(List.of(bet1, bet2, bet3));
        when(playerDao.getAll()).thenReturn(Map.of("John", player1, "Jane", player2));
        when(rouletteEngine.play(eq(bet1), anyInt())).thenReturn(outcome1);
        when(rouletteEngine.play(eq(bet2), anyInt())).thenReturn(outcome2);
        when(rouletteEngine.play(eq(bet3), anyInt())).thenReturn(outcome3);
    }

    @Test
    void shouldPrintBettingOutcomeReport() {
        // when
        spinTask.spin();

        // then
        verify(uiUtils).printOutcomeRow(outcomeColumnHeadersCaptor.capture());
        verify(uiUtils, times(3)).printOutcomeRow(betOutcomeCaptor.capture());
        verify(uiUtils).printPlayerTotalRow(balanceColumnHeadersCaptor.capture());
        verify(uiUtils, times(2)).printPlayerTotalRow(playerCaptor.capture());
        verify(betDao).deleteAll();

        assertThat(outcomeColumnHeadersCaptor.getValue()).containsExactly(COL_PLAYER, COL_BET, COL_OUTCOME, COL_WINNINGS);
        assertThat(betOutcomeCaptor.getAllValues()).containsExactlyInAnyOrder(outcome1, outcome2, outcome3);
        assertThat(balanceColumnHeadersCaptor.getValue()).containsExactly(COL_PLAYER, COL_TOTAL_WIN, COL_TOTAL_BET);
        assertThat(playerCaptor.getAllValues()).containsExactlyInAnyOrder(player1, player2)
                .extracting(Player::getName, Player::getTotalWin, Player::getTotalBet)
                .containsExactlyInAnyOrder(
                        tuple("John", 5L + 60L, 20L + 10L + 30L),
                        tuple("Jane", 0L, 10L + 20L));
    }

}