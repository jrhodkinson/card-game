package jrh.game.card.store;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.CollationStrength;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import jrh.game.common.account.Account;
import jrh.game.common.account.AccountId;
import jrh.game.common.account.AccountWithHashedPassword;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountStore {

    private final MongoCollection<StoredAccount> collection;

    private static final Collation CASE_INSENSITIVE = Collation.builder().locale("en")
            .collationStrength(CollationStrength.PRIMARY).build();

    private static final String INDEX_EMAIL = "email";
    private static final String INDEX_NAME = "name";

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
        return Optional.ofNullable(collection.find(filter).collation(CASE_INSENSITIVE).first())
                .map(StoredAccount::getId).map(AccountId::fromUUID);
    }

    public Optional<AccountId> getAccountIdByEmail(String email) {
        Bson filter = Filters.eq("email", email);
        return Optional.ofNullable(collection.find(filter).collation(CASE_INSENSITIVE).first())
                .map(StoredAccount::getId).map(AccountId::fromUUID);
    }

    public Optional<AccountWithHashedPassword> getAccountWithHashedPassword(AccountId accountId) {
        Bson filter = Filters.eq("_id", accountId.toUUID());
        return Optional.ofNullable(collection.find(filter).first()).map(StoredAccountAdapter::account);
    }

    void initialise() {
        new IndexOptions().name("").collation(CASE_INSENSITIVE);
        List<String> indexes = new ArrayList<>();
        for (Document index : collection.listIndexes()) {
            indexes.add(index.getString("name"));
        }
        if (!indexes.contains(INDEX_EMAIL)) {
            collection.createIndex(Indexes.ascending("email"), indexOptions(INDEX_EMAIL));
        }
        if (!indexes.contains(INDEX_NAME)) {
            collection.createIndex(Indexes.ascending("name"), indexOptions(INDEX_NAME));
        }
    }

    private IndexOptions indexOptions(String name) {
        return new IndexOptions().background(true).name(name).collation(CASE_INSENSITIVE).unique(true);
    }
}
