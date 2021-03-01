package com.shcheglov.task.consoleroulette.logic;

import com.shcheglov.task.consoleroulette.domain.Bet;
import com.shcheglov.task.consoleroulette.service.BetService;
import com.shcheglov.task.consoleroulette.ui.UIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.shcheglov.task.consoleroulette.ui.UIUtils.HORIZONTAL_SEPARATOR;

@Component
@Slf4j
public class BetCollector {

    public static final String COMMAND_QUIT = "quit";

    private final BetService betService;
    private final UIUtils uiUtils;

    @Autowired
    public BetCollector(@Qualifier("betService") final BetService betService,
                        @Qualifier("uiUtils") final UIUtils uiUtils) {
        this.betService = betService;
        this.uiUtils = uiUtils;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startTakingBets() {
        String input;
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            uiUtils.clearScreen();
            printPrompt();
            input = scanner.nextLine().trim();
            if (COMMAND_QUIT.equalsIgnoreCase(input)) {
                break;
            } else {
                try {
                    final Bet bet = betService.createBet(input);
                    System.out.printf("Your bet: %s on %s%n%n", bet.getAmount(), bet.getBetType());
                } catch (final Exception e) {
                    System.out.println("Couldn't accept your bet, please try again.");
                    log.debug(String.format("Couldn't create a bet based on the input [%s]!", input), e);
                }
            }
        }
        uiUtils.clearScreen();
        System.out.println("See you later!");
        System.exit(0);
    }

    private static void printPrompt() {
        System.out.printf(HORIZONTAL_SEPARATOR + "%n" +
                "Type your name, bet type and amount separated by spaces and press ENTER.%n" +
                HORIZONTAL_SEPARATOR + "%n" +
                "Supported name format:%n" +
                " alphanumeric characters, hyphens and underscores, no spaces; up to 30 characters%n%n" +
                "Supported types of bets:%n" +
                " - a number from 1 to 36 inclusive%n" +
                " - EVEN%n" +
                " - ODD%n%n" +
                "Supported amounts:%n" +
                " whole numbers from 1 to 1,000,000,000%n" +
                HORIZONTAL_SEPARATOR + "%n" +
                "Type 'quit' and press ENTER to stop the application.%n" +
                HORIZONTAL_SEPARATOR + "%n");
    }

}
