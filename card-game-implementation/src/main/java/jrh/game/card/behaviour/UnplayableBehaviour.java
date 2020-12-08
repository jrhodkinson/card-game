package jrh.game.card.behaviour;

import jrh.game.asset.JsonKey;
import jrh.game.common.description.AtomicDescription;
import jrh.game.common.description.Keyword;

@JsonKey("unplayable")
public class UnplayableBehaviour extends AbstractBehaviour {

    public UnplayableBehaviour() {
        super(false);
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
