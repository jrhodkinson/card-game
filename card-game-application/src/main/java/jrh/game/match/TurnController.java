package jrh.game.match;

import jrh.game.api.Controller;

public class TurnController implements Controller {

    private final MutableMatch match;

    public TurnController(MutableMatch match) {
        this.match = match;
    }

    public void changeMoney(int amount) {
        match.getCurrentTurn().setMoney(match.getCurrentTurn().getMoney() + amount);
    }
}
