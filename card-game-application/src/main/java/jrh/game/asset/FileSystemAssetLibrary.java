package jrh.game.asset;

import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.card.Card;
import jrh.game.card.CardId;
import jrh.game.util.ObjectMapperFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FileSystemAssetLibrary implements AssetLibrary {

    private static final Logger logger = LogManager.getLogger(FileSystemAssetLibrary.class);

    private final Map<CardId, Card> cards = new HashMap<>();
    private final Map<CardId, Card> debugCards = new HashMap<>();

    public FileSystemAssetLibrary(File cardDirectory) {
        loadCards(cardDirectory);
    }

    @Override
    public Card getCard(CardId cardId) {
        return cards.get(cardId);
    }

    @Override
    public Card getDebugCard(CardId cardId) {
        return debugCards.get(cardId);
    }

    @Override
    public List<Card> getAllCards() {
        return List.copyOf(cards.values());
    }

    private void loadCards(File library) {
        ObjectMapper objectMapper = ObjectMapperFactory.create();
        addCardsFromDirectory(library, objectMapper);
    }

    private void addCardsFromDirectory(File directory, ObjectMapper objectMapper) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isHidden()) {
                continue;
            }
            if (file.isDirectory()) {
                addCardsFromDirectory(file, objectMapper);
            } else {
                try {
                    Card card = objectMapper.readValue(file, Card.class);
                    if (card.getCardId().isDebugId()) {
                        debugCards.put(card.getCardId(), card);
                    } else {
                        cards.put(card.getCardId(), card);
                    }
                } catch (IOException e) {
                    logger.error("Error reading card from file={}", file, e);
                }
            }
        }
    }
}
