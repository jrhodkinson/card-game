package jrh.game.common.description;

import java.util.Collections;
import java.util.List;

public class Description {

    private final List<AtomicDescription> atomicDescriptions;

    private Description(List<AtomicDescription> atomicDescriptions) {
        this.atomicDescriptions = atomicDescriptions;
    }

    public static Description of(List<AtomicDescription> atomicDescriptions) {
        return new Description(List.copyOf(atomicDescriptions));
    }

    public List<AtomicDescription> getAtomicDescriptions() {
        return Collections.unmodifiableList(atomicDescriptions);
    }
}
