package jrh.game.service.lobby;

import jrh.game.common.account.AccountId;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MatchQueue {

    private final BlockingQueue<AccountId> queue = new LinkedBlockingQueue<>();

    public int size() {
        return queue.size();
    }

    public void join(AccountId accountId) {
        if (!queue.contains(accountId)) {
            queue.add(accountId);
        }
    }

    public void remove(AccountId accountId) {
        queue.remove(accountId);
    }

    public boolean contains(AccountId accountId) {
        return queue.contains(accountId);
    }

    public AccountId poll() {
        return queue.poll();
    }
}
