package com.shcheglov.task.consoleroulette.ui;

import com.shcheglov.task.consoleroulette.domain.BetOutcome;
import com.shcheglov.task.consoleroulette.domain.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("uiUtils")
@Slf4j
public class UIUtils {

    public static final String HORIZONTAL_SEPARATOR =
            "---------------------------------------------------------------------------------";

    public void clearScreen() {
        System.out.println();
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final IOException | InterruptedException e) {
            log.warn("Failed clearing the screen!", e);
        }
    }

    public void printOutcomeRow(final Object[] outcomeFields) {
        System.out.printf("%-30s %-6s %-8s %-20s%n", outcomeFields);
    }

    public void printOutcomeRow(final BetOutcome outcome) {
        printOutcomeRow(new Object[]{
                outcome.getBet().getPlayer().getName(),
                outcome.getBet().getBetType(),
                outcome.getType(),
                outcome.getWinningAmount()
        });
    }

    public void printPlayerTotalRow(final Object[] playerTotalFields) {
        System.out.printf("%-30s %-12s %-12s%n", playerTotalFields);
    }

    public void printPlayerTotalRow(final Player player) {
        printPlayerTotalRow(new Object[]{player.getName(), player.getTotalWin(), player.getTotalBet()});
    }

}
