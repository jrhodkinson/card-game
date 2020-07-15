package jrh.game.common.description;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class AtomicDescription {

    private final String description;

    private AtomicDescription(Builder builder) {
        this.description = builder.pieces.stream()
            .map(DescriptionPiece::get)
            .collect(joining(" "));
    }

    public static AtomicDescription keyword(String keyword) {
        return builder().keyword(keyword).build();
    }

    public static AtomicDescription.Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return description;
    }

    public static class Builder {

        private final List<DescriptionPiece> pieces = new ArrayList<>();

        private Builder() {

        }

        public Builder plainString(String plainString) {
            pieces.add(new PlainStringDescriptionPiece(plainString));
            return this;
        }

        public Builder keyword(String keyword) {
            pieces.add(new KeywordDescriptionPiece(keyword));
            return this;
        }

        public Builder number(int number) {
            pieces.add(new IntegerDescriptionPiece(number));
            return this;
        }

        public AtomicDescription build() {
            return new AtomicDescription(this);
        }
    }
}
