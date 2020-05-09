package jrh.game;

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
        simulateGame(match);
    }

    private void simulateGame(Match match) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println(match.toString());
            List<Card> cards = match.activePlayer().getHand().getCards();
            System.out.print("\033[0;34m"); // BLUE
            String optionFormat = "%d: %-12s";
            for (int i = 0; i < cards.size(); i++) {
                System.out.printf(optionFormat, i + 1, cards.get(i));
            }
            System.out.printf("%n" + optionFormat + optionFormat + "%n", cards.size() + 1, "End turn", cards.size() + 2, "Quit");
            System.out.print("\033[0m"); // RESET
            int option = scanner.nextInt();
            if (option > 0 && option < cards.size() + 1) {
                Card card = cards.get(option - 1);
                if (card instanceof DamageCard) {
                    match.accept(new PlayCard(card, match.inactivePlayer()));
                } else if (card instanceof MoneyCard) {
                    match.accept(new PlayCard(card, match.activePlayer()));
                } else {
                    throw new NotImplementedException("Not implemented yet");
                }
            } else if (option == cards.size() + 1) {
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
                hand.addCard(new DamageCard(1 + random.nextInt(10)));
            } else {
                hand.addCard(new MoneyCard(1 + random.nextInt(2)));
            }
        }
        return hand;
    }
}
