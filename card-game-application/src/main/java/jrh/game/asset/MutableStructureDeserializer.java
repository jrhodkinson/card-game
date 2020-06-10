package jrh.game.asset;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import jrh.game.common.StructureId;
import jrh.game.structure.MutableStructure;
import jrh.game.structure.power.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class MutableStructureDeserializer extends StdDeserializer<MutableStructure> {

    private static final Logger logger = LogManager.getLogger(MutableStructureDeserializer.class);

    protected MutableStructureDeserializer() {
        super(MutableStructure.class);
    }

    @Override
    public MutableStructure deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        JsonNode tree = jp.readValueAsTree();
        StructureId id = tree.get("id").traverse(jp.getCodec()).readValueAs(StructureId.class);
        String name = tree.get("name").traverse(jp.getCodec()).readValueAs(String.class);
        Integer health = tree.get("health").traverse(jp.getCodec()).readValueAs(Integer.class);
        MutableStructure structure = new MutableStructure(id, name, health);
        JsonNode powers = tree.get("powers");
        for (int i = 0; i < powers.size(); i++) {
            JsonNode powerJson = powers.get(i);
            String powerKey = powerJson.get(0).asText();
            Class<? extends AbstractPower> powerClass = SerializationKeys.getPowerClass(powerKey)
                    .asSubclass(AbstractPower.class);
            if (powerJson.size() == 1) {
                try {
                    structure.addPower(powerClass.getConstructor().newInstance());
                } catch (InstantiationException | NoSuchMethodException | IllegalAccessException
                        | InvocationTargetException e) {
                    logger.error("Error instantiating power={} for structureId={} with no-args constructor", powerJson,
                            tree.get("id"), e);
                }
            } else if (powerJson.size() == 2) {
                AbstractPower power = powerJson.get(1).traverse(jp.getCodec()).readValueAs(powerClass);
                structure.addPower(power);
            } else {
                logger.error("Invalid power={} for structureId={}, too many elements in array", powerJson,
                        tree.get("id"));
            }
        }
        return structure;
    }

}