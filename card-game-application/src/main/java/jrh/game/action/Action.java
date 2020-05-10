package jrh.game.action;

import jrh.game.match.Match;

public interface Action {
    void applyTo(Match match);
}
