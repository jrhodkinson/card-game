package jrh.game.card.behaviour;

import jrh.game.asset.JsonKey;
import jrh.game.common.description.AtomicDescription;
import jrh.game.common.description.Keyword;

import static jrh.game.card.behaviour.AbstractBehaviour.TargetType.NO_TARGET;

@JsonKey("unplayable")
public class UnplayableBehaviour extends AbstractBehaviour {

    public UnplayableBehaviour() {
        super(NO_TARGET);
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription.keyword(Keyword.UNPLAYABLE);
    }

    @Override
    public UnplayableBehaviour duplicate() {
        return new UnplayableBehaviour();
    }
}
