package jrh.game.alert;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingAlertService implements AlertService {

    private static final Logger logger = LogManager.getLogger(LoggingAlertService.class);

    @Override
    public void sendAlert(String message) {
        logger.info("Would have sent alert={}", message);
    }
}
