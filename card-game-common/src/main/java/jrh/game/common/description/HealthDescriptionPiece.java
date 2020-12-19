package jrh.game.common.description;

public class HealthDescriptionPiece implements DescriptionPiece {

    private final int health;

    public HealthDescriptionPiece(int health) {
        this.health = health;
    }

    @Override
    public String get(DescriptionContext descriptionContext) {
        return String.format("%d health", health);
    }
}
