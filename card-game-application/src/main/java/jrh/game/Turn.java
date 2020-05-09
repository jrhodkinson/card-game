package jrh.game;

public class Turn {

    private final Player activePlayer;
    private final Player inactivePlayer;
    private int money;

    public Turn(Player activePlayer, Player inactivePlayer) {
        this.activePlayer = activePlayer;
        this.inactivePlayer = inactivePlayer;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public Player getInactivePlayer() {
        return inactivePlayer;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return activePlayer + " (*)" + "\n" +
                inactivePlayer + "\n" +
                money + " money";
    }
}
