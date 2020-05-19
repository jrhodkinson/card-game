package jrh.game.card;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import jrh.game.card.behaviour.Behaviour;
import jrh.game.card.behaviour.BehaviourSerializationKeys;
import jrh.game.util.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class CardDeserializer extends StdDeserializer<Card> {

    private static final Logger logger = LogManager.getLogger(CardDeserializer.class);

    protected CardDeserializer() {
        super(Card.class);
    }

    @Override
    public Card deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        JsonNode tree = jp.readValueAsTree();
        CardId id = tree.get("id").traverse(jp.getCodec()).readValueAs(CardId.class);
        String name = tree.get("name").traverse(jp.getCodec()).readValueAs(String.class);
        Integer cost = tree.get("cost").traverse(jp.getCodec()).readValueAs(Integer.class);
        Color color = tree.get("color").traverse(jp.getCodec()).readValueAs(Color.class);
        Card.Builder cardBuilder = Card.card(id).withName(name).withCost(cost).withColor(color);
        Card card = cardBuilder.build();
        JsonNode behaviours = tree.get("behaviours");
        for (int i = 0; i < behaviours.size(); i++) {
            JsonNode behaviourJson = behaviours.get(i);
            String behaviourKey = behaviourJson.get(0).asText();
            Class<? extends Behaviour> behaviourClass = BehaviourSerializationKeys.getClass(behaviourKey);
            if (behaviourJson.size() == 1) {
                try {
                    card.addBehaviour(behaviourClass.getConstructor().newInstance());
                } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    logger.error("Error instantiating behaviour={} for cardId={} with no-args constructor", behaviourJson, tree.get("id"), e);
                }
            } else if (behaviourJson.size() == 2) {
                Behaviour behaviour = behaviourJson.get(1).traverse(jp.getCodec()).readValueAs(behaviourClass);
                card.addBehaviour(behaviour);
            } else {
                logger.error("Invalid behaviour={} for cardId={}, too many elements in array", behaviourJson, tree.get("id"));
            }
        }
        return card;
    }

}