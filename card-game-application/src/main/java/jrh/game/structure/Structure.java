package jrh.game.structure;

public class Structure {

    private final StructureId structureId;
    private int health;

    public Structure(StructureId structureId) {
        this.structureId = structureId;
    }

    public int getHealth() {
        return health;
    }

    void changeHealth(int amount) {
        health += amount;
    }

}
