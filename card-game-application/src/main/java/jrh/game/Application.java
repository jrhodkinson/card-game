package jrh.game;

import jrh.game.action.BuyCard;
import jrh.game.action.EndTurn;
import jrh.game.action.PlayCard;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Application {

    private static final Logger logger = LogManager.getLogger(Application.class);

    Application() {

    }

    void start() {
        Player hero = new Player("Hero", hand(5));
        Player villain = new Player("Villain", hand(5));
        Match match = new Match(hero, villain);
        populateStore(match);
        simulateGame(match);
    }

    private void simulateGame(Match match) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println(match.toString());
            Turn currentTurn = match.getCurrentTurn();
            List<Card> cards = match.getActivePlayer().getHand().getCards();
            System.out.print(Color.BLUE);
            String optionFormat = "%d: %-15s";
            for (int i = 0; i < cards.size(); i++) {
                System.out.printf(optionFormat, i + 1, cards.get(i));
            }
            System.out.println(Color.YELLOW);
            for (int i = 0; i < match.getStore().getCards().size(); i++) {
                System.out.printf(optionFormat, i + 1 + cards.size(), match.getStore().getCards().get(i));
            }
            System.out.print(Color.RED);
            System.out.printf("%n" + optionFormat + optionFormat + "%n", match.getStore().getCards().size() + cards.size() + 1, "End turn", match.getStore().getCards().size() + cards.size() + 2, "Quit");
            System.out.print(Color.RESET);
            int option = scanner.nextInt();
            if (option > 0 && option < cards.size() + 1) {
                Card card = cards.get(option - 1);
                if (card instanceof DamageCard) {
                    match.accept(new PlayCard(card, match.getInactivePlayer()));
                } else if (card instanceof MoneyCard) {
                    match.accept(new PlayCard(card, null));
                } else {
                    throw new NotImplementedException("Not implemented yet");
                }
            } else if (option >= cards.size() + 1 && option < cards.size() + match.getStore().getCards().size() + 1) {
                Card card = match.getStore().getCards().get(option - 1 - cards.size());
                match.accept(new BuyCard(card));
            } else if (option == cards.size() + match.getStore().getCards().size() + 1) {
                match.accept(new EndTurn());
            } else {
                running = false;
            }
            System.out.println();
        }
    }

    private Hand hand(int size) {
        Random random = new Random();
        Hand hand = new Hand();
        for (int i = 0; i < size; i++) {
            if (random.nextBoolean()) {
                hand.addCard(new DamageCard(1 + random.nextInt(5)));
            } else {
                hand.addCard(new MoneyCard(1 + random.nextInt(2)));
            }
        }
        return hand;
    }

    private void populateStore(Match match) {
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            if (random.nextBoolean()) {
                match.getStore().addCard(new DamageCard(2 + random.nextInt(10)));
            } else {
                match.getStore().addCard(new MoneyCard(2 + random.nextInt(3)));
            }
        }
    }
}
