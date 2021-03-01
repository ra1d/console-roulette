package com.shcheglov.task.consoleroulette.dao;

import com.shcheglov.task.consoleroulette.domain.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.unmodifiableMap;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toConcurrentMap;
import static org.springframework.util.ResourceUtils.getFile;

@Repository("playerDao")
@Slf4j
public class PlayerDaoImpl implements PlayerDao {

    private static final Map<String, Player> PLAYERS_BY_NAME = new ConcurrentHashMap<>();

    public PlayerDaoImpl() {
        try {
            PLAYERS_BY_NAME.putAll(Files.lines(getFile("classpath:players.txt").toPath())
                    .map(this::lineToPlayer)
                    .collect(toConcurrentMap(Player::getName, identity())));
            log.info("The following players were found: [\n" + String.join("\n", PLAYERS_BY_NAME.keySet()) + "\n]");
        } catch (final IOException e) {
            log.warn("Could not read the list of players!", e);
        }
    }

    private Player lineToPlayer(final String playerRawInput) {
        final String[] playerRawInputTokens = playerRawInput.split(",");
        return new Player(playerRawInputTokens[0],
                Double.valueOf(playerRawInputTokens[1]).longValue(),
                Double.valueOf(playerRawInputTokens[2]).longValue());
    }

    @Override
    public Map<String, Player> getAll() {
        return unmodifiableMap(PLAYERS_BY_NAME);
    }

    @Override
    public Optional<Player> getByName(final String playerName) {
        return Optional.ofNullable(PLAYERS_BY_NAME.get(playerName));
    }

}
