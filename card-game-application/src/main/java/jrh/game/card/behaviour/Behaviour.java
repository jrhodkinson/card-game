package jrh.game.card.behaviour;

import jrh.game.card.Card;
import jrh.game.match.Match;
import jrh.game.match.Player;

public interface Behaviour {
    void play(Match match, Player player, Card card);
}
