package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonProperty;
import jrh.game.api.Damageable;
import jrh.game.api.Match;
import jrh.game.api.Player;
import jrh.game.api.Subscribe;
import jrh.game.api.event.CardPurchased;
import jrh.game.asset.JsonKey;
import jrh.game.common.description.AtomicDescription;
import jrh.game.common.Target;
import jrh.game.common.description.Keyword;
import jrh.game.match.HealthController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@JsonKey("damageOnPurchase")
public class DamageOnPurchaseBehaviour extends AbstractBehaviour {

    private static final Logger logger = LogManager.getLogger(DamageBehaviour.class);

    @JsonProperty
    private final List<Target> targets;

    @JsonProperty
    private final int amount;

    public DamageOnPurchaseBehaviour(@JsonProperty("targets") List<Target> targets,
            @JsonProperty("amount") int amount) {
        super(false);
        this.targets = targets;
        this.amount = amount;
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription.builder().plainString("On purchase,").keyword(Keyword.DAMAGE).targets(targets).number(amount)
                .build();
    }

    @Subscribe
    private void cardPurchased(CardPurchased cardPurchased, Match match) {
        if (cardPurchased.getCard().equals(this.getCard())) {
            List<Damageable> realTargets = computeRealTargets(match, cardPurchased.getPurchaser());
            logger.info("Damaging targets={} by amount={}", realTargets, amount);
            realTargets.forEach((target) -> match.getController(HealthController.class)
                    .damage(cardPurchased.getPurchaser(), target, this.amount));
        }
    }

    private List<Damageable> computeRealTargets(Match match, Player player) {
        List<Damageable> realTargets = new ArrayList<>();
        if (targets.contains(Target.SELF)) {
            realTargets.add(player);
        }
        if (targets.contains(Target.OTHER)) {
            realTargets.add(match.getOtherPlayer(player.getUser()));
        }
        if (targets.contains(Target.OWN_STRUCTURES)) {
            realTargets.addAll(player.getStructures());
        }
        if (targets.contains(Target.OTHER_STRUCTURES)) {
            realTargets.addAll(match.getOtherPlayer(player.getUser()).getStructures());
        }
        return realTargets;
    }

    @Override
    public DamageOnPurchaseBehaviour duplicate() {
        return new DamageOnPurchaseBehaviour(List.copyOf(targets), amount);
    }
}
