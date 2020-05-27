package jrh.game.card;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jrh.game.asset.CardDeserializer;
import jrh.game.asset.CardSerializer;
import jrh.game.card.behaviour.Behaviour;
import jrh.game.event.EventBus;
import jrh.game.util.Color;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@JsonDeserialize(using = CardDeserializer.class)
@JsonSerialize(using = CardSerializer.class)
public class Card {

    private final UUID instanceId = UUID.randomUUID();
    private final CardId cardId;
    private final String name;
    private final int cost;
    private final Color color;
    private final Map<Class<? extends Behaviour>, Behaviour> behaviours = new HashMap<>();

    private Card(Builder builder) {
        this.cardId = builder.cardId;
        this.name = builder.name;
        this.cost = builder.cost;
        this.color = builder.color;
    }

    public static NameSetter card(CardId cardId) {
        return new Builder(cardId);
    }

    public void registerBehaviours(EventBus eventBus) {
        behaviours.values().forEach(behaviour -> behaviour.registerWith(eventBus));
    }

    public void unregisterBehaviours(EventBus eventBus) {
        behaviours.values().forEach(behaviour -> behaviour.unregisterWith(eventBus));
    }

    public UUID getInstanceId() {
        return instanceId;
    }

    public CardId getCardId() {
        return cardId;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public Color getColor() {
        return color;
    }

    public void addBehaviour(Behaviour behaviour) {
        behaviours.put(behaviour.getClass(), behaviour);
        behaviour.forCard(this);
    }

    public boolean hasBehaviour(Class<? extends Behaviour> behaviourClass) {
        return behaviours.containsKey(behaviourClass);
    }

    public Collection<Behaviour> getBehaviours() {
        return Collections.unmodifiableCollection(behaviours.values());
    }

    public Behaviour getBehaviour(Class<? extends Behaviour> behaviourClass) {
        return behaviours.get(behaviourClass);
    }

    Card duplicate() {
        Card duplicate = Card.card(cardId).withName(name).withCost(cost).withColor(color).build();
        for (Behaviour behaviour : behaviours.values()) {
            duplicate.addBehaviour(behaviour.duplicate());
        }
        return duplicate;
    }

    @Override
    public String toString() {
        return String.format("%s%s (%d)%s", color, name, cost, Color.RESET);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        return this.instanceId.equals(((Card) o).instanceId);
    }

    @Override
    public int hashCode() {
        return instanceId.hashCode();
    }

    public interface NameSetter {
        CostSetter withName(String name);
    }

    public interface CostSetter {
        ColorSetter withCost(int cost);
    }

    public interface ColorSetter {
        Builder withColor(Color color);
    }

    public static class Builder implements NameSetter, CostSetter, ColorSetter {

        private final CardId cardId;
        private String name;
        private int cost;
        private Color color;

        private Builder(CardId cardId) {
            this.cardId = cardId;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCost(int cost) {
            this.cost = cost;
            return this;
        }

        public Builder withColor(Color color) {
            this.color = color;
            return this;
        }

        public Card build() {
            return new Card(this);
        }
    }
}
