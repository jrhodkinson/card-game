package jrh.game.api;

import jrh.game.common.CardId;
import jrh.game.common.EventHandler;
import jrh.game.common.description.AtomicDescription;

public interface Power extends EventHandler {

    Power duplicate();

    AtomicDescription getDescription();

    int modifyDamage(Match match, Player source, Damageable target, int damage);

    int modifyCost(Match match, Player purchaser, CardId cardId, int currentCost);
}
