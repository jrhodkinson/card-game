package jrh.game.api.action;

import jrh.game.api.Action;
import jrh.game.common.InstanceId;
import jrh.game.common.User;

public class PlayCard implements Action {

    private final User user;
    private final InstanceId cardInstanceId;
    private final InstanceId target;

    public PlayCard(User user, InstanceId cardInstanceId, InstanceId target) {
        this.user = user;
        this.cardInstanceId = cardInstanceId;
        this.target = target;
    }

    public User getUser() {
        return user;
    }

    public InstanceId getCardInstanceId() {
        return cardInstanceId;
    }

    public InstanceId getTarget() {
        return target;
    }
}
