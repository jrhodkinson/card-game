package jrh.game.card.behaviour;

import jrh.game.api.Card;
import jrh.game.api.Player;
import jrh.game.api.action.PlayCard;
import jrh.game.asset.AssetLibrary;
import jrh.game.asset.TestAssetLibrary;
import jrh.game.card.CardImpl;
import jrh.game.card.TestCard;
import jrh.game.common.User;
import jrh.game.match.MutableMatch;
import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DamageBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new DamageBehaviour(nextInt()));
    }

    @Test
    public void damagesOtherPlayer() {
        DamageBehaviour damageBehaviour = new DamageBehaviour(1);

        CardImpl testCard = TestCard.forBehaviour(damageBehaviour);
        AssetLibrary assetLibrary = TestAssetLibrary.of(testCard);
        User firstUser = new User("a");
        User secondUser = new User("b");
        MutableMatch match = new MutableMatch(assetLibrary, firstUser, secondUser);
        match.start();

        Player activePlayer = match.getActivePlayer();
        Card damageCard = activePlayer.getHand().get(0);
        assertThat(damageCard.getCardId(), equalTo(testCard.getCardId()));

        int initialHealth = match.getInactivePlayer().getHealth();

        PlayCard playCard = new PlayCard(activePlayer.getUser(), damageCard.getEntityId(), match.getInactivePlayer().getEntityId());
        match.getActionHandler().accept(playCard);

        assertThat(match.getInactivePlayer().getHealth(), equalTo(initialHealth - 1));
    }
}
