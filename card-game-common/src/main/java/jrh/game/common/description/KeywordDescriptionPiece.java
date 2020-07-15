package jrh.game.common.description;

public class KeywordDescriptionPiece implements DescriptionPiece {

    private final String value;

    public KeywordDescriptionPiece(String value) {
        this.value = value;
    }

    @Override
    public String lowercase() {
        return value.toLowerCase();
    }
}
