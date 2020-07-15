package jrh.game.common.description;

import java.util.ArrayList;
import java.util.List;

public class AtomicDescription {

    private final String description;

    private AtomicDescription(Builder builder) {
        this.description = String.join(" ", builder.pieces);
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

        private final List<String> pieces = new ArrayList<>();

        private Builder() {

        }

        public Builder plainString(String plainString) {
            pieces.add(plainString);
            return this;
        }

        public Builder keyword(String keyword) {
            pieces.add(keyword);
            return this;
        }

        public Builder number(int number) {
            pieces.add(String.valueOf(number));
            return this;
        }

        public AtomicDescription build() {
            return new AtomicDescription(this);
        }
    }
}
