package com.shcheglov.task.consoleroulette.service;

import com.shcheglov.task.consoleroulette.domain.Bet;

public interface BetService {

    Bet createBet(String betRawInput);

}
