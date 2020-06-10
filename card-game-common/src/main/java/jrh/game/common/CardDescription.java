package jrh.game.common;

import java.util.List;
import java.util.stream.Collectors;

public class CardDescription {

    private final String description;

    private CardDescription(String description) {
        this.description = description;
    }

    public static CardDescription fromBehaviourDescriptions(List<BehaviourDescription> behaviourDescriptions) {
        return new CardDescription(
                behaviourDescriptions.stream().map(BehaviourDescription::toString).collect(Collectors.joining(". "))
                        + ".");
    }

    @Override
    public String toString() {
        return description;
    }
}
