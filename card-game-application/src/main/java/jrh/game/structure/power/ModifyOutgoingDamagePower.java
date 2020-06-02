package jrh.game.structure.power;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.match.api.Damageable;
import jrh.game.match.api.Match;
import jrh.game.match.api.Player;
import jrh.game.structure.StructureStateController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModifyOutgoingDamagePower extends Power {

    private static final Logger logger = LogManager.getLogger(ModifyOutgoingDamagePower.class);

    @JsonValue
    private final int amount;

    public ModifyOutgoingDamagePower(int amount) {
        this.amount = amount;
    }

    @Override
    public int modifyDamage(Match match, Player source, Damageable target, int damage) {
        if (!source.equals(match.getController(StructureStateController.class).getOwner(getStructure()))) {
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
