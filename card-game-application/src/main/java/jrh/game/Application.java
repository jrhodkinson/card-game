package jrh.game;

import jrh.game.action.BuyCard;
import jrh.game.action.EndTurn;
import jrh.game.action.PlayCard;
import jrh.game.card.Card;
import jrh.game.card.CardFactory;
import jrh.game.card.DamageCard;
import jrh.game.card.DrawCard;
import jrh.game.card.MoneyCard;
import jrh.game.deck.Hand;
import jrh.game.deck.Storefront;
import jrh.game.match.Match;
import jrh.game.match.Player;
import jrh.game.match.Turn;
import jrh.game.util.Colors;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Application {

    private static final Logger logger = LogManager.getLogger(Application.class);

    Application() {

    }

    void start() {
        Player hero = new Player(new User("Hero"), CardFactory.startingDeck());
        Player villain = new Player(new User("Villain"), CardFactory.startingDeck());
        Match match = new Match(hero, villain);
        match.start();
        simulateGame(match);
    }

    private void simulateGame(Match match) {
        Scanner scanner = new Scanner(System.in);
        while (!match.isOver()) {
            System.out.println(match.toString());
            Turn currentTurn = match.getCurrentTurn();
            Hand hand = match.getActivePlayer().getHand();
            String optionFormat = "%2d: %-25s";
            System.out.printf(optionFormat, 0, option("Play all"));
            for (int i = 0; i < hand.size(); i++) {
                System.out.printf(optionFormat, i + 1, hand.get(i));
            }
            System.out.println();
            Storefront storefront = match.getStore().getStorefront();
            for (int i = 0; i < storefront.size(); i++) {
                System.out.printf(optionFormat, i + 1 + hand.size(), storefront.get(i));
            }
            System.out.printf("%n" + optionFormat + optionFormat + "%n", storefront.size() + hand.size() + 1, option("End turn"), storefront.size() + hand.size() + 2, option("Quit"));
            int option = scanner.nextInt();
            if (option == 0) {
                while (hand.size() > 0) {
                    playCard(match, hand.get(0));
                }
            } else if (option > 0 && option < hand.size() + 1) {
                playCard(match, hand.get(option - 1));
            } else if (option >= hand.size() + 1 && option < hand.size() + storefront.size() + 1) {
                Card card = storefront.get(option - 1 - hand.size());
                match.accept(new BuyCard(card));
            } else if (option == hand.size() + storefront.size() + 1) {
                match.accept(new EndTurn());
            }
            System.out.println();
        }
        System.out.println(Colors.GREEN + "Winner: " + match.getWinner().getUser());
    }

    private void playCard(Match match, Card card) {
        if (card instanceof DamageCard) {
            match.accept(new PlayCard(card, match.getInactivePlayer()));
        } else if (card instanceof MoneyCard) {
            match.accept(new PlayCard(card, null));
        } else if (card instanceof DrawCard) {
            match.accept(new PlayCard(card, null));
        } else {
            throw new NotImplementedException("Not implemented yet");
        }
    }

    private String option(String plainText) {
        return Colors.CYAN + plainText + Colors.RESET;
    }
}
