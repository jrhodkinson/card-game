package jrh.game.asset;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import jrh.game.card.behaviour.Behaviour;
import jrh.game.structure.power.Power;
import jrh.game.util.ObjectMapperFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Map;

public class SerializationKeys {

    private static final Logger logger = LogManager.getLogger(SerializationKeys.class);
    private static final String BEHAVIOURS = "behaviours.json";
    private static final String POWERS = "powers.json";
    private static final BiMap<String, Class<? extends Behaviour>> behaviourClasses = load(Behaviour.class, BEHAVIOURS);
    private static final BiMap<String, Class<? extends Power>> powerClasses = load(Power.class, POWERS);

    private SerializationKeys() {

    }

    @SuppressWarnings("unchecked")
    private static <T> BiMap<String, Class<? extends T>> load(Class<T> clazz, String location) {
        try {
            File file = Paths.get(ClassLoader.getSystemResource(location).toURI()).toFile();
            ObjectMapper objectMapper = ObjectMapperFactory.create();
            Map<String, String> fullyQualifiedClasses = objectMapper.readValue(file, new TypeReference<>() {
            });
            BiMap<String, Class<? extends T>> classes = HashBiMap.create();
            for (Map.Entry<String, String> entry : fullyQualifiedClasses.entrySet()) {
                Class<? extends T> componentClass = (Class<? extends T>) Class
                        .forName(entry.getValue());
                classes.put(entry.getKey(), componentClass);
            }
            logger.info("Loaded type {}: {} from {}", clazz, classes, location);
            return classes;
        } catch (IOException | ClassNotFoundException | URISyntaxException e) {
            logger.error(e);
            System.exit(1);
        }
        return null;
    }

    public static Class<? extends Behaviour> getBehaviourClass(String key) {
        return behaviourClasses.get(key);
    }

    public static String getBehaviourKey(Class<? extends Behaviour> behaviourClass) {
        return behaviourClasses.inverse().get(behaviourClass);
    }

    public static Class<? extends Power> getPowerClass(String key) {
        return powerClasses.get(key);
    }

    public static String getPowerKey(Class<? extends Power> powerClass) {
        return powerClasses.inverse().get(powerClass);
    }
}
