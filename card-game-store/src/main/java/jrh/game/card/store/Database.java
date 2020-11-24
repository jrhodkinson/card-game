package jrh.game.card.store;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromCodecs;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Database {

    private static final Database INSTANCE = new Database();
    private static final String DATABASE = "card-game";

    private final AccountStore accountStore;

    public static Database instance() {
        return INSTANCE;
    }

    private Database() {
        MongoDatabase database = database();
        this.accountStore = new AccountStore(database.getCollection(Collections.ACCOUNTS, StoredAccount.class));
    }

    public AccountStore accountStore() {
        return accountStore;
    }

    private MongoDatabase database() {
        CodecRegistry pojoCodecRegistry = fromRegistries(fromCodecs(new UuidCodec(UuidRepresentation.STANDARD)),
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientSettings settings = MongoClientSettings.builder()
            .codecRegistry(pojoCodecRegistry)
            .build();
        return MongoClients.create(settings).getDatabase(DATABASE);
    }
}
