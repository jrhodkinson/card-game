package jrh.game.common.description;

import jrh.game.common.Target;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TargetDescriptionPiece implements DescriptionPiece {

    private final Logger logger = LoggerFactory.getLogger(TargetDescriptionPiece.class);

    private static final Map<Set<Target>, String> SPECIAL_CASES = new HashMap<>();
    static {
        SPECIAL_CASES.put(Set.of(Target.OWN_STRUCTURES, Target.OTHER_STRUCTURES), "all structures");
    }

    private final List<Target> targets;
    private final Set<Target> targetSet;

    public TargetDescriptionPiece(List<Target> targets) {
        this.targets = targets;
        this.targetSet = new HashSet<>(targets);
    }

    @Override
    public String get(DescriptionContext descriptionContext) {
        if (SPECIAL_CASES.containsKey(targetSet)) {
            return SPECIAL_CASES.get(targetSet);
        }

        if (targets.isEmpty()) {
            return "";
        }
        if (targets.size() == 1) {
            return targetString(targets.get(0));
        }
        if (targets.size() == 2) {
            return targetString(targets.get(0)) + " and " + targetString(targets.get(1));
        }
        StringBuilder description = new StringBuilder();
        for (int i = 0; i < targets.size() - 1; i++) {
            description.append(targetString(targets.get(i))).append(", ");
        }
        description.append("and ").append(targetString(targets.get(targets.size() - 1)));
        return description.toString();
    }

    private String targetString(Target target) {
        switch (target) {
        case SELF:
            return "yourself";
        case OTHER:
            return "your opponent";
        case OWN_STRUCTURES:
            return "your structures";
        case OTHER_STRUCTURES:
            return "your opponent's structures";
        default:
            logger.error("Unsupported target={}", target);
            return "~" + target.toString() + "~";
        }
    }
}
