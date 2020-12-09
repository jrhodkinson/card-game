package jrh.game.common.description;

import java.util.Optional;

public interface DescriptionPiece {
    String get(DescriptionContext descriptionContext);

    default Optional<String> getContext(DescriptionContext descriptionContext) {
        return Optional.empty();
    }
}
