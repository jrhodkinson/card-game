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

public class MoneyBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new MoneyBehaviour(nextInt()));
    }

    @Test
    public void addsMoneyToCurrentTurn() {
        MoneyBehaviour moneyBehaviour = new MoneyBehaviour(3);

        CardImpl testCard = TestCard.forBehaviour(moneyBehaviour);
        AssetLibrary assetLibrary = TestAssetLibrary.of(testCard);
        User firstUser = new User("a");
        User secondUser = new User("b");
        MutableMatch match = new MutableMatch(assetLibrary, firstUser, secondUser);
        match.start();

        Player activePlayer = match.getActivePlayer();
        Card moneyCard = activePlayer.getHand().get(0);
        assertThat(moneyCard.getCardId(), equalTo(testCard.getCardId()));

        assertThat(match.getCurrentTurn().getMoney(), equalTo(0));
        PlayCard playCard = new PlayCard(activePlayer.getUser(), moneyCard.getEntityId(), null);
        match.getActionHandler().accept(playCard);

        assertThat(match.getCurrentTurn().getMoney(), equalTo(3));
    }
}
