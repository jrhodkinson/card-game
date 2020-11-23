package jrh.game.api.action;

import jrh.game.api.Action;
import jrh.game.common.EntityId;
import jrh.game.common.User;

public class PlayCard implements Action {

    private final User user;
    private final EntityId cardEntityId;
    private final EntityId target;

    public PlayCard(User user, EntityId cardEntityId, EntityId target) {
        this.user = user;
        this.cardEntityId = cardEntityId;
        this.target = target;
    }

    @Override
    public User getActor() {
        return user;
    }

    public EntityId getCardEntityId() {
        return cardEntityId;
    }

    public EntityId getTarget() {
        return target;
    }
}
