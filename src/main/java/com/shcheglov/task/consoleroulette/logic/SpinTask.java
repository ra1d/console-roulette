package com.shcheglov.task.consoleroulette.logic;

import com.shcheglov.task.consoleroulette.dao.BetDao;
import com.shcheglov.task.consoleroulette.dao.PlayerDao;
import com.shcheglov.task.consoleroulette.domain.BetOutcome;
import com.shcheglov.task.consoleroulette.ui.UIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

import static com.shcheglov.task.consoleroulette.ui.UIUtils.HORIZONTAL_SEPARATOR;

@Component
public class SpinTask {

    static final String COL_PLAYER = "Player";
    static final String COL_BET = "Bet";
    static final String COL_OUTCOME = "Outcome";
    static final String COL_WINNINGS = "Winnings";
    static final String COL_TOTAL_WIN = "Total Win";
    static final String COL_TOTAL_BET = "Total Bet";

    private static final String INITIAL_DELAY_DEFAULT = "30000";
    private static final String SPINNING_INTERVAL_DEFAULT = "30000";

    private final Random random = new Random();

    private final BetDao betDao;
    private final PlayerDao playerDao;
    private final RouletteEngine rouletteEngine;
    private final UIUtils uiUtils;

    @Autowired
    public SpinTask(@Qualifier("betDao") final BetDao betDao,
                    @Qualifier("playerDao") final PlayerDao playerDao,
                    @Qualifier("rouletteEngine") final RouletteEngine rouletteEngine,
                    @Qualifier("uiUtils") final UIUtils uiUtils) {
        this.betDao = betDao;
        this.playerDao = playerDao;
        this.rouletteEngine = rouletteEngine;
        this.uiUtils = uiUtils;
    }

    @Scheduled(initialDelayString = "${initial-delay:" + INITIAL_DELAY_DEFAULT + "}",
            fixedRateString = "${spinning-interval:" + SPINNING_INTERVAL_DEFAULT + "}")
    public void spin() {
        System.out.printf("%n" + HORIZONTAL_SEPARATOR + "%n");
        processBets();
        System.out.printf("%n" + HORIZONTAL_SEPARATOR + "%n");
        processTotal();
        System.out.println();
    }

    private void processBets() {
        final int winningNumber = random.nextInt(37);
        System.out.printf("Number: %s%n", winningNumber);
        System.out.println("---");
        uiUtils.printOutcomeRow(new Object[]{COL_PLAYER, COL_BET, COL_OUTCOME, COL_WINNINGS});
        System.out.println("---");
        betDao.getAll().stream()
                .map(bet -> rouletteEngine.play(bet, winningNumber))
                .peek(BetOutcome::updatePlayerTotal)
                .forEach(uiUtils::printOutcomeRow);
        betDao.deleteAll();
    }

    private void processTotal() {
        uiUtils.printPlayerTotalRow(new Object[]{COL_PLAYER, COL_TOTAL_WIN, COL_TOTAL_BET});
        System.out.println("---");
        playerDao.getAll().values()
                .forEach(uiUtils::printPlayerTotalRow);
    }

}
