package jrh.game.card;

import jrh.game.card.behaviour.Behaviour;
import jrh.game.match.Match;
import jrh.game.match.Target;
import jrh.game.util.Color;

import java.util.ArrayList;
import java.util.List;

public class Card {

    private final String name;
    private final int cost;
    private final Color color;
    private final List<Behaviour> behaviours;

    private Card(Builder builder) {
        this.name = builder.name;
        this.cost = builder.cost;
        this.color = builder.color;
        this.behaviours = builder.behaviours;
    }

    public static CostSetter card(String name) {
        return new Builder(name);
    }

    public void play(Match match, Target target) {
        for (Behaviour behaviour : behaviours) {
            behaviour.play(match, target);
        }
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return String.format("%s%s (%d)%s", color, name, cost, Color.RESET);
    }

    public interface CostSetter {
        ColorSetter withCost(int cost);
    }

    public interface ColorSetter {
        Builder withColor(Color color);
    }

    public static class Builder implements CostSetter, ColorSetter {

        private final String name;
        private int cost;
        private Color color;
        private final List<Behaviour> behaviours = new ArrayList<>();

        private Builder(String name) {
            this.name = name;
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
