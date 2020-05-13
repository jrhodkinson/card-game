package jrh.game.card;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jrh.game.card.behaviour.Behaviour;
import jrh.game.card.behaviour.BehaviourSerializationKeys;

import java.io.IOException;

public class CardSerializer extends StdSerializer<Card> {

    protected CardSerializer() {
        super(Card.class);
    }

    @Override
    public void serialize(Card card, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("id", card.getId());
        gen.writeObjectField("name", card.getName());
        gen.writeObjectField("cost", card.getCost());
        gen.writeObjectField("color", card.getColor());
        gen.writeObjectFieldStart("behaviours");
        for (Behaviour behaviour : card.getBehaviours()) {
            gen.writeObjectField(BehaviourSerializationKeys.getKey(behaviour.getClass()), behaviour);
        }
        gen.writeEndObject();
        gen.writeEndObject();
    }

}