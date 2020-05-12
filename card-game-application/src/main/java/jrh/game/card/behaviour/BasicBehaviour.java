package jrh.game.card.behaviour;

import jrh.game.match.Match;
import jrh.game.match.Target;

public interface BasicBehaviour extends Behaviour {

    void play(Match match);

    @Override
    default void play(Match match, Target target) {
        play(match);
    }
}
