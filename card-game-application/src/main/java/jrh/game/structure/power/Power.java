package jrh.game.structure.power;

import jrh.game.event.EventHandler;
import jrh.game.match.api.Damageable;
import jrh.game.match.api.Match;
import jrh.game.match.api.Player;
import jrh.game.structure.Structure;

public abstract class Power implements EventHandler {

    private Match match;
    private Structure structure;

    public final void inContext(Match match, Structure structure) {
        this.match = match;
        this.structure = structure;
    }

    protected final Match getMatch() {
        return match;
    }

    protected final Structure getStructure() {
        return structure;
    }

    abstract public Power duplicate();

    public int modifyDamage(Player source, Damageable target, int damage) {
        return damage;
    }
}
