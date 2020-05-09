package jrh.game.action;

import jrh.game.Card;
import jrh.game.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BuyCard implements Action {

    private static final Logger logger = LogManager.getLogger(BuyCard.class);

    private final Card card;

    public BuyCard(Card card) {
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
        if (!match.getStore().contains(card)) {
            logger.error("Card={} is not in the store", card);
            return;
        }
        match.getCurrentTurn().setMoney(money - cost);
        match.getStore().removeCard(card);
        match.getActivePlayer().getHand().addCard(card); // TODO discard pile
    }
}
