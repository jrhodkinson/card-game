package jrh.game;

import jrh.game.action.BuyCardFromPermanentPile;
import jrh.game.action.BuyCardFromRow;
import jrh.game.action.EndTurn;
import jrh.game.action.PlayCard;
import jrh.game.asset.AssetLibrary;
import jrh.game.asset.FileSystemAssetLibrary;
import jrh.game.card.Card;
import jrh.game.deck.Hand;
import jrh.game.deck.Pile;
import jrh.game.deck.Row;
import jrh.game.match.MutableMatch;
import jrh.game.match.MutableTurn;
import jrh.game.match.api.Damageable;
import jrh.game.util.Color;
import jrh.game.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class Application {

    private static final Logger logger = LogManager.getLogger(Application.class);
    private static final String OPTION_FORMAT = "%2d: %-32s";

    private final AssetLibrary assetLibrary;
    private final Scanner scanner;

    Application() {
        this.assetLibrary = new FileSystemAssetLibrary(Constants.CARDS_DIRECTORY);
        this.scanner = new Scanner(System.in);
    }

    void start() {
        MutableMatch match = new MutableMatch(assetLibrary, new User("Hero"), new User("Villain"));
        match.start();
        simulateGame(match);
    }

    private void simulateGame(MutableMatch match) {
        boolean isRunning = true;
        while (!match.isOver() && isRunning) {
            System.out.println(match.toString());
            MutableTurn currentTurn = match.getCurrentTurn();
            Hand hand = match.getActivePlayer().getHand();
            System.out.printf(OPTION_FORMAT, 0, optionString("Play all"));
            for (int i = 0; i < hand.size(); i++) {
                System.out.printf(OPTION_FORMAT, i + 1, hand.get(i));
            }
            Row row = match.getStore().getRow();
            for (int i = 0; i < row.size(); i++) {
                if (i % 7 == 0) {
                    System.out.println();
                }
                System.out.printf(OPTION_FORMAT, i + 1 + hand.size(), row.get(i));
            }
            List<Pile> permanentPiles = match.getStore().getPermanentPiles();
            for (int i = 0; i < permanentPiles.size(); i++) {
                System.out.printf(OPTION_FORMAT, i + 1 + hand.size() + row.size(),
                        permanentPiles.get(i).getCard() + " - " + permanentPiles.get(i).getQuantity());
            }
            System.out.printf("%n" + OPTION_FORMAT + OPTION_FORMAT + "%n",
                    permanentPiles.size() + row.size() + hand.size() + 1, optionString("End turn"),
                    permanentPiles.size() + row.size() + hand.size() + 2, optionString("Quit"));
            int option = scanner.nextInt();
            if (option == 0) {
                while (hand.size() > 0 && !match.isOver()) {
                    playCard(match, hand.get(0));
                }
            } else if (option > 0 && option < hand.size() + 1) {
                playCard(match, hand.get(option - 1));
            } else if (option >= hand.size() + 1 && option < hand.size() + row.size() + 1) {
                Card card = row.get(option - 1 - hand.size());
                (new BuyCardFromRow(match, match.getActivePlayer(), card)).perform();
            } else if (option >= hand.size() + row.size() + 1
                    && option < hand.size() + row.size() + permanentPiles.size() + 1) {
                Card card = permanentPiles.get(option - 1 - hand.size() - row.size()).getCard();
                (new BuyCardFromPermanentPile(match, match.getActivePlayer(), card)).perform();
            } else if (option == hand.size() + row.size() + permanentPiles.size() + 1) {
                (new EndTurn(match)).perform();
            } else {
                isRunning = false;
            }
            System.out.println();
        }
        if (match.isOver()) {
            System.out.println(Color.GREEN + "Winner: " + match.getWinner().getUser());
        }
    }

    private void playCard(MutableMatch match, Card card) {
        if (card.requiresTarget()) {
            (new PlayCard(match, match.getActivePlayer(), card, getTarget(match))).perform();
        } else {
            (new PlayCard(match, match.getActivePlayer(), card, null)).perform();
        }
    }

    private Damageable getTarget(MutableMatch match) {
        System.out.println(option(1, match.getActivePlayer().getUser().toString()));
        System.out.println(option(2, match.getInactivePlayer().getUser().toString()));
        for (int i = 0; i < match.getActivePlayer().getStructures().size(); i++) {
            System.out.println(option(i + 3, "A - " + match.getActivePlayer().getStructures().get(i)));
        }
        for (int i = 0; i < match.getInactivePlayer().getStructures().size(); i++) {
            System.out.println(option(i + match.getActivePlayer().getStructures().size() + 3, "I - " + match.getActivePlayer().getStructures().get(i)));
        }
        int choice = scanner.nextInt();
        if (choice == 1) {
            return match.getActivePlayer();
        }
        if (choice == 2) {
            return match.getInactivePlayer();
        }
        if (choice > 2 && choice < match.getActivePlayer().getStructures().size() + 3) {
            return match.getActivePlayer().getStructures().get(choice - 3);
        }
        return match.getInactivePlayer().getStructures().get(choice - 3 - match.getActivePlayer().getStructures().size());
    }

    private String option(int number, String text) {
        return String.format(OPTION_FORMAT, number, text);
    }

    private String optionString(String plainText) {
        return Color.CYAN + plainText + Color.RESET;
    }
}
