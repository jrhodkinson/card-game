package jrh.game.common.description;

public class IntegerDescriptionPiece implements DescriptionPiece {

    private final int value;

    public IntegerDescriptionPiece(int value) {
        this.value = value;
    }

    @Override
    public String get() {
        return String.valueOf(value);
    }
}
