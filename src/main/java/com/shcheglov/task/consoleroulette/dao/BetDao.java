package com.shcheglov.task.consoleroulette.dao;

import com.shcheglov.task.consoleroulette.domain.Bet;

import java.util.List;

public interface BetDao {

    Bet save(Bet bet);

    List<Bet> getAll();

    void deleteAll();

}
