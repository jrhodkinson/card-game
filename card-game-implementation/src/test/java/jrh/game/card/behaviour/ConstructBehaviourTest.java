package jrh.game.card.behaviour;

import jrh.game.api.Card;
import jrh.game.api.Player;
import jrh.game.api.action.PlayCard;
import jrh.game.asset.AssetLibrary;
import jrh.game.asset.TestAssetLibrary;
import jrh.game.card.CardImpl;
import jrh.game.card.TestCard;
import jrh.game.common.StructureId;
import jrh.game.common.User;
import jrh.game.match.MutableMatch;
import jrh.game.structure.MutableStructure;
import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstructBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new ConstructBehaviour(new StructureId("structure-id")));
    }

    @Test
    public void constructsStructureOnPlay() {
        StructureId structureId = new StructureId(randomAlphabetic(10));
        ConstructBehaviour constructBehaviour = new ConstructBehaviour(structureId);
        CardImpl testCard = TestCard.forBehaviour(constructBehaviour);
        MutableStructure structure = new MutableStructure(structureId, randomAlphabetic(10), 4);
        AssetLibrary assetLibrary = TestAssetLibrary.of(testCard, structure);

        User firstUser = new User("a");
        User secondUser = new User("b");
        MutableMatch match = new MutableMatch(assetLibrary, firstUser, secondUser);
        match.start();

        Player activePlayer = match.getActivePlayer();
        Card constructCard = activePlayer.getHand().get(0);
        assertThat(constructCard.getCardId(), equalTo(testCard.getCardId()));

        int initialStructures = match.getActivePlayer().getStructures().size();

        PlayCard playCard = new PlayCard(activePlayer.getUser(), constructCard.getEntityId(), null);
        match.getActionHandler().accept(playCard);

        assertThat(match.getActivePlayer().getStructures().size(), equalTo(initialStructures + 1));
        assertTrue(
                match.getActivePlayer().getStructures().stream().anyMatch(s -> s.getStructureId().equals(structureId)));
    }

}
