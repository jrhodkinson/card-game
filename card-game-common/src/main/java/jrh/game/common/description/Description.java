package jrh.game.common.description;

import java.util.List;
import java.util.stream.Collectors;

public class Description {

    private final List<AtomicDescription> atomicDescriptions;

    private Description(List<AtomicDescription> atomicDescriptions) {
        this.atomicDescriptions = atomicDescriptions;
    }

    public static Description of(List<AtomicDescription> atomicDescriptions) {
        return new Description(List.copyOf(atomicDescriptions));
    }

    @Override
    public String toString() {
        return atomicDescriptions.stream().map(AtomicDescription::toString).collect(Collectors.joining(". "))
                + ".";
    }
}
