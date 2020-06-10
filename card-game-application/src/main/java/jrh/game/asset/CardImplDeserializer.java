package jrh.game.asset;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import jrh.game.api.Card;
import jrh.game.card.CardImpl;
import jrh.game.card.behaviour.AbstractBehaviour;
import jrh.game.common.CardId;
import jrh.game.common.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class CardImplDeserializer extends StdDeserializer<CardImpl> {

    private static final Logger logger = LogManager.getLogger(CardImplDeserializer.class);

    protected CardImplDeserializer() {
        super(Card.class);
    }

    @Override
    public CardImpl deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        JsonNode tree = jp.readValueAsTree();
        CardId id = tree.get("id").traverse(jp.getCodec()).readValueAs(CardId.class);
        String name = tree.get("name").traverse(jp.getCodec()).readValueAs(String.class);
        Integer cost = tree.get("cost").traverse(jp.getCodec()).readValueAs(Integer.class);
        Color color = tree.get("color").traverse(jp.getCodec()).readValueAs(Color.class);
        CardImpl.Builder cardBuilder = CardImpl.card(id).withName(name).withCost(cost).withColor(color);
        JsonNode behaviours = tree.get("behaviours");
        for (int i = 0; i < behaviours.size(); i++) {
            JsonNode behaviourJson = behaviours.get(i);
            String behaviourKey = behaviourJson.get(0).asText();
            Class<? extends AbstractBehaviour> behaviourClass = SerializationKeys.getBehaviourClass(behaviourKey);
            if (behaviourJson.size() == 1) {
                try {
                    cardBuilder.withBehaviour(behaviourClass.getConstructor().newInstance());
                } catch (InstantiationException | NoSuchMethodException | IllegalAccessException
                        | InvocationTargetException e) {
                    logger.error("Error instantiating behaviour={} for cardId={} with no-args constructor",
                            behaviourJson, tree.get("id"), e);
                }
            } else if (behaviourJson.size() == 2) {
                AbstractBehaviour behaviour = behaviourJson.get(1).traverse(jp.getCodec()).readValueAs(behaviourClass);
                cardBuilder.withBehaviour(behaviour);
            } else {
                logger.error("Invalid behaviour={} for cardId={}, too many elements in array", behaviourJson,
                        tree.get("id"));
            }
        }
        return cardBuilder.build();
    }

}