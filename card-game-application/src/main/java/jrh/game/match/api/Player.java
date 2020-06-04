package jrh.game.match.api;

import jrh.game.User;
import jrh.game.structure.api.Structure;

import java.util.Collection;

public interface Player extends Damageable {

    User getUser();

    int getHealth();

    Collection<Structure> getStructures();
}
