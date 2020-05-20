package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.event.bus.Subscribe;
import jrh.game.event.impl.CardPlayed;
import jrh.game.match.Match;

public class MoneyBehaviour extends Behaviour {

    @JsonValue
    private final int amount;

    public MoneyBehaviour(int amount) {
        this.amount = amount;
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard())) {
            match.getCurrentTurn().setMoney(match.getCurrentTurn().getMoney() + amount);
        }
    }

    @Override
    public MoneyBehaviour duplicate() {
        return new MoneyBehaviour(amount);
    }
}
