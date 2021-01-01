package jrh.game.card.store.history;

import com.mongodb.client.MongoCollection;
import jrh.game.common.history.HistoricMatch;

public class HistoricMatchStore {

    private final MongoCollection<StoredHistoricMatch> collection;

    public HistoricMatchStore(MongoCollection<StoredHistoricMatch> collection) {
        this.collection = collection;
    }

    public void putHistoricMatch(HistoricMatch historicMatch) {
        collection.insertOne(StoredHistoricMatchAdapter.storedHistoricMatch(historicMatch));
    }
}
