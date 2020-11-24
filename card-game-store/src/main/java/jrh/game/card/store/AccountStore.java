package jrh.game.card.store;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jrh.game.common.account.Account;
import jrh.game.common.account.AccountId;
import org.bson.conversions.Bson;

import java.util.Optional;

public class AccountStore {

    private final MongoCollection<StoredAccount> collection;

    AccountStore(MongoCollection<StoredAccount> collection) {
        this.collection = collection;
    }

    public Optional<AccountId> getAccountId(String name) {
        Bson filter = Filters.eq("name", name);
        return Optional.ofNullable(collection.find(filter).first())
            .map(StoredAccount::getId)
            .map(AccountId::fromUUID);
    }

    public Optional<Account> getAccount(AccountId accountId) {
        Bson filter = Filters.eq("_id", accountId.toUUID());
        return Optional.ofNullable(collection.find(filter).first())
            .map(StoredAccountAdapter::account);
    }
}
