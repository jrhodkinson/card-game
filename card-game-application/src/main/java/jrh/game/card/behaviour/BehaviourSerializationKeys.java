package jrh.game.card.behaviour;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import jrh.game.util.ObjectMapperFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Map;

public class BehaviourSerializationKeys {

    private static final Logger logger = LogManager.getLogger(BehaviourSerializationKeys.class);
    private static final String BEHAVIOURS = "behaviours.json";
    private static final BiMap<String, Class<? extends Behaviour>> behaviourClasses = load();

    private BehaviourSerializationKeys() {

    }

    @SuppressWarnings("unchecked")
    private static BiMap<String, Class<? extends Behaviour>> load() {
        try {
            File file = Paths.get(ClassLoader.getSystemResource(BEHAVIOURS).toURI()).toFile();
            ObjectMapper objectMapper = ObjectMapperFactory.create();
            Map<String, String> fullyQualifiedBehaviourClasses = objectMapper.readValue(file, new TypeReference<>() {
            });
            BiMap<String, Class<? extends Behaviour>> behaviourClasses = HashBiMap.create();
            for (Map.Entry<String, String> entry : fullyQualifiedBehaviourClasses.entrySet()) {
                Class<? extends Behaviour> componentClass = (Class<? extends Behaviour>) Class
                        .forName(entry.getValue());
                behaviourClasses.put(entry.getKey(), componentClass);
            }
            logger.info("Loaded {}", behaviourClasses);
            return behaviourClasses;
        } catch (IOException | ClassNotFoundException | URISyntaxException e) {
            logger.error(e);
            System.exit(1);
        }
        return null;
    }

    public static Class<? extends Behaviour> getClass(String key) {
        return behaviourClasses.get(key);
    }

    public static String getKey(Class<? extends Behaviour> behaviourClass) {
        return behaviourClasses.inverse().get(behaviourClass);
    }
}
