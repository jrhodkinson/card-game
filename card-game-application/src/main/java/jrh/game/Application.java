package jrh.game;

import jrh.game.action.BuyCardFromPermanentPile;
import jrh.game.action.BuyCardFromRow;
import jrh.game.action.EndTurn;
import jrh.game.action.PlayCard;
import jrh.game.card.Card;
import jrh.game.card.CardFactory;
import jrh.game.card.CardId;
import jrh.game.card.FileSystemLibrary;
import jrh.game.card.Library;
import jrh.game.card.behaviour.BasicBehaviour;
import jrh.game.deck.Hand;
import jrh.game.deck.Pile;
import jrh.game.deck.Row;
import jrh.game.deck.Store;
import jrh.game.match.Match;
import jrh.game.match.Player;
import jrh.game.match.Turn;
import jrh.game.util.Color;
import jrh.game.util.Constants;
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
        Library library = new FileSystemLibrary(Constants.CARDS_DIRECTORY);
        CardFactory cardFactory = new CardFactory(library, new Random());
        Store store = new Store(cardFactory, Constants.STORE_SIZE, List.of(new Pile(library.getCard(CardId.COPPER), 10)));
        Player hero = new Player(new User("Hero"), cardFactory.startingDeck());
        Player villain = new Player(new User("Villain"), cardFactory.startingDeck());
        Match match = new Match(store, hero, villain);
        match.start();
        simulateGame(match);
    }

    private void simulateGame(Match match) {
        Scanner scanner = new Scanner(System.in);
        while (!match.isOver()) {
            System.out.println(match.toString());
            Turn currentTurn = match.getCurrentTurn();
            Hand hand = match.getActivePlayer().getHand();
            String optionFormat = "%2d: %-30s";
            System.out.printf(optionFormat, 0, option("Play all"));
            for (int i = 0; i < hand.size(); i++) {
                System.out.printf(optionFormat, i + 1, hand.get(i));
            }
            System.out.println();
            Row row = match.getStore().getRow();
            for (int i = 0; i < row.size(); i++) {
                System.out.printf(optionFormat, i + 1 + hand.size(), row.get(i));
            }
            List<Pile> permanentPiles = match.getStore().getPermanentPiles();
            for (int i = 0; i < permanentPiles.size(); i++) {
                System.out.printf(optionFormat, i + 1 + hand.size() + row.size(), permanentPiles.get(i).getCard() + " - " + permanentPiles.get(i).getQuantity());
            }
            System.out.printf("%n" + optionFormat + optionFormat + "%n", permanentPiles.size() + row.size() + hand.size() + 1,
                    option("End turn"), permanentPiles.size() + row.size() + hand.size() + 2, option("Quit"));
            int option = scanner.nextInt();
            if (option == 0) {
                while (hand.size() > 0 && !match.isOver()) {
                    playCard(match, hand.get(0));
                }
            } else if (option > 0 && option < hand.size() + 1) {
                playCard(match, hand.get(option - 1));
            } else if (option >= hand.size() + 1 && option < hand.size() + row.size() + 1) {
                Card card = row.get(option - 1 - hand.size());
                match.accept(new BuyCardFromRow(card));
            } else if (option >= hand.size() + row.size() + 1 && option < hand.size() + row.size() + permanentPiles.size() + 1) {
                Card card = permanentPiles.get(option - 1 - hand.size() - row.size()).getCard();
                match.accept(new BuyCardFromPermanentPile(card));
            } else if (option == hand.size() + row.size() + permanentPiles.size() + 1) {
                match.accept(new EndTurn());
            }
            System.out.println();
        }
        System.out.println(Color.GREEN + "Winner: " + match.getWinner().getUser());
    }

    private void playCard(Match match, Card card) {
        if (card instanceof BasicBehaviour) {
            match.accept(new PlayCard(card, null));
        } else {
            match.accept(new PlayCard(card, match.getInactivePlayer()));
        }
    }

    private String option(String plainText) {
        return Color.CYAN + plainText + Color.RESET;
    }
}
