package jrh.game;

import jrh.game.action.BuyCardFromPermanentPile;
import jrh.game.action.BuyCardFromRow;
import jrh.game.action.EndTurn;
import jrh.game.action.PlayCard;
import jrh.game.asset.AssetLibrary;
import jrh.game.asset.FileSystemAssetLibrary;
import jrh.game.card.Card;
import jrh.game.card.behaviour.DamageBehaviour;
import jrh.game.card.behaviour.HealBehaviour;
import jrh.game.card.behaviour.IncrementingDamageBehaviour;
import jrh.game.deck.Hand;
import jrh.game.deck.Pile;
import jrh.game.deck.Row;
import jrh.game.match.MutableMatch;
import jrh.game.match.MutableTurn;
import jrh.game.util.Color;
import jrh.game.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class Application {

    private static final Logger logger = LogManager.getLogger(Application.class);

    private final AssetLibrary assetLibrary;

    Application() {
        this.assetLibrary = new FileSystemAssetLibrary(Constants.CARDS_DIRECTORY);
    }

    void start() {
        MutableMatch match = new MutableMatch(assetLibrary, new User("Hero"), new User("Villain"));
        match.start();
        simulateGame(match);
    }

    private void simulateGame(MutableMatch match) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (!match.isOver() && isRunning) {
            System.out.println(match.toString());
            MutableTurn currentTurn = match.getCurrentTurn();
            Hand hand = match.getActivePlayer().getHand();
            String optionFormat = "%2d: %-32s";
            System.out.printf(optionFormat, 0, option("Play all"));
            for (int i = 0; i < hand.size(); i++) {
                System.out.printf(optionFormat, i + 1, hand.get(i));
            }
            Row row = match.getStore().getRow();
            for (int i = 0; i < row.size(); i++) {
                if (i % 7 == 0) {
                    System.out.println();
                }
                System.out.printf(optionFormat, i + 1 + hand.size(), row.get(i));
            }
            List<Pile> permanentPiles = match.getStore().getPermanentPiles();
            for (int i = 0; i < permanentPiles.size(); i++) {
                System.out.printf(optionFormat, i + 1 + hand.size() + row.size(),
                        permanentPiles.get(i).getCard() + " - " + permanentPiles.get(i).getQuantity());
            }
            System.out.printf("%n" + optionFormat + optionFormat + "%n",
                    permanentPiles.size() + row.size() + hand.size() + 1, option("End turn"),
                    permanentPiles.size() + row.size() + hand.size() + 2, option("Quit"));
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
        if (card.hasBehaviour(HealBehaviour.class)) {
            (new PlayCard(match, match.getActivePlayer(), card, match.getActivePlayer())).perform();
        } else if (card.hasBehaviour(DamageBehaviour.class) || card.hasBehaviour(IncrementingDamageBehaviour.class)) {
            (new PlayCard(match, match.getActivePlayer(), card, match.getInactivePlayer())).perform();
        } else {
            (new PlayCard(match, match.getActivePlayer(), card, null)).perform();
        }
    }

    private String option(String plainText) {
        return Color.CYAN + plainText + Color.RESET;
    }
}
