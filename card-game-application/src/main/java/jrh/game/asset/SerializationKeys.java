package jrh.game.asset;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import jrh.game.card.behaviour.Behaviour;
import jrh.game.structure.power.Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.util.Set;

public class SerializationKeys {

    private static final Logger logger = LogManager.getLogger(SerializationKeys.class);

    private static final BiMap<String, Class<? extends Behaviour>> BEHAVIOURS = HashBiMap.create();
    private static final BiMap<String, Class<? extends Power>> POWERS = HashBiMap.create();

    static {
        Reflections reflections = new Reflections("jrh.game");
        Set<Class<?>> serializableClasses = reflections.getTypesAnnotatedWith(JsonKey.class);
        for (Class<?> serializableClass : serializableClasses) {
            String key = serializableClass.getAnnotation(JsonKey.class).value();
            if (Behaviour.class.isAssignableFrom(serializableClass)) {
                BEHAVIOURS.put(key, serializableClass.asSubclass(Behaviour.class));
            } else if (Power.class.isAssignableFrom(serializableClass)) {
                POWERS.put(key, serializableClass.asSubclass(Power.class));
            } else {
                logger.error("Unsupported @JsonKey annotation on " + serializableClass.toString());
            }
        }
        logger.info("Loaded behaviours: {}", BEHAVIOURS);
        logger.info("Loaded powers: {}", POWERS);
    }

    public static Class<? extends Behaviour> getBehaviourClass(String key) {
        return BEHAVIOURS.get(key);
    }

    public static String getBehaviourKey(Class<? extends Behaviour> behaviourClass) {
        return BEHAVIOURS.inverse().get(behaviourClass);
    }

    public static Class<? extends Power> getPowerClass(String key) {
        return POWERS.get(key);
    }

    public static String getPowerKey(Class<? extends Power> powerClass) {
        return POWERS.inverse().get(powerClass);
    }

    private SerializationKeys() {
        // not instantiable
    }
}
