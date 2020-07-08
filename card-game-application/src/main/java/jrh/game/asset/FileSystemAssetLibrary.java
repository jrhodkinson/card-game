package jrh.game.asset;

import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.card.CardImpl;
import jrh.game.common.CardId;
import jrh.game.common.ObjectMapperFactory;
import jrh.game.common.StructureId;
import jrh.game.structure.MutableStructure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FileSystemAssetLibrary implements AssetLibrary {

    static final String ASSETS_DIRECTORY = "assets";

    private static final Logger logger = LogManager.getLogger(FileSystemAssetLibrary.class);

    private final Map<CardId, CardImpl> cards = new HashMap<>();
    private final Map<StructureId, MutableStructure> structures = new HashMap<>();

    public FileSystemAssetLibrary() throws IOException {
        loadAssets();
    }

    @Override
    public Optional<CardImpl> getCard(CardId cardId) {
        return Optional.ofNullable(cards.get(cardId));
    }

    @Override
    public List<CardId> getAllCardIds() {
        return List.copyOf(cards.keySet());
    }

    @Override
    public Optional<MutableStructure> getStructure(StructureId structureId) {
        return Optional.ofNullable(structures.get(structureId));
    }

    @Override
    public List<StructureId> getAllStructureIds() {
        return List.copyOf(structures.keySet());
    }

    private void loadAssets() throws IOException {
        try {
            Path assetsDirectory = Path.of(getClass().getClassLoader().getResource(ASSETS_DIRECTORY).toURI());
            ObjectMapper objectMapper = ObjectMapperFactory.create();
            addCardsFromDirectory(assetsDirectory.resolve("cards").toFile(), objectMapper);
            addStructuresFromDirectory(assetsDirectory.resolve("structures").toFile(), objectMapper);
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

    private void addCardsFromDirectory(File directory, ObjectMapper objectMapper) throws IOException {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isHidden()) {
                continue;
            }
            if (file.isDirectory()) {
                addCardsFromDirectory(file, objectMapper);
            } else {
                CardImpl card = objectMapper.readValue(file, CardImpl.class);
                cards.put(card.getCardId(), card);
                logger.debug("Loaded card: {}, {}, {}", card.getCardId(), card.getName(), card.getDescription());
            }
        }
    }

    private void addStructuresFromDirectory(File directory, ObjectMapper objectMapper) throws IOException {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isHidden()) {
                continue;
            }
            if (file.isDirectory()) {
                addStructuresFromDirectory(file, objectMapper);
            } else {
                MutableStructure structure = objectMapper.readValue(file, MutableStructure.class);
                structures.put(structure.getStructureId(), structure);
                logger.debug("Loaded structure: {}, {}, {}", structure.getStructureId(), structure.getName());
            }
        }
    }
}
