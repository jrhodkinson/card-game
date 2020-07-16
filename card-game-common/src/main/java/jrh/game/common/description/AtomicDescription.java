package jrh.game.common.description;

import jrh.game.common.StructureId;
import jrh.game.common.Target;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class AtomicDescription {

    private final Builder builder;

    private AtomicDescription(Builder builder) {
        this.builder = builder;
    }

    public static AtomicDescription keyword(String keyword) {
        return builder().keyword(keyword).build();
    }

    public static AtomicDescription.Builder builder() {
        return new Builder();
    }

    public String get(DescriptionContext descriptionContext) {
        return builder.get(descriptionContext);
    }

    public static class Builder {

        private final List<DescriptionPiece> pieces = new ArrayList<>();

        private Builder() {

        }

        public Builder keyword(String keyword) {
            pieces.add(new KeywordDescriptionPiece(keyword));
            return this;
        }

        public Builder number(int number) {
            pieces.add(new IntegerDescriptionPiece(number));
            return this;
        }

        public Builder targets(List<Target> targets) {
            pieces.add(new TargetDescriptionPiece(targets));
            return this;
        }

        public Builder structure(StructureId structureId) {
            pieces.add(new StructureDescriptionPiece(structureId));
            return this;
        }

        public Builder plainString(String plainString) {
            pieces.add(new PlainStringDescriptionPiece(plainString));
            return this;
        }

        public AtomicDescription build() {
            return new AtomicDescription(this);
        }

        private String get(DescriptionContext descriptionContext) {
            String description = pieces.stream()
                .map(descriptionPiece -> descriptionPiece.get(descriptionContext))
                .collect(joining(" "));
            if (description.length() > 0) {
                description = description.substring(0, 1).toUpperCase() + description.substring(1);
            }
            return description;
        }
    }
}
