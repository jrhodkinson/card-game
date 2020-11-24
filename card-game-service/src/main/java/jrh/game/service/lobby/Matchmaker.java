package jrh.game.service.lobby;

import jrh.game.common.account.AccountId;

public class Matchmaker {

    private final MatchManager matchManager;
    private AccountId queue = null;

    public Matchmaker(MatchManager matchManager) {
        this.matchManager = matchManager;
    }

    public synchronized void queue(AccountId accountId) {
        if (queue == null) {
            queue = accountId;
        } else if (!accountId.equals(queue)) {
            matchManager.newMatch(queue, accountId);
            queue = null;
        }
    }

}
