package jrh.game.asset;

import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.api.Card;
import jrh.game.api.Structure;
import jrh.game.card.CardImpl;
import jrh.game.common.CardId;
import jrh.game.common.ObjectMapperFactory;
import jrh.game.common.StructureId;
import jrh.game.common.description.DescriptionContext;
import jrh.game.structure.MutableStructure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class FileSystemAssetLibrary implements ConcreteAssetLibrary {

    static final String ASSETS_DIRECTORY = "assets";

    private static final Logger logger = LogManager.getLogger(FileSystemAssetLibrary.class);

    private final Map<CardId, CardImpl> cards = new HashMap<>();
    private final Map<StructureId, MutableStructure> structures = new HashMap<>();

    public FileSystemAssetLibrary() throws IOException {
        loadAssets();
        logAssets();
    }

    @Override
    public Optional<Card> getCard(CardId cardId) {
        return Optional.ofNullable(cards.get(cardId));
    }

    @Override
    public Optional<CardImpl> getCardImpl(CardId cardId) {
        return Optional.ofNullable(cards.get(cardId));
    }

    @Override
    public Optional<Structure> getStructure(StructureId structureId) {
        return Optional.ofNullable(structures.get(structureId));
    }

    @Override
    public Optional<MutableStructure> getMutableStructure(StructureId structureId) {
        return Optional.ofNullable(structures.get(structureId));
    }

    @Override
    public List<CardId> getAllCardIds() {
        return List.copyOf(cards.keySet());
    }

    @Override
    public List<StructureId> getAllStructureIds() {
        return List.copyOf(structures.keySet());
    }

    private void loadAssets() throws IOException {
        FileSystem fileSystem = null;
        try {
            Path assetsDirectory;
            URI uri = getClass().getClassLoader().getResource(ASSETS_DIRECTORY).toURI();
            if ("jar".equals(uri.getScheme())) {
                fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap(), null);
                assetsDirectory = fileSystem.getPath(ASSETS_DIRECTORY);
            } else {
                assetsDirectory = Paths.get(uri);
            }
            ObjectMapper objectMapper = ObjectMapperFactory.create();
            addCardsFromDirectory(assetsDirectory.resolve("cards"), objectMapper);
            addStructuresFromDirectory(assetsDirectory.resolve("structures"), objectMapper);
        } catch (URISyntaxException e) {
            throw new IOException(e);
        } finally {
            if (fileSystem != null) {
                fileSystem.close();
            }
        }
    }

    private void logAssets() {
        DescriptionContext descriptionContext = new DescriptionContext(this);
        cards.keySet().stream().sorted(Comparator.comparing(CardId::toString)).forEach((cardId) -> {
            Card card = cards.get(cardId);
            logger.debug("Loaded card: {}, {}, {}", card.getCardId(), card.getName(), card.getDescription().get(descriptionContext));
        });
        structures.keySet().stream().sorted(Comparator.comparing(StructureId::toString)).forEach((structureId) -> {
            Structure structure = structures.get(structureId);
            logger.debug("Loaded structure: {}, {}, {}", structure.getStructureId(), structure.getName(), structure.getDescription().get(descriptionContext));
        });
    }

    private void addCardsFromDirectory(Path directory, ObjectMapper objectMapper) throws IOException {
        List<Path> paths = Files.walk(directory).filter(p -> !Files.isDirectory(p)).collect(toList());
        for (Path path : paths) {
            if (Files.isHidden(path)) {
                continue;
            }
            CardImpl card = objectMapper.readValue(Files.newInputStream(path), CardImpl.class);
            cards.put(card.getCardId(), card);
        }
    }

    private void addStructuresFromDirectory(Path directory, ObjectMapper objectMapper) throws IOException {
        List<Path> paths = Files.walk(directory).filter(p -> !Files.isDirectory(p)).collect(toList());
        for (Path path : paths) {
            if (Files.isHidden(path)) {
                continue;
            }
            MutableStructure structure = objectMapper.readValue(Files.newInputStream(path), MutableStructure.class);
            structures.put(structure.getStructureId(), structure);
        }
    }

    private Path getAssetsDirectoryPath() throws URISyntaxException, IOException {
        URI uri = getClass().getClassLoader().getResource(ASSETS_DIRECTORY).toURI();
        if ("jar".equals(uri.getScheme())) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap(), null);
            return fileSystem.getPath(ASSETS_DIRECTORY);
        } else {
            return Paths.get(uri);
        }
    }
}
