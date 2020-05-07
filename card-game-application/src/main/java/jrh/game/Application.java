package jrh.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

public class Application {

    private static final Logger logger = LogManager.getLogger(Application.class);

    Application() {

    }

    void start() {
        Player hero = new Player("Hero", hand(5));
        Player villain = new Player("Villain", hand(5));
        Match match = new Match(List.of(hero, villain));
        logger.info(match);
    }

    private Hand hand(int size) {
        Random random = new Random();
        Hand hand = new Hand();
        for (int i = 0; i < size; i++) {
            if (random.nextBoolean()) {
                hand.addCard(new DamageCard(1 + random.nextInt(10)));
            } else {
                hand.addCard(new MoneyCard(1 + random.nextInt(2)));
            }
        }
        return hand;
    }
}
