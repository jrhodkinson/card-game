package jrh.game.asset;

import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.card.Card;
import jrh.game.card.CardId;
import jrh.game.structure.MutableStructure;
import jrh.game.structure.StructureId;
import jrh.game.util.ObjectMapperFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FileSystemAssetLibrary implements AssetLibrary {

    private static final Logger logger = LogManager.getLogger(FileSystemAssetLibrary.class);

    private final Map<CardId, Card> cards = new HashMap<>();
    private final Map<StructureId, MutableStructure> structures = new HashMap<>();

    public FileSystemAssetLibrary(Path assetsDirectory) {
        loadAssets(assetsDirectory);
    }

    @Override
    public Card getCard(CardId cardId) {
        return cards.get(cardId);
    }

    @Override
    public List<Card> getAllCards() {
        return List.copyOf(cards.values());
    }

    @Override
    public MutableStructure getStructure(StructureId structureId) {
        return structures.get(structureId);
    }

    @Override
    public List<MutableStructure> getAllStructures() {
        return List.copyOf(structures.values());
    }

    private void loadAssets(Path assetsDirectory) {
        ObjectMapper objectMapper = ObjectMapperFactory.create();
        addCardsFromDirectory(assetsDirectory.resolve("cards").toFile(), objectMapper);
        addStructuresFromDirectory(assetsDirectory.resolve("structures").toFile(), objectMapper);
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
                    cards.put(card.getCardId(), card);
                } catch (IOException e) {
                    logger.error("Error reading card from file={}", file, e);
                }
            }
        }
    }

    private void addStructuresFromDirectory(File directory, ObjectMapper objectMapper) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isHidden()) {
                continue;
            }
            if (file.isDirectory()) {
                addStructuresFromDirectory(file, objectMapper);
            } else {
                try {
                    MutableStructure structure = objectMapper.readValue(file, MutableStructure.class);
                    structures.put(structure.getStructureId(), structure);
                } catch (IOException e) {
                    logger.error("Error reading structure from file={}", file, e);
                }
            }
        }
    }
}
