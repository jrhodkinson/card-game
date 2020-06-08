package jrh.game.structure.power;

import com.fasterxml.jackson.annotation.JsonProperty;
import jrh.game.asset.JsonKey;
import jrh.game.card.CardId;
import jrh.game.match.api.Match;
import jrh.game.match.api.Player;
import jrh.game.match.api.Target;
import jrh.game.structure.StructureStateController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static jrh.game.match.api.Target.OTHER;
import static jrh.game.match.api.Target.SELF;

@JsonKey("modify-cost")
public class ModifyCostPower extends Power {

    private static final Logger logger = LogManager.getLogger(ModifyCostPower.class);

    @JsonProperty
    private final List<Target> targets;

    @JsonProperty
    private final int amount;

    public ModifyCostPower(@JsonProperty("targets") List<Target> targets, @JsonProperty("amount") int amount) {
        this.targets = targets;
        this.amount = amount;
    }

    @Override
    public int modifyCost(Match match, Player purchaser, CardId cardId, int cost) {
        if (!shouldModifyCost(match, purchaser)){
            return cost;
        }
        int modifiedCost = cost + amount;
        int finalCost = Math.max(modifiedCost, 0);
        logger.debug("Modifying initial cost {} by {}. Result: {}", cost, amount, finalCost);
        return finalCost;
    }

    private boolean shouldModifyCost(Match match, Player purchaser) {
        Player owner = match.getController(StructureStateController.class).getOwner(getStructure());
        return (owner.equals(purchaser) && targets.contains(SELF)) || (!owner.equals(purchaser) && targets.contains(OTHER));
    }

    @Override
    public ModifyCostPower duplicate() {
        return new ModifyCostPower(List.copyOf(targets), amount);
    }
}
