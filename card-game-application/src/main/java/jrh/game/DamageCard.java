package jrh.game;

public class DamageCard extends Card {

    private final int damage;

    public DamageCard(int damage) {
        super(1);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return String.format("%d damage", damage);
    }
}
