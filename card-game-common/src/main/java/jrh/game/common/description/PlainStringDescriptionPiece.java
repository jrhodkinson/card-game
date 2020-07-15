package jrh.game.common.description;

public class PlainStringDescriptionPiece implements DescriptionPiece {

    private final String value;

    public PlainStringDescriptionPiece(String value) {
        this.value = value;
    }

    @Override
    public String get() {
        return value;
    }
}
