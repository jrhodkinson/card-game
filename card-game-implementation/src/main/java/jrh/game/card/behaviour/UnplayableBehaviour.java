package jrh.game.card.behaviour;

import jrh.game.asset.JsonKey;
import jrh.game.common.description.AtomicDescription;

@JsonKey("unplayable")
public class UnplayableBehaviour extends AbstractBehaviour {

    public UnplayableBehaviour() {
        super(false);
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription.keyword("Unplayable");
    }

    @Override
    public UnplayableBehaviour duplicate() {
        return new UnplayableBehaviour();
    }
}
