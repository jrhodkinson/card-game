package jrh.game.match;

import jrh.game.card.Card;
import jrh.game.match.api.Damageable;
import jrh.game.match.api.Match;
import jrh.game.match.api.Player;
import jrh.game.structure.power.Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ModificationComputer implements Controller {

    private static final Logger logger = LogManager.getLogger(ModificationComputer.class);

    private final Match match;

    public ModificationComputer(Match match) {
        this.match = match;
    }

    public int computeModifiedDamage(Player source, Damageable target, int initialAmount) {
        logger.debug("Computing modified damage for source={} target={} and initialAmount={}", source, target,
                initialAmount);
        int finalAmount = initialAmount;
        for (Power power : powers()) {
            finalAmount = power.modifyDamage(match, source, target, finalAmount);
        }
        return finalAmount;
    }

    public int computeModifiedCost(Player purchaser, Card card) {
        logger.debug("Computing modified cost for purchaser={} and card={}", purchaser, card);
        int finalCost = card.getCost();
        for (Power power : powers()) {
            finalCost = power.modifyCost(match, purchaser, card.getCardId(), finalCost);
        }
        return finalCost;
    }

    private List<Power> powers() {
        return match.getAllStructures().stream().flatMap(structure -> structure.getPowers().stream()).collect(toList());
    }
}
