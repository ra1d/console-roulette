package com.shcheglov.task.consoleroulette.dao;


import java.util.concurrent.atomic.AtomicLong;

public final class BetIdGenerator {

    private static final AtomicLong SEQUENCE = new AtomicLong();

    private BetIdGenerator() {
        // NOOP
    }

    public static long next() {
        return SEQUENCE.getAndIncrement();
    }

}
