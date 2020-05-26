package jrh.game.structure;

public class Structure {

    private int health;

    public int getHealth() {
        return health;
    }

    void changeHealth(int amount) {
        health += amount;
    }

}
