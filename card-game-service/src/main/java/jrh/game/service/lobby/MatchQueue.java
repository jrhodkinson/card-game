package jrh.game.service.lobby;

import jrh.game.common.account.AccountId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MatchQueue {

    private static final Logger logger = LogManager.getLogger(MatchQueue.class);
    private static final long QUEUE_TIMEOUT_MS = 20 * 1000;

    private final BlockingDeque<AccountId> queue = new LinkedBlockingDeque<>();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private final Map<AccountId, Future<?>> leaveQueueFutures = new ConcurrentHashMap<>();

    public int size() {
        return queue.size();
    }

    public void join(AccountId accountId) {
        if (!queue.contains(accountId)) {
            logger.info("accountId={} joined the back of the queue", accountId);
            queue.addLast(accountId);
        }
        logger.debug("Queue now contains {} accounts", queue.size());
        refresh(accountId);
    }

    public void prioritise(AccountId accountId) {
        queue.remove(accountId);
        logger.info("accountId={} joined the front of the queue", accountId);
        queue.addFirst(accountId);
        logger.debug("Queue now contains {} accounts", queue.size());
        refresh(accountId);
    }

    public void remove(AccountId accountId) {
        if (queue.remove(accountId)) {
            logger.info("accountId={} left the queue", accountId);
        }
        logger.debug("Queue now contains {} accounts", queue.size());
        Future<?> leaveQueue = leaveQueueFutures.remove(accountId);
        if (leaveQueue != null) {
            leaveQueue.cancel(true);
        }
    }

    public boolean contains(AccountId accountId) {
        return queue.contains(accountId);
    }

    public void refresh(AccountId accountId) {
        Future<?> leaveQueue = leaveQueueFutures.remove(accountId);
        if (leaveQueue != null) {
            leaveQueue.cancel(true);
        }
        Future<?> newLeaveQueue = scheduledExecutorService.schedule(() -> {
            remove(accountId);
            leaveQueueFutures.remove(accountId);
        }, QUEUE_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        leaveQueueFutures.put(accountId, newLeaveQueue);
    }

    public AccountId poll() {
        return queue.poll();
    }
}
