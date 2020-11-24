package jrh.game.service.lobby;

import jrh.game.common.account.AccountId;

public class Matchmaker {

    private final MatchManager matchManager;
    private AccountId queue = AccountId.fromString("043107f6-2c82-4306-8416-5ba9d882c323");

    public Matchmaker(MatchManager matchManager) {
        this.matchManager = matchManager;
    }

    public synchronized void queue(AccountId accountId) {
        if (queue == null) {
            queue = accountId;
        } else {
            matchManager.newMatch(queue, accountId);
            queue = null;
        }
    }

}
