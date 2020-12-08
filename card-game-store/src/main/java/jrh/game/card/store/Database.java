package jrh.game.card.store;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static java.util.Collections.singletonList;
import static org.bson.codecs.configuration.CodecRegistries.fromCodecs;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Database {

    private static final String DATABASE = "card-game";

    private final AccountStore accountStore;

    public static Database instance(String host, int port) {
        return new Database(host, port);
    }

    private Database(String host, int port) {
        MongoDatabase database = database(host, port);
        this.accountStore = new AccountStore(database.getCollection(Collections.ACCOUNTS, StoredAccount.class));
        accountStore.initialise();
    }

    public AccountStore accountStore() {
        return accountStore;
    }

    private MongoDatabase database(String host, int port) {
        ServerAddress serverAddress = new ServerAddress(host, port);
        MongoClientSettings settings = mongoClientSettings(serverAddress);
        return MongoClients.create(settings).getDatabase(DATABASE);
    }

    private MongoClientSettings mongoClientSettings(ServerAddress serverAddress) {
        CodecRegistry pojoCodecRegistry = fromRegistries(fromCodecs(new UuidCodec(UuidRepresentation.STANDARD)),
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        return MongoClientSettings.builder().applyToClusterSettings(b -> b.hosts(singletonList(serverAddress)))
                .codecRegistry(pojoCodecRegistry).build();
    }
}
