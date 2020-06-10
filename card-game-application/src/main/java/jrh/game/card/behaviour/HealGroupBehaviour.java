package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.asset.JsonKey;
import jrh.game.api.event.CardPlayed;
import jrh.game.common.BehaviourDescription;
import jrh.game.event.Subscribe;
import jrh.game.match.HealthController;
import jrh.game.api.Damageable;
import jrh.game.api.Match;
import jrh.game.api.Player;
import jrh.game.common.Target;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@JsonKey("heal-group")
public class HealGroupBehaviour extends AbstractBehaviour {

    private static final Logger logger = LogManager.getLogger(HealGroupBehaviour.class);

    @JsonValue
    private final List<Target> targets;

    @JsonValue
    private final int amount;

    public HealGroupBehaviour(@JsonProperty("targets") List<Target> targets, @JsonProperty("amount") int amount) {
        super(false);
        this.targets = targets;
        this.amount = amount;
    }

    @Override
    public BehaviourDescription getDescription() {
        return BehaviourDescription.builder().keyword("Heal").plainString(targets.toString()).number(amount).build();
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard())) {
            List<Damageable> realTargets = computeRealTargets(match, cardPlayed.getPlayer());
            logger.info("Healing targets={} by amount={}", realTargets, amount);
            realTargets.forEach((target) -> match.getController(HealthController.class).heal(cardPlayed.getPlayer(),
                    target, this.amount));
        }
    }

    private List<Damageable> computeRealTargets(Match match, Player player) {
        List<Damageable> realTargets = new ArrayList<>();
        if (targets.contains(Target.SELF)) {
            realTargets.add(player);
        }
        if (targets.contains(Target.OTHER)) {
            realTargets.add(match.getOtherPlayer(player));
        }
        if (targets.contains(Target.OWN_STRUCTURES)) {
            realTargets.addAll(player.getStructures());
        }
        if (targets.contains(Target.OTHER_STRUCTURES)) {
            realTargets.addAll(match.getOtherPlayer(player).getStructures());
        }
        return realTargets;
    }

    @Override
    public HealGroupBehaviour duplicate() {
        return new HealGroupBehaviour(List.copyOf(targets), amount);
    }
}
