package jrh.game.service.websocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class WebSocketPinger {

    private static final Logger logger = LogManager.getLogger(WebSocketPinger.class);

    private final long PING_DELAY_S = 10;

    public WebSocketPinger(WebSocketConnectionManager webSocketConnectionManager,
            ScheduledExecutorService scheduledExecutorService) {
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            try {
                webSocketConnectionManager.broadcast(WebSocketMessages.ping());
            } catch (Exception e) {
                logger.error("Exception while pinging clients", e);
            } catch (Throwable t) {
                logger.error("Throwable while pinging clients", t);
                System.exit(1);
            }
        }, 0, PING_DELAY_S, SECONDS);
    }

    private void createPingMessage() {

    }
}
