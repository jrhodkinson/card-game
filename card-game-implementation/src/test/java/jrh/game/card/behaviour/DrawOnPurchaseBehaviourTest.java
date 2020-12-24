package jrh.game.card.behaviour;

import jrh.game.api.Card;
import jrh.game.api.Player;
import jrh.game.api.action.BuyCard;
import jrh.game.asset.ConcreteAssetLibrary;
import jrh.game.asset.TestAssetLibrary;
import jrh.game.card.CardImpl;
import jrh.game.card.TestCard;
import jrh.game.common.User;
import jrh.game.match.MutableMatch;
import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DrawOnPurchaseBehaviourTest {

    @Test
    public void standardTests() {
        TestBehaviour.passesAllTests(new DrawOnPurchaseBehaviour(nextInt()));
    }

    @Test
    public void doesDrawOnPurchase() {
        DrawOnPurchaseBehaviour drawOnPurchaseBehaviour = new DrawOnPurchaseBehaviour(2);

        CardImpl testCard = TestCard.forBehaviour(drawOnPurchaseBehaviour);
        ConcreteAssetLibrary assetLibrary = TestAssetLibrary.of(testCard);
        User firstUser = new User("a");
        User secondUser = new User("b");
        MutableMatch match = new MutableMatch(assetLibrary, firstUser, secondUser);
        match.start();

        Player activePlayer = match.getActivePlayer();
        Card drawOnPurchaseCard = match.getStore().getRow().get(0);
        assertThat(drawOnPurchaseCard.getCardId(), equalTo(testCard.getCardId()));

        int initialHandSize = match.getActivePlayer().getHand().size();

        BuyCard buyCard = new BuyCard(activePlayer.getUser(), drawOnPurchaseCard.getEntityId());
        match.getActionHandler().accept(buyCard);

        assertThat(match.getActivePlayer().getHand().size(), equalTo(initialHandSize + 2));
    }
}
