package jrh.game.common;

import java.util.ArrayList;
import java.util.List;

public class BehaviourDescription {

    private final String description;

    private BehaviourDescription(Builder builder) {
        this.description = String.join(" ", builder.pieces);
    }

    public static BehaviourDescription keyword(String keyword) {
        return builder().keyword(keyword).build();
    }

    public static BehaviourDescription.Builder builder() {
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
            pieces.add(String.format("%s%s%s", Color.RED, keyword, Color.RESET));
            return this;
        }

        public Builder number(int number) {
            pieces.add(String.format("%s%d%s", Color.BLUE, number, Color.RESET));
            return this;
        }

        public BehaviourDescription build() {
            return new BehaviourDescription(this);
        }
    }
}
