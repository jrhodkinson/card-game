package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.match.Match;
import jrh.game.match.Player;

public class DamageBehaviour implements Behaviour {

    @JsonValue
    private final int damage;

    public DamageBehaviour(int damage) {
        this.damage = damage;
    }

    @Override
    public void play(Match match, Player player) {
        match.getDamageController().damage(player, this.damage);
    }
}
