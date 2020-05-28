package jrh.game.match.api;

import jrh.game.User;

public interface Player extends Damageable {

    User getUser();

    int getHealth();
}
