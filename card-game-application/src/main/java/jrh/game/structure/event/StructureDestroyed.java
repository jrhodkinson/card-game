package jrh.game.structure.event;

import jrh.game.event.Event;
import jrh.game.structure.Structure;

public class StructureDestroyed implements Event {

    private final Structure structure;

    public StructureDestroyed(Structure structure) {
        this.structure = structure;
    }

    public Structure getStructure() {
        return structure;
    }

    @Override
    public String toString() {
        return String.format("%s structure=%s", getClass().getSimpleName(), structure);
    }
}