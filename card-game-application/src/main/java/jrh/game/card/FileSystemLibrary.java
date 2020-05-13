package jrh.game.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.util.ObjectMapperFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FileSystemLibrary implements Library {

    private static final Logger logger = LogManager.getLogger(FileSystemLibrary.class);

    private final Map<CardId, Card> cards;

    public FileSystemLibrary(File cardDirectory) {
        this.cards = loadCards(cardDirectory);
    }

    @Override
    public Card getCard(CardId cardId) {
        return cards.get(cardId);
    }

    @Override
    public List<Card> getAllCards() {
        return List.copyOf(cards.values());
    }

    private Map<CardId, Card> loadCards(File library) {
        ObjectMapper objectMapper = ObjectMapperFactory.create();
        Map<CardId, Card> cards = new HashMap<>();
        addCardsFromDirectory(library, cards, objectMapper);
        return cards;
    }

    private void addCardsFromDirectory(File directory, Map<CardId, Card> cardMap, ObjectMapper objectMapper) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isHidden()) {
                continue;
            }
            if (file.isDirectory()) {
                addCardsFromDirectory(file, cardMap, objectMapper);
            } else {
                try {
                    Card card = objectMapper.readValue(file, Card.class);
                    cardMap.put(card.getId(), card);
                } catch (IOException e) {
                    logger.error("Error reading card from file={}", file, e);
                }
            }
        }
    }
}
