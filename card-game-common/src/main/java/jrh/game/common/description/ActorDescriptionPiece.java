package jrh.game.common.description;

import jrh.game.common.Target;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ActorDescriptionPiece implements DescriptionPiece {

    private final Logger logger = LoggerFactory.getLogger(TargetDescriptionPiece.class);

    private final List<Target> actors;

    public ActorDescriptionPiece(List<Target> actors) {
        this.actors = actors;
    }

    @Override
    public String get(DescriptionContext descriptionContext) {
        if (actors.isEmpty()) {
            return "";
        }
        if (actors.size() == 1) {
            return actorString(actors.get(0));
        }
        if (actors.size() == 2) {
            return actorString(actors.get(0)) + " and " + actorString(actors.get(1));
        }
        StringBuilder description = new StringBuilder();
        for (int i = 0; i < actors.size() - 1; i++) {
            description.append(actorString(actors.get(i))).append(", ");
        }
        description.append("and ").append(actorString(actors.get(actors.size() - 1)));
        return description.toString();
    }

    private String actorString(Target target) {
        switch (target) {
        case SELF:
            return "you";
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
