package jrh.game;

import jrh.game.api.action.BuyCardFromPermanentPile;
import jrh.game.api.action.BuyCardFromRow;
import jrh.game.api.action.EndTurn;
import jrh.game.api.action.PlayCard;
import jrh.game.api.Card;
import jrh.game.api.Damageable;
import jrh.game.asset.AssetLibrary;
import jrh.game.asset.FileSystemAssetLibrary;
import jrh.game.common.Color;
import jrh.game.common.User;
import jrh.game.deck.Hand;
import jrh.game.deck.Pile;
import jrh.game.match.MutableMatch;
import jrh.game.match.MutableTurn;
import jrh.game.service.Service;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.util.List;
import java.util.Scanner;

public class Application {

    private static final Logger logger = LogManager.getLogger(Application.class);
    private static final String OPTION_FORMAT = "%2d: %-32s";

    private final AssetLibrary assetLibrary;
    private final Scanner scanner;
    private MutableMatch match;

    Application() {
        this.assetLibrary = new FileSystemAssetLibrary(Constants.ASSETS_DIRECTORY);
        this.scanner = new Scanner(System.in);
    }

    void start() {
        match = new MutableMatch(assetLibrary, new User("Hero"), new User("Villain"));
        Service service = new Service(7000);
        service.start(match, match.getEventBus());
        match.start();
        simulateGame();
    }

    private void simulateGame() {
        boolean isRunning = true;
        while (!match.isOver() && isRunning) {
            Configurator.setLevel("jrh", Level.ERROR);
            System.out.println(match.toString());
            MutableTurn currentTurn = match.getCurrentTurn();
            Hand hand = match.getActivePlayer().getHand();
            System.out.printf(OPTION_FORMAT, 0, optionString("Play all"));
            for (int i = 0; i < hand.size(); i++) {
                System.out.printf(OPTION_FORMAT, i + 1, hand.get(i));
            }
            List<Card> row = match.getStore().getRow();
            for (int i = 0; i < row.size(); i++) {
                if (i % 7 == 0) {
                    System.out.println();
                }
                System.out.printf(OPTION_FORMAT, i + 1 + hand.size(), cardString(row.get(i)));
            }
            List<Pile> permanentPiles = match.getStore().getPermanentPiles();
            for (int i = 0; i < permanentPiles.size(); i++) {
                System.out.printf(OPTION_FORMAT, i + 1 + hand.size() + row.size(),
                        cardString(permanentPiles.get(i).getCard()) + " - " + permanentPiles.get(i).getQuantity());
            }
            System.out.printf("%n" + OPTION_FORMAT + OPTION_FORMAT + "%n",
                    permanentPiles.size() + row.size() + hand.size() + 1, optionString("End turn"),
                    permanentPiles.size() + row.size() + hand.size() + 2, optionString("Quit"));
            Configurator.setLevel("jrh", Level.DEBUG);
            int option = scanner.nextInt();
            if (option == 0) {
                while (hand.size() > 0 && !match.isOver()) {
                    playCard(hand.get(0));
                }
            } else if (option > 0 && option < hand.size() + 1) {
                playCard(hand.get(option - 1));
            } else if (option >= hand.size() + 1 && option < hand.size() + row.size() + 1) {
                Card card = row.get(option - 1 - hand.size());
                match.getActionHandler().accept(new BuyCardFromRow(match.getActivePlayer(), card));
            } else if (option >= hand.size() + row.size() + 1
                    && option < hand.size() + row.size() + permanentPiles.size() + 1) {
                Card card = permanentPiles.get(option - 1 - hand.size() - row.size()).getCard();
                match.getActionHandler().accept(new BuyCardFromPermanentPile(match.getActivePlayer(), card));
            } else if (option == hand.size() + row.size() + permanentPiles.size() + 1) {
                match.getActionHandler().accept(new EndTurn(match.getActivePlayer().getUser()));
            } else {
                isRunning = false;
            }
            System.out.println();
        }
        if (match.isOver()) {
            System.out.println(Color.GREEN + "Winner: " + match.getWinner().getUser());
        }
    }

    private void playCard(Card card) {
        if (card.requiresTarget()) {
            match.getActionHandler().accept(new PlayCard(match.getActivePlayer().getUser(), card.getInstanceId(), getTarget()));
        } else {
            match.getActionHandler().accept(new PlayCard(match.getActivePlayer().getUser(), card.getInstanceId(), null));
        }
    }

    private Damageable getTarget() {
        System.out.println(option(1, match.getActivePlayer().getUser().toString()));
        System.out.println(option(2, match.getInactivePlayer().getUser().toString()));
        for (int i = 0; i < match.getActivePlayer().getStructuresAsMutable().size(); i++) {
            System.out.println(option(i + 3, "A - " + match.getActivePlayer().getStructuresAsMutable().get(i)));
        }
        for (int i = 0; i < match.getInactivePlayer().getStructuresAsMutable().size(); i++) {
            System.out.println(option(i + match.getActivePlayer().getStructuresAsMutable().size() + 3,
                    "I - " + match.getInactivePlayer().getStructuresAsMutable().get(i)));
        }
        int choice = scanner.nextInt();
        if (choice == 1) {
            return match.getActivePlayer();
        }
        if (choice == 2) {
            return match.getInactivePlayer();
        }
        if (choice > 2 && choice < match.getActivePlayer().getStructuresAsMutable().size() + 3) {
            return match.getActivePlayer().getStructuresAsMutable().get(choice - 3);
        }
        return match.getInactivePlayer().getStructuresAsMutable()
                .get(choice - 3 - match.getActivePlayer().getStructuresAsMutable().size());
    }

    private String option(int number, String text) {
        return String.format(OPTION_FORMAT, number, text);
    }

    private String optionString(String plainText) {
        return Color.CYAN + plainText + Color.RESET;
    }

    private String cardString(Card card) {
        return String.format("%s%s (%d)%s", card.getColor(), card.getName(),
                match.getModificationComputer().computeModifiedCost(match.getActivePlayer(), card), Color.RESET);
    }
}
