package jrh.game.match;

import jrh.game.match.api.Damageable;
import jrh.game.match.api.Match;
import jrh.game.match.api.Player;
import jrh.game.structure.power.Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class HealthModificationComputer implements Controller {

    private static final Logger logger = LogManager.getLogger(HealthModificationComputer.class);

    private final Match match;

    public HealthModificationComputer(Match match) {
        this.match = match;
    }

    public int computeModifiedDamage(Player source, Damageable target, int initialAmount) {
        logger.debug("Computing modified damage for source={} target={} and initialAmount={}", source, target, initialAmount);
        List<Power> powers = match.getAllStructures().stream()
                .flatMap(structure -> structure.getPowers().stream())
                .collect(toList());
        int finalAmount = initialAmount;
        for (Power power : powers) {
            finalAmount = power.modifyDamage(match, source, target, finalAmount);
        }
        return finalAmount;
    }
}
