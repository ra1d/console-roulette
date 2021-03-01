package com.shcheglov.task.consoleroulette.dao;

import com.shcheglov.task.consoleroulette.domain.Bet;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Collections.unmodifiableList;

@Repository("betDao")
public class BetDaoImpl implements BetDao {

    private static final List<Bet> BETS = new CopyOnWriteArrayList<>();

    @Override
    public Bet save(final Bet bet) {
        BETS.add(bet);
        return bet;
    }

    @Override
    public List<Bet> getAll() {
        return unmodifiableList(BETS);
    }

    @Override
    public void deleteAll() {
        BETS.clear();
    }

}
