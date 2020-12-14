package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.event.CardPlayed;
import jrh.game.asset.JsonKey;
import jrh.game.common.StructureId;
import jrh.game.common.description.AtomicDescription;
import jrh.game.common.description.Keyword;
import jrh.game.structure.StructureStateController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static jrh.game.card.behaviour.AbstractBehaviour.TargetType.NO_TARGET;

@JsonKey("construct")
public class ConstructBehaviour extends AbstractBehaviour {

    private static final Logger logger = LogManager.getLogger(ConstructBehaviour.class);

    @JsonValue
    private final StructureId structureId;

    @JsonCreator
    public ConstructBehaviour(StructureId structureId) {
        super(NO_TARGET);
        this.structureId = structureId;
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription.builder().keyword(Keyword.CONSTRUCT).structure(structureId).build();
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
