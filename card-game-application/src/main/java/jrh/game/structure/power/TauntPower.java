package jrh.game.structure.power;

import jrh.game.asset.JsonKey;

@JsonKey("taunt")
public class TauntPower extends AbstractPower {

    @Override
    public TauntPower duplicate() {
        return new TauntPower();
    }
}
