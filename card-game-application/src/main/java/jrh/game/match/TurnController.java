package jrh.game.match;

import jrh.game.api.Controller;
import jrh.game.api.event.MoneyChanged;

public class TurnController implements Controller {

    private final MutableMatch match;

    public TurnController(MutableMatch match) {
        this.match = match;
    }

    public void changeMoney(int amount) {
        if (amount != 0) {
            match.getCurrentTurn().setMoney(match.getCurrentTurn().getMoney() + amount);
            match.getEventBus().dispatch(new MoneyChanged());
        }
    }
}
