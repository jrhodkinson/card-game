package jrh.game.structure;

import jrh.game.match.api.Damageable;

public class Structure implements Damageable {

    private final StructureId structureId;
    private int health;

    public Structure(StructureId structureId) {
        this.structureId = structureId;
    }

    public int getHealth() {
        return health;
    }

    public void changeHealth(int amount) {
        health += amount;
    }

    Structure duplicate() {
        return new Structure(structureId);
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", structureId.toString(), health);
    }
}
