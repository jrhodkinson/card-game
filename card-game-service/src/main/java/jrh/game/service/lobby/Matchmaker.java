package jrh.game.service.lobby;

import jrh.game.common.account.AccountId;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Matchmaker {

    private final MatchManager matchManager;
    private final MatchQueue matchQueue;

    public Matchmaker(MatchManager matchManager, MatchQueue matchQueue) {
        this.matchManager = matchManager;
        this.matchQueue = matchQueue;
    }

    public void start() {
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(this::matchmake, 0, 1, TimeUnit.SECONDS);
    }

    private void matchmake() {
        while (matchQueue.size() >= 2) {
            startOneMatch();
        }
    }

    private void startOneMatch() {
        AccountId first = matchQueue.poll();
        AccountId second = matchQueue.poll();
        if (first == null || second == null) {
            return;
        }
        if (!matchManager.isInAMatch(first) && !matchManager.isInAMatch(second)) {
            matchManager.newMatch(first, second);
        }
    }

}
