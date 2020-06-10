package jrh.game.deck;

import jrh.game.api.Card;
import jrh.game.card.CardImplFactory;

import java.util.List;
import java.util.Optional;

public class Store {

    private final CardImplFactory cardImplFactory;
    private final Row row;
    private final List<Pile> permanentPiles;

    public Store(CardImplFactory cardImplFactory, int rowSize, List<Pile> permanentPiles) {
        this.cardImplFactory = cardImplFactory;
        this.row = new Row();
        this.permanentPiles = permanentPiles;
        populateRow(rowSize);
    }

    public boolean removeFromPile(Card card) {
        Optional<Pile> optionalPile = permanentPiles.stream()
                .filter(pile -> pile.getCard().equals(card) && !pile.isEmpty()).findFirst();
        if (optionalPile.isPresent()) {
            optionalPile.get().take();
            return true;
        }
        return false;
    }

    public boolean removeFromRow(Card card) {
        int index = row.indexOf(card);
        if (index != -1) {
            row.remove(card);
            row.add(index, cardImplFactory.randomCard());
            return true;
        }
        return false;
    }

    private void populateRow(int size) {
        while (row.size() < size) {
            row.add(cardImplFactory.randomCard());
        }
    }

    public Row getRow() {
        return row;
    }

    public List<Pile> getPermanentPiles() {
        return permanentPiles;
    }

}
