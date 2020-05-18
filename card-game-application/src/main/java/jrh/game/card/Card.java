package jrh.game.card;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jrh.game.card.behaviour.Behaviour;
import jrh.game.match.Match;
import jrh.game.match.Player;
import jrh.game.util.Color;

import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(using = CardDeserializer.class)
@JsonSerialize(using = CardSerializer.class)
public class Card {

    private final CardId id;
    private final String name;
    private final int cost;
    private final Color color;
    private final List<Behaviour> behaviours;

    private Card(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.cost = builder.cost;
        this.color = builder.color;
        this.behaviours = builder.behaviours;
    }

    public static NameSetter card(CardId cardId) {
        return new Builder(cardId);
    }

    public void play(Match match, Player player) {
        for (Behaviour behaviour : behaviours) {
            behaviour.play(match, player, this);
        }
    }

    public CardId getId() {
        return id;
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

    public List<Behaviour> getBehaviours() {
        return behaviours;
    }

    @Override
    public String toString() {
        return String.format("%s%s (%d)%s", color, name, cost, Color.RESET);
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

        private final CardId id;
        private String name;
        private int cost;
        private Color color;
        private final List<Behaviour> behaviours = new ArrayList<>();

        private Builder(CardId id) {
            this.id = id;
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

        public Builder withBehaviour(Behaviour behaviour) {
            this.behaviours.add(behaviour);
            return this;
        }

        public Card build() {
            return new Card(this);
        }
    }
}
