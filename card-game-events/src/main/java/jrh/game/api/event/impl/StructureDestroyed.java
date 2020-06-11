package jrh.game.api.event.impl;

import jrh.game.api.event.Event;
import jrh.game.api.Structure;

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
