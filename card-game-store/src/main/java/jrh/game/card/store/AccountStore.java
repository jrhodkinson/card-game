package jrh.game.card.store;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jrh.game.common.account.Account;
import jrh.game.common.account.AccountId;
import jrh.game.common.account.AccountWithHashedPassword;
import org.bson.conversions.Bson;

import java.util.Optional;

public class AccountStore {

    private final MongoCollection<StoredAccount> collection;

    AccountStore(MongoCollection<StoredAccount> collection) {
        this.collection = collection;
    }

    public Optional<Account> getAccount(AccountId accountId) {
        return getAccountWithHashedPassword(accountId).map(AccountWithHashedPassword::getAccount);
    }

    public void putAccount(AccountWithHashedPassword account) {
        collection.insertOne(StoredAccountAdapter.storedAccount(account));
    }

    public Optional<AccountId> getAccountIdByName(String name) {
        Bson filter = Filters.eq("name", name);
        return Optional.ofNullable(collection.find(filter).first())
            .map(StoredAccount::getId)
            .map(AccountId::fromUUID);
    }

    public Optional<AccountId> getAccountIdByEmail(String email) {
        Bson filter = Filters.eq("email", email);
        return Optional.ofNullable(collection.find(filter).first())
            .map(StoredAccount::getId)
            .map(AccountId::fromUUID);
    }

    public Optional<AccountWithHashedPassword> getAccountWithHashedPassword(AccountId accountId) {
        Bson filter = Filters.eq("_id", accountId.toUUID());
        return Optional.ofNullable(collection.find(filter).first())
            .map(StoredAccountAdapter::account);
    }
}
