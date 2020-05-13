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
import java.util.Iterator;

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
        JsonNode behaviours = tree.get("behaviours");
        for (Iterator<String> it = behaviours.fieldNames(); it.hasNext();) {
            String field = it.next();
            Class<? extends Behaviour> behaviourClass = BehaviourSerializationKeys.getClass(field);
            Behaviour behaviour = behaviours.get(field).traverse(jp.getCodec()).readValueAs(behaviourClass);
            cardBuilder.withBehaviour(behaviour);
        }
        return cardBuilder.build();
    }

}