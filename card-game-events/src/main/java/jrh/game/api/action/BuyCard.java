package jrh.game.api.action;

import jrh.game.api.Action;
import jrh.game.common.EntityId;
import jrh.game.common.User;

public class BuyCard implements Action {

    private final User user;
    private final EntityId cardEntityId;

    public BuyCard(User user, EntityId cardEntityId) {
        this.user = user;
        this.cardEntityId = cardEntityId;
    }

    @Override
    public User getActor() {
        return user;
    }

    public EntityId getCardEntityId() {
        return cardEntityId;
    }
}
