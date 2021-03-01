package com.shcheglov.task.consoleroulette.dao;

import com.shcheglov.task.consoleroulette.domain.Player;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerDaoTest {

    private final PlayerDao playerDao = new PlayerDaoImpl();

    @Test
    void shouldGetAllPlayers() {
        // when
        final Map<String, Player> allPlayers = playerDao.getAll();

        //then
        assertThat(allPlayers)
                .extractingFromEntries(
                        Map.Entry::getKey,
                        playerEntry -> playerEntry.getValue().getName(),
                        playerEntry -> playerEntry.getValue().getTotalWin(),
                        playerEntry -> playerEntry.getValue().getTotalBet())
                .containsExactlyInAnyOrder(
                        Tuple.tuple("Tiki_Monkey", "Tiki_Monkey", 1L, 2L),
                        Tuple.tuple("Barbara", "Barbara", 2L, 1L));
    }

    @Test
    void shouldGetPlayerByName() {
        // when
        final Optional<Player> tikiMonkey = playerDao.getByName("Tiki_Monkey");

        //then
        assertThat(tikiMonkey.get())
                .extracting(Player::getName, Player::getTotalWin, Player::getTotalBet)
                .containsExactly("Tiki_Monkey", 1L, 2L);
    }

}