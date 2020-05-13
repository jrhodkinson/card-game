package jrh.game.action;

import jrh.game.card.Card;
import jrh.game.match.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BuyCardFromRow implements Action {

    private static final Logger logger = LogManager.getLogger(BuyCardFromRow.class);

    private final Card card;

    public BuyCardFromRow(Card card) {
        this.card = card;
    }

    @Override
    public void applyTo(Match match) {
        int money = match.getCurrentTurn().getMoney();
        int cost = card.getCost();
        if (money < cost) {
            logger.error("Not enough money to buy card (money={}, cost={})", money, cost);
            return;
        }
        if (!match.getStore().removeFromRow(card)) {
            logger.error("Card={} is not in the store row", card);
            return;
        }
        match.getCurrentTurn().setMoney(money - cost);
        match.getActivePlayer().getDeckAndDiscardPile().getDiscardPile().add(card);
    }
}
