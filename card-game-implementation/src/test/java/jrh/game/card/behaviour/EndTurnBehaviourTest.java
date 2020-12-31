package jrh.game.card.behaviour;

import jrh.game.api.Card;
import jrh.game.api.Player;
import jrh.game.api.action.PlayCard;
import jrh.game.asset.ConcreteAssetLibrary;
import jrh.game.asset.TestAssetLibrary;
import jrh.game.card.CardImpl;
import jrh.game.card.TestCard;
import jrh.game.common.User;
import jrh.game.match.MutableMatch;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class EndTurnBehaviourTest {

    @Test
    public void standardTests() {
        TestBehaviour.passesAllStandardTests(new EndTurnBehaviour());
    }

    @Test
    public void endsCurrentTurn() {
        EndTurnBehaviour behaviour = new EndTurnBehaviour();

        CardImpl testCard = TestCard.forBehaviour(behaviour);
        ConcreteAssetLibrary assetLibrary = TestAssetLibrary.of(testCard);
        User firstUser = new User("a");
        User secondUser = new User("b");
        MutableMatch match = new MutableMatch(assetLibrary, firstUser, secondUser);
        match.start();

        Player activePlayer = match.getActivePlayer();
        Card card = activePlayer.getHand().get(0);
        assertThat(card.getCardId(), equalTo(testCard.getCardId()));

        PlayCard playCard = new PlayCard(activePlayer.getUser(), card.getEntityId(), null);
        match.getActionHandler().accept(playCard);

        assertThat(match.getInactivePlayer(), equalTo(activePlayer));
        assertThat(match.getActivePlayer().getUser(),
                equalTo(activePlayer.getUser().equals(firstUser) ? secondUser : firstUser));
    }

}
