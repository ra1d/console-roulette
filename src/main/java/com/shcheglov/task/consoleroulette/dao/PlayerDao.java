package com.shcheglov.task.consoleroulette.dao;

import com.shcheglov.task.consoleroulette.domain.Player;

import java.util.Map;
import java.util.Optional;

public interface PlayerDao {

    Map<String, Player> getAll();

    Optional<Player> getByName(String playerName);

}
