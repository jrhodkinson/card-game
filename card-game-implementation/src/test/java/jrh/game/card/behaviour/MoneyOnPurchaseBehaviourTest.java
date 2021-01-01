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

public class MoneyOnPurchaseBehaviourTest {

    @Test
    public void standardTests() {
        TestBehaviour.passesAllStandardTests(() -> new MoneyOnPurchaseBehaviour(nextInt()));
    }

    @Test
    public void doesGiveMoneyOnPurchase() {
        MoneyOnPurchaseBehaviour moneyOnPurchaseBehaviour = new MoneyOnPurchaseBehaviour(2);

        CardImpl testCard = TestCard.forBehaviour(moneyOnPurchaseBehaviour);
        ConcreteAssetLibrary assetLibrary = TestAssetLibrary.of(testCard);
        User firstUser = new User("a");
        User secondUser = new User("b");
        MutableMatch match = new MutableMatch(assetLibrary, firstUser, secondUser);
        match.start();

        Player activePlayer = match.getActivePlayer();
        Card moneyOnPurchaseCard = match.getStore().getRow().get(0);
        assertThat(moneyOnPurchaseCard.getCardId(), equalTo(testCard.getCardId()));

        int initialMoney = match.getCurrentTurn().getMoney();

        BuyCard buyCard = new BuyCard(activePlayer.getUser(), moneyOnPurchaseCard.getEntityId());
        match.getActionHandler().accept(buyCard);

        assertThat(match.getCurrentTurn().getMoney(), equalTo(initialMoney + 2));
    }
}
