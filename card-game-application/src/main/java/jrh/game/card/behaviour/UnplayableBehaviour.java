package jrh.game.card.behaviour;

public class UnplayableBehaviour extends Behaviour {

    public UnplayableBehaviour() {
        super(false);
    }

    @Override
    public UnplayableBehaviour duplicate() {
        return new UnplayableBehaviour();
    }
}
