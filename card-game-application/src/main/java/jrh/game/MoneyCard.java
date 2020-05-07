package jrh.game;

public class MoneyCard extends Card {

    private final int amount;

    public MoneyCard(int amount) {
        super(0);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("%d money", amount);
    }
}
