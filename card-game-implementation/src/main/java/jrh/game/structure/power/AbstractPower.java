package jrh.game.structure.power;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jrh.game.api.Damageable;
import jrh.game.api.Match;
import jrh.game.api.Player;
import jrh.game.api.Power;
import jrh.game.api.Structure;
import jrh.game.common.CardId;
import jrh.game.common.description.AtomicDescription;
import jrh.game.structure.StructureStateController;

public abstract class AbstractPower implements Power {

    private Structure structure;

    public final void forStructure(Structure structure) {
        this.structure = structure;
    }

    protected final Structure getStructure() {
        return structure;
    }

    protected final Player getOwner(Match match) {
        return match.getController(StructureStateController.class).getOwner(structure);
    }

    @Override
    @JsonIgnore
    abstract public AtomicDescription getDescription();

    @Override
    abstract public AbstractPower duplicate();

    @Override
    public int modifyDamage(Match match, Player source, Damageable target, int damage) {
        return damage;
    }

    @Override
    public int modifyCost(Match match, Player purchaser, CardId cardId, int currentCost) {
        return currentCost;
    }
}
