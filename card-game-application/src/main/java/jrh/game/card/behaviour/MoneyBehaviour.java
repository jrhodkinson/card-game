package jrh.game.card.behaviour;

import jrh.game.match.Match;

public class MoneyBehaviour implements BasicBehaviour {

    private final int amount;

    public MoneyBehaviour(int amount) {
        this.amount = amount;
    }

    @Override
    public void play(Match match) {
        match.getCurrentTurn().setMoney(match.getCurrentTurn().getMoney() + amount);
    }
}
