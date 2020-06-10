package jrh.game.api;

import jrh.game.common.User;

import java.util.Collection;

public interface Player extends Damageable {

    User getUser();

    int getHealth();

    Collection<Structure> getStructures();
}
