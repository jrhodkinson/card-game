package jrh.game.structure.power;

import com.fasterxml.jackson.annotation.JsonProperty;
import jrh.game.api.Match;
import jrh.game.api.Player;
import jrh.game.api.Subscribe;
import jrh.game.api.event.CardPurchased;
import jrh.game.asset.JsonKey;
import jrh.game.common.Target;
import jrh.game.match.HealthController;
import jrh.game.structure.StructureStateController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static jrh.game.common.Target.OTHER;
import static jrh.game.common.Target.SELF;

@JsonKey("damageOnPurchase")
public class DamageOnPurchasePower extends AbstractPower {

    private static final Logger logger = LogManager.getLogger(DamageOnPurchasePower.class);

    @JsonProperty
    private final List<Target> purchasers;

    @JsonProperty
    private final int amount;

    public DamageOnPurchasePower(@JsonProperty("purchasers") List<Target> purchasers,
            @JsonProperty("amount") int amount) {
        this.purchasers = purchasers;
        this.amount = amount;
    }

    @Subscribe
    private void cardPurchased(CardPurchased cardPurchased, Match match) {
        Player purchaser = cardPurchased.getPurchaser();
        if (!shouldDamage(match, cardPurchased.getPurchaser())) {
            return;
        }
        logger.info("Damaging purchaser={} by amount={}", purchaser, amount);
        match.getController(HealthController.class).damage(getOwner(match), purchaser, amount);
    }

    private boolean shouldDamage(Match match, Player purchaser) {
        Player owner = match.getController(StructureStateController.class).getOwner(getStructure());
        return (owner.equals(purchaser) && purchasers.contains(SELF))
                || (!owner.equals(purchaser) && purchasers.contains(OTHER));
    }

    @Override
    public DamageOnPurchasePower duplicate() {
        return new DamageOnPurchasePower(List.copyOf(purchasers), amount);
    }
}
