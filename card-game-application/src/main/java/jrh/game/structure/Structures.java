package jrh.game.structure;

import jrh.game.api.Structure;
import jrh.game.common.InstanceId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Structures {

    private final List<MutableStructure> structures = new ArrayList<>();

    public void add(MutableStructure structure) {
        this.structures.add(structure);
    }

    public boolean contains(InstanceId instanceId) {
        return structures.stream().anyMatch(mutableStructure -> mutableStructure.getInstanceId().equals(instanceId));
    }

    public void remove(InstanceId instanceId) {
        structures.removeIf(mutableStructure -> mutableStructure.getInstanceId().equals(instanceId));
    }

    public int size() {
        return structures.size();
    }

    public MutableStructure get(int index) {
        return structures.get(index);
    }

    public Stream<MutableStructure> stream() {
        return structures.stream();
    }

    @Override
    public String toString() {
        return structures.toString();
    }

    public Collection<Structure> unmodifiable() {
        return Collections.unmodifiableCollection(structures);
    }
}
