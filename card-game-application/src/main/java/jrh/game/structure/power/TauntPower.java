package jrh.game.structure.power;

import jrh.game.asset.JsonKey;
import jrh.game.common.description.AtomicDescription;

@JsonKey("taunt")
public class TauntPower extends AbstractPower {

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription.builder().keyword("Taunt").build();
    }

    @Override
    public TauntPower duplicate() {
        return new TauntPower();
    }
}
