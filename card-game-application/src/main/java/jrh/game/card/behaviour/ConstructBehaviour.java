package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.asset.JsonKey;
import jrh.game.card.event.CardPlayed;
import jrh.game.event.Subscribe;
import jrh.game.match.api.Match;
import jrh.game.structure.StructureId;
import jrh.game.structure.StructureStateController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonKey("construct")
public class ConstructBehaviour extends Behaviour {

    private static final Logger logger = LogManager.getLogger(ConstructBehaviour.class);

    @JsonValue
    private final StructureId structureId;

    @JsonCreator
    public ConstructBehaviour(StructureId structureId) {
        super(false);
        this.structureId = structureId;
    }

    @Override
    public BehaviourDescription getDescription() {
        return BehaviourDescription.builder()
                .keyword("Construct")
                .plainString(structureId.toString())
                .build();
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard())) {
            logger.info("Constructing structure={} for player={}", structureId, cardPlayed.getPlayer());
            match.getController(StructureStateController.class).construct(structureId, cardPlayed.getPlayer());
        }
    }

    @Override
    public ConstructBehaviour duplicate() {
        return new ConstructBehaviour(structureId);
    }
}
