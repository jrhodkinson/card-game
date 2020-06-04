package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.card.event.CardPlayed;
import jrh.game.event.Subscribe;
import jrh.game.match.HealthController;
import jrh.game.match.api.Damageable;
import jrh.game.match.api.Match;
import jrh.game.match.api.Player;
import jrh.game.match.api.Target;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DamageGroupBehaviour extends Behaviour {

    private static final Logger logger = LogManager.getLogger(DamageGroupBehaviour.class);

    @JsonValue
    private final List<Target> targets;

    @JsonValue
    private final int damage;

    public DamageGroupBehaviour(@JsonProperty("targets") List<Target> targets, @JsonProperty("damage") int damage) {
        super(false);
        this.targets = targets;
        this.damage = damage;
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard())) {
            List<Damageable> realTargets = computeRealTargets(match, cardPlayed.getPlayer());
            logger.info("Damaging targets={} by amount={}", realTargets, damage);
            realTargets.forEach((target) -> match.getController(HealthController.class).damage(cardPlayed.getPlayer(), target, this.damage));
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
    public DamageGroupBehaviour duplicate() {
        return null;
    }
}
