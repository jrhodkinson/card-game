package jrh.game.api.action;

import jrh.game.api.Action;
import jrh.game.common.InstanceId;
import jrh.game.common.User;

public class BuyCard implements Action {

    private final User user;
    private final InstanceId cardInstanceId;

    public BuyCard(User user, InstanceId cardInstanceId) {
        this.user = user;
        this.cardInstanceId = cardInstanceId;
    }

    public User getUser() {
        return user;
    }

    public InstanceId getCardInstanceId() {
        return cardInstanceId;
    }
}
