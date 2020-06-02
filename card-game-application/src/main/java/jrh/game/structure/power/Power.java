package jrh.game.structure.power;

import jrh.game.event.EventHandler;
import jrh.game.match.api.Damageable;
import jrh.game.match.api.Match;
import jrh.game.match.api.Player;
import jrh.game.structure.api.Structure;

public abstract class Power implements EventHandler {

    private Structure structure;

    public final void forStructure(Structure structure) {
        this.structure = structure;
    }

    protected final Structure getStructure() {
        return structure;
    }

    abstract public Power duplicate();

    public int modifyDamage(Match match, Player source, Damageable target, int damage) {
        return damage;
    }
}
