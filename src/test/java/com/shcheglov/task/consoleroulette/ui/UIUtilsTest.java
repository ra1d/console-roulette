package com.shcheglov.task.consoleroulette.ui;

import com.shcheglov.task.consoleroulette.domain.BetOutcome;
import com.shcheglov.task.consoleroulette.domain.Player;
import com.shcheglov.task.consoleroulette.domain.SingleBet;
import org.junit.jupiter.api.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UIUtilsTest {

    private final UIUtils uiUtils = new UIUtils();

    @Test
    void shouldPrintOutcomeRowFromArray() throws Exception {
        // when
        final String textWrittenToSystemOut =
                tapSystemOutNormalized(() -> uiUtils.printOutcomeRow(new Object[]{"Col1", "Col2", "Col3", "Col4"}));

        // then
        assertEquals("Col1" + " ".repeat(27) +
                        "Col2" + " ".repeat(3) +
                        "Col3" + " ".repeat(5) +
                        "Col4" + " ".repeat(16) + "\n",
                textWrittenToSystemOut);
    }

    @Test
    void shouldPrintOutcomeRowFromBetOutcome() throws Exception {
        // given
        final BetOutcome betOutcome =
                new BetOutcome(new SingleBet(new Player("John_Smith"), 7, 10), BetOutcome.Type.LOSE, 0);

        // when
        final String textWrittenToSystemOut = tapSystemOutNormalized(() -> uiUtils.printOutcomeRow(betOutcome));

        // then
        assertEquals("John_Smith" + " ".repeat(21) +
                        "7" + " ".repeat(6) +
                        "LOSE" + " ".repeat(5) +
                        "0" + " ".repeat(19) + "\n",
                textWrittenToSystemOut);
    }

    @Test
    void shouldPrintPlayerTotalRowFromArray() throws Exception {
        // when
        final String textWrittenToSystemOut =
                tapSystemOutNormalized(() -> uiUtils.printPlayerTotalRow(new Object[]{"Col1", "Col2", "Col3"}));

        // then
        assertEquals("Col1" + " ".repeat(27) +
                        "Col2" + " ".repeat(9) +
                        "Col3" + " ".repeat(8) + "\n",
                textWrittenToSystemOut);
    }

    @Test
    void shouldPrintPlayerTotalRowFromPlayer() throws Exception {
        // given
        final BetOutcome betOutcome =
                new BetOutcome(new SingleBet(new Player("John_Smith"), 7, 10), BetOutcome.Type.LOSE, 0);

        // when
        final String textWrittenToSystemOut = tapSystemOutNormalized(() -> uiUtils.printPlayerTotalRow(new Player("John_Smith", 100L, 2300L)));

        // then
        assertEquals("John_Smith" + " ".repeat(21) +
                        "100" + " ".repeat(10) +
                        "2300" + " ".repeat(8) + "\n",
                textWrittenToSystemOut);
    }

}