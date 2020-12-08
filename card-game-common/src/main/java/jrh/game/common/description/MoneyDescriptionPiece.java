package jrh.game.common.description;

public class MoneyDescriptionPiece implements DescriptionPiece {

    private final int value;

    public MoneyDescriptionPiece(int value) {
        this.value = value;
    }

    @Override
    public String get(DescriptionContext descriptionContext) {
        return String.format("$%s", value);
    }
}
