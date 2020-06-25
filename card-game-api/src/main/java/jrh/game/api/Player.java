package jrh.game.api;

import jrh.game.common.InstanceId;
import jrh.game.common.User;

import java.util.Collection;
import java.util.List;

public interface Player extends Damageable {

    InstanceId getInstanceId();

    User getUser();

    int getHealth();

    Collection<Structure> getStructures();

    List<Card> getHand();

    List<Card> getDeck();

    List<Card> getDiscardPile();
}
