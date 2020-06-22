package jrh.game.common;

import java.util.List;
import java.util.stream.Collectors;

public class CardDescription {

    private final List<BehaviourDescription> behaviourDescriptions;

    private CardDescription(List<BehaviourDescription> behaviourDescriptions) {
        this.behaviourDescriptions = behaviourDescriptions;
    }

    public static CardDescription fromBehaviourDescriptions(List<BehaviourDescription> behaviourDescriptions) {
        return new CardDescription(List.copyOf(behaviourDescriptions));
    }

    @Override
    public String toString() {
        return behaviourDescriptions.stream().map(BehaviourDescription::toString).collect(Collectors.joining(". "))
                + ".";
    }
}
