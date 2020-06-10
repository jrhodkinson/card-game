package jrh.game.structure.event;

import jrh.game.event.Event;
import jrh.game.api.Player;
import jrh.game.api.Structure;

public class StructureConstructed implements Event {

    private final Structure structure;
    private final Player owner;

    public StructureConstructed(Structure structure, Player owner) {
        this.structure = structure;
        this.owner = owner;
    }

    public Structure getStructure() {
        return structure;
    }

    public Player getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return String.format("%s structure=%s", getClass().getSimpleName(), structure);
    }
}
