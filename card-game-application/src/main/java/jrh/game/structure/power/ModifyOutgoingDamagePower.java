package jrh.game.structure.power;

import jrh.game.match.api.Damageable;
import jrh.game.match.api.Player;
import jrh.game.structure.StructureStateController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModifyOutgoingDamagePower extends Power {

    private static final Logger logger = LogManager.getLogger(ModifyOutgoingDamagePower.class);

    private final int amount;

    public ModifyOutgoingDamagePower(int amount) {
        this.amount = amount;
    }

    @Override
    public int modifyDamage(Player source, Damageable target, int damage) {
        if (!source.equals(getMatch().getController(StructureStateController.class).getOwner(getStructure()))) {
            return damage;
        }
        int modifiedDamage = damage + amount;
        return Math.max(modifiedDamage, 0);
    }

    @Override
    public ModifyOutgoingDamagePower duplicate() {
        return new ModifyOutgoingDamagePower(amount);
    }
}
