package jrh.game.card.behaviour;

import jrh.game.asset.JsonKey;
import jrh.game.common.BehaviourDescription;

@JsonKey("unplayable")
public class UnplayableBehaviour extends AbstractBehaviour {

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
