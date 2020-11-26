package jrh.game.service.lobby;

import com.google.common.collect.ImmutableSet;
import jrh.game.api.ActionHandler;
import jrh.game.api.EventBus;
import jrh.game.api.Match;
import jrh.game.common.account.AccountId;
import jrh.game.match.MutableMatch;

import java.util.Set;
import java.util.UUID;

public class ActiveMatch {

    private final Match match;
    private final EventBus eventBus;
    private final Set<AccountId> involvedAccountIds;

    private ActiveMatch(Match match, EventBus eventBus, Set<AccountId> involvedAccountIds) {
        this.match = match;
        this.eventBus = eventBus;
        this.involvedAccountIds = involvedAccountIds;
    }

    public static ActiveMatch from(MutableMatch mutableMatch, AccountId... involvedAccounts) {
        if (involvedAccounts.length < 2) {
            throw new IllegalArgumentException("At least 2 accounts must be involved in a match");
        }
        return new ActiveMatch(mutableMatch, mutableMatch.getEventBus(), ImmutableSet.copyOf(involvedAccounts));
    }

    public UUID getId() {
        return match.getId();
    }

    public ActionHandler getActionHandler() {
        return match.getActionHandler();
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public Set<AccountId> getInvolvedAccountIds() {
        return involvedAccountIds;
    }
}
