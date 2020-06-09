package jrh.game.card.behaviour;

import jrh.game.asset.JsonKey;

@JsonKey("unplayable")
public class UnplayableBehaviour extends Behaviour {

    public UnplayableBehaviour() {
        super(false);
    }

    @Override
    public BehaviourDescription getDescription() {
        return BehaviourDescription.keyword("Unplayable");
    }

    @Override
    public UnplayableBehaviour duplicate() {
        return new UnplayableBehaviour();
    }
}
