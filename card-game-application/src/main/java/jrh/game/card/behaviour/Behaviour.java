package jrh.game.card.behaviour;

import jrh.game.match.Match;
import jrh.game.match.Target;

public interface Behaviour {
    void play(Match match, Target target);
}
