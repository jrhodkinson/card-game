package jrh.game.structure.power;

import com.fasterxml.jackson.annotation.JsonProperty;
import jrh.game.asset.JsonKey;
import jrh.game.common.CardId;
import jrh.game.api.Match;
import jrh.game.api.Player;
import jrh.game.common.Target;
import jrh.game.common.description.AtomicDescription;
import jrh.game.structure.StructureStateController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static jrh.game.common.Target.OTHER;
import static jrh.game.common.Target.SELF;

@JsonKey("modifyCost")
public class ModifyCostPower extends AbstractPower {

    private static final Logger logger = LogManager.getLogger(ModifyCostPower.class);

    @JsonProperty
    private final List<Target> purchasers;

    @JsonProperty
    private final int amount;

    public ModifyCostPower(@JsonProperty("purchasers") List<Target> purchasers, @JsonProperty("amount") int amount) {
        this.purchasers = purchasers;
        this.amount = amount;
    }

    @Override
    public AtomicDescription getDescription() {
        String introduction = "Increase cost of cards purchased by ";
        if (amount < 0) {
            introduction = "Decrease cost of cards purchased by ";
        }
        return AtomicDescription.builder().plainString(introduction).targets(purchasers).plainString("by $")
                .number(Math.abs(amount)).build();
    }

    @Override
    public int modifyCost(Match match, Player purchaser, CardId cardId, int cost) {
        if (!shouldModifyCost(match, purchaser)) {
            return cost;
        }
        int modifiedCost = cost + amount;
        int finalCost = Math.max(modifiedCost, 0);
        logger.debug("Modifying initial cost {} by {}. Result: {}", cost, amount, finalCost);
        return finalCost;
    }

    private boolean shouldModifyCost(Match match, Player purchaser) {
        Player owner = match.getController(StructureStateController.class).getOwner(getStructure());
        return (owner.equals(purchaser) && purchasers.contains(SELF))
                || (!owner.equals(purchaser) && purchasers.contains(OTHER));
    }

    @Override
    public ModifyCostPower duplicate() {
        return new ModifyCostPower(List.copyOf(purchasers), amount);
    }
}
