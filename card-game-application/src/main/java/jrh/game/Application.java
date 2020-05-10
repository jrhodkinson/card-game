package jrh.game;

import jrh.game.action.BuyCard;
import jrh.game.action.EndTurn;
import jrh.game.action.PlayCard;
import jrh.game.card.Card;
import jrh.game.card.DamageCard;
import jrh.game.card.Hand;
import jrh.game.card.MoneyCard;
import jrh.game.match.Match;
import jrh.game.match.Player;
import jrh.game.match.Turn;
import jrh.game.util.Colors;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            Hand hand = match.getActivePlayer().getHand();
            System.out.print(Colors.BLUE);
            String optionFormat = "%d: %-15s";
            for (int i = 0; i < hand.size(); i++) {
                System.out.printf(optionFormat, i + 1, hand.get(i));
            }
            System.out.println(Colors.YELLOW);
            for (int i = 0; i < match.getStore().size(); i++) {
                System.out.printf(optionFormat, i + 1 + hand.size(), match.getStore().get(i));
            }
            System.out.print(Colors.RED);
            System.out.printf("%n" + optionFormat + optionFormat + "%n", match.getStore().size() + hand.size() + 1, "End turn", match.getStore().size() + hand.size() + 2, "Quit");
            System.out.print(Colors.RESET);
            int option = scanner.nextInt();
            if (option > 0 && option < hand.size() + 1) {
                Card card = hand.get(option - 1);
                if (card instanceof DamageCard) {
                    match.accept(new PlayCard(card, match.getInactivePlayer()));
                } else if (card instanceof MoneyCard) {
                    match.accept(new PlayCard(card, null));
                } else {
                    throw new NotImplementedException("Not implemented yet");
                }
            } else if (option >= hand.size() + 1 && option < hand.size() + match.getStore().size() + 1) {
                Card card = match.getStore().get(option - 1 - hand.size());
                match.accept(new BuyCard(card));
            } else if (option == hand.size() + match.getStore().size() + 1) {
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
                hand.add(new DamageCard(1 + random.nextInt(5)));
            } else {
                hand.add(new MoneyCard(1 + random.nextInt(2)));
            }
        }
        return hand;
    }

    private void populateStore(Match match) {
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            if (random.nextBoolean()) {
                match.getStore().add(new DamageCard(2 + random.nextInt(10)));
            } else {
                match.getStore().add(new MoneyCard(2 + random.nextInt(3)));
            }
        }
    }
}
