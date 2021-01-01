package jrh.game.service.lobby;

import jrh.game.common.account.AccountId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Matchmaker {

    private static final Logger logger = LogManager.getLogger(Matchmaker.class);

    private final MatchManager matchManager;
    private final MatchQueue matchQueue;

    public Matchmaker(MatchManager matchManager, MatchQueue matchQueue) {
        this.matchManager = matchManager;
        this.matchQueue = matchQueue;
    }

    public void start() {
        Executors
            .newSingleThreadScheduledExecutor()
            .scheduleWithFixedDelay(safely(this::matchmake), 0, 1,
                    TimeUnit.SECONDS);
    }

    private void matchmake() {
        while (matchQueue.size() >= 2) {
            startOneMatch();
        }
    }

    private void startOneMatch() {
        AccountId first = matchQueue.poll();
        AccountId second = matchQueue.poll();
        boolean firstIsEligible = isEligible(first);
        boolean secondIsEligible = isEligible(second);
        if (firstIsEligible) {
            if (secondIsEligible) {
                logger.info("Starting match between firstAccountId={} and secondAccountId={}", first, second);
                matchManager.startMatch(first, second);
            } else {
                matchQueue.prioritise(first);
            }
        } else if (secondIsEligible) {
            matchQueue.prioritise(second);
        }
    }

    private boolean isEligible(AccountId accountId) {
        return accountId != null && !matchManager.isInAMatch(accountId);
    }

    private Runnable safely(Runnable runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                logger
                    .warn(
                            "Caught Exception processing matchmaking. Some players might have been dropped from the queue. Continuing anyway.",
                            e);
            } catch (Throwable t) {
                logger.error("Caught Throwable processing matchmaking. Exiting", t);
                System.exit(1);
            }
        };
    }

}
