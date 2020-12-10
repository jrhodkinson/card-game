package jrh.game.common.description;

import java.util.Optional;

public interface DescriptionPiece {
    String get(DescriptionContext descriptionContext);

    default Optional<String> getHelp(DescriptionContext descriptionContext) {
        return Optional.empty();
    }

    default String getType() {
        return getClass().getSimpleName().replaceAll("DescriptionPiece", "");
    }
}
