package jrh.game.card.store.history;

import com.mongodb.client.MongoCollection;
import jrh.game.common.history.HistoricMatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HistoricMatchStore {

    private static final Logger logger = LogManager.getLogger(HistoricMatchStore.class);

    private final MongoCollection<StoredHistoricMatch> collection;

    public HistoricMatchStore(MongoCollection<StoredHistoricMatch> collection) {
        this.collection = collection;
    }

    public void putHistoricMatch(HistoricMatch historicMatch) {
        logger.info("Storing historicMatch={}", historicMatch);
        collection.insertOne(StoredHistoricMatchAdapter.storedHistoricMatch(historicMatch));
    }
}
