package jrh.game.card;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import jrh.game.api.Behaviour;
import jrh.game.api.Card;
import jrh.game.asset.CardImplDeserializer;
import jrh.game.asset.CardImplSerializer;
import jrh.game.card.behaviour.AbstractBehaviour;
import jrh.game.common.CardId;
import jrh.game.common.Color;
import jrh.game.common.EntityId;
import jrh.game.common.description.Description;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@JsonDeserialize(using = CardImplDeserializer.class)
@JsonSerialize(using = CardImplSerializer.class)
public class CardImpl implements Card {

    private final EntityId entityId = EntityId.randomEntityId();
    private final CardId cardId;
    private final String name;
    private final int cost;
    private final Color color;
    private final String flavorText;
    private final ListMultimap<Class<? extends Behaviour>, AbstractBehaviour> behaviours;

    private CardImpl(Builder builder) {
        this.cardId = builder.cardId;
        this.name = builder.name;
        this.cost = builder.cost;
        this.color = builder.color;
        this.behaviours = builder.behaviours;
        this.flavorText = builder.flavorText;
        this.behaviours.values().forEach(behaviour -> behaviour.forCard(this));
    }

    public static NameSetter card(CardId cardId) {
        return new Builder(cardId);
    }

    @Override
    public EntityId getEntityId() {
        return entityId;
    }

    @Override
    public CardId getCardId() {
        return cardId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getFlavorText() {
        return Optional.ofNullable(flavorText);
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Description getDescription() {
        return Description.of(getAllBehaviours().stream().map(Behaviour::getDescription).collect(toList()));
    }

    @Override
    public boolean hasBehaviour(Class<? extends Behaviour> behaviourClass) {
        return behaviours.containsKey(behaviourClass);
    }

    @Override
    public Collection<Behaviour> getAllBehaviours() {
        return Collections.unmodifiableCollection(behaviours.values());
    }

    @Override
    public List<Behaviour> getBehaviours(Class<? extends Behaviour> behaviourClass) {
        return behaviours.get(behaviourClass).stream().map(ab -> (Behaviour) ab).collect(toList());
    }

    @Override
    public boolean requiresTarget() {
        return behaviours.values().stream().anyMatch(AbstractBehaviour::requiresTarget);
    }

    CardImpl duplicate() {
        CardImpl.Builder duplicateBuilder = CardImpl.card(cardId).withName(name).withCost(cost).withColor(color)
                .withFlavorText(flavorText);
        behaviours.values().forEach(behaviour -> duplicateBuilder.withBehaviour(behaviour.duplicate()));
        return duplicateBuilder.build();
    }

    @Override
    public String toString() {
        return String.format("%s%s%s", color, name, Color.RESET);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        return this.entityId.equals(((CardImpl) o).entityId);
    }

    @Override
    public int hashCode() {
        return entityId.hashCode();
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
        private String flavorText;
        private final ListMultimap<Class<? extends Behaviour>, AbstractBehaviour> behaviours = LinkedListMultimap
                .create();

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

        public Builder withFlavorText(String flavorText) {
            this.flavorText = flavorText;
            return this;
        }

        public Builder withBehaviour(AbstractBehaviour behaviour) {
            behaviours.put(behaviour.getClass(), behaviour);
            return this;
        }

        public CardImpl build() {
            return new CardImpl(this);
        }
    }
}
