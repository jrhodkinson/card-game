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
        JsonNode flavorNode = tree.get("flavor");
        MutableStructure structure;
        if (flavorNode == null) {
            structure = new MutableStructure(id, name, health);
        } else {
            String flavorText = flavorNode.traverse(jp.getCodec()).readValueAs(String.class);
            structure = new MutableStructure(id, name, health, flavorText);
        }
        JsonNode powers = tree.get("powers");
        for (int i = 0; i < powers.size(); i++) {
            JsonNode powerJson = powers.get(i);
            String powerKey = powerJson.get(0).asText();
            Class<? extends AbstractPower> powerClass = SerializationKeys.getPowerClass(powerKey);
            if (powerClass == null) {
                throw new AssetDeserializationException(String
                    .format(
                            "Invalid power=%s for structureId=%s, could not find Power class", powerKey,
                            tree.get("id")));
            }
            if (powerJson.size() == 1) {
                try {
                    structure.addPower(powerClass.getConstructor().newInstance());
                } catch (InstantiationException | NoSuchMethodException | IllegalAccessException
                        | InvocationTargetException e) {
                    throw new AssetDeserializationException(String
                        .format(
                                "Exception instantiating power=%s for structureId=%s with no-args constructor",
                                powerKey,
                                tree.get("id")),
                            e);
                }
            } else if (powerJson.size() == 2) {
                AbstractPower power = powerJson.get(1).traverse(jp.getCodec()).readValueAs(powerClass);
                structure.addPower(power);
            } else {
                throw new AssetDeserializationException(
                        String
                            .format("Invalid power=%s for structureId=%s, too many elements in array: %s", powerKey,
                                    tree.get("id"), powerJson));
            }
        }
        return structure;
    }

}
