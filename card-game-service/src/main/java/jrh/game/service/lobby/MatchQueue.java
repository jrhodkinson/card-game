package jrh.game.service.lobby;

import jrh.game.common.account.AccountId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class MatchQueue {

    private static final Logger logger = LogManager.getLogger(MatchQueue.class);

    private final BlockingDeque<AccountId> queue = new LinkedBlockingDeque<>();

    public int size() {
        return queue.size();
    }

    public void join(AccountId accountId) {
        if (!queue.contains(accountId)) {
            logger.info("accountId={} joined the back of the queue", accountId);
            queue.addLast(accountId);
        }
    }

    public void prioritise(AccountId accountId) {
        queue.remove(accountId);
        logger.info("accountId={} joined the front of the queue", accountId);
        queue.addFirst(accountId);
    }

    public void remove(AccountId accountId) {
        if (queue.remove(accountId)) {
            logger.info("accountId={} left the queue", accountId);
        }
    }

    public boolean contains(AccountId accountId) {
        return queue.contains(accountId);
    }

    public AccountId poll() {
        return queue.poll();
    }
}
