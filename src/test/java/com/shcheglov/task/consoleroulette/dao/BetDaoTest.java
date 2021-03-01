package com.shcheglov.task.consoleroulette.dao;

import com.shcheglov.task.consoleroulette.domain.Bet;
import com.shcheglov.task.consoleroulette.domain.EvenBet;
import com.shcheglov.task.consoleroulette.domain.OddBet;
import com.shcheglov.task.consoleroulette.domain.Player;
import com.shcheglov.task.consoleroulette.domain.SingleBet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BetDaoTest {

    private final Player player1 = new Player("Jane");
    private final Player player2 = new Player("John");

    private final Bet bet1 = new OddBet(player1, 100);
    private final Bet bet2 = new EvenBet(player2, 200);
    private final Bet bet3 = new SingleBet(player2, 17, 300);

    private final BetDao betDao = new BetDaoImpl();

    @BeforeEach
    void setUp() {
        betDao.save(bet1);
        betDao.save(bet2);
        betDao.save(bet3);
    }

    @Test
    void shouldReturn3AlreadyStoredBets() {
        // when
        final List<Bet> allBets = betDao.getAll();

        // then
        assertThat(allBets).containsExactlyInAnyOrder(bet1, bet2, bet3);
    }

    @Test
    void shouldRemoveAllBetsAndReturnNothing() {
        // when
        betDao.deleteAll();
        final List<Bet> allBets = betDao.getAll();

        // then
        assertThat(allBets).isEmpty();
    }

}