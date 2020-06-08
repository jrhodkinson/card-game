package jrh.game.structure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jrh.game.asset.MutableStructureDeserializer;
import jrh.game.asset.MutableStructureSerializer;
import jrh.game.event.EventBus;
import jrh.game.structure.api.Structure;
import jrh.game.structure.power.Power;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@JsonDeserialize(using = MutableStructureDeserializer.class)
@JsonSerialize(using = MutableStructureSerializer.class)
public class MutableStructure implements Structure {

    private final UUID instanceId = UUID.randomUUID();
    private final StructureId structureId;
    private final String name;
    private final Map<Class<? extends Power>, Power> powers = new HashMap<>();
    private int health;

    @JsonCreator
    public MutableStructure(@JsonProperty("id") StructureId structureId, @JsonProperty("name") String name,
            @JsonProperty("health") int health) {
        this.structureId = structureId;
        this.name = name;
        this.health = health;
    }

    public void registerPowers(EventBus eventBus) {
        powers.values().forEach(power -> power.registerWith(eventBus));
    }

    public void unregisterPowers(EventBus eventBus) {
        powers.values().forEach(power -> power.unregisterWith(eventBus));
    }

    @Override
    public UUID getInstanceId() {
        return instanceId;
    }

    @Override
    public StructureId getStructureId() {
        return structureId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        return health;
    }

    void changeHealth(int amount) {
        health += amount;
    }

    public void addPower(Power power) {
        powers.put(power.getClass(), power);
        power.forStructure(this);
    }

    @Override
    public boolean hasPower(Class<? extends Power> powerClass) {
        return powers.containsKey(powerClass);
    }

    @Override
    public Collection<Power> getPowers() {
        return Collections.unmodifiableCollection(powers.values());
    }

    @Override
    public Power getPower(Class<? extends Power> powerClass) {
        return powers.get(powerClass);
    }

    MutableStructure duplicate() {
        MutableStructure duplicate = new MutableStructure(structureId, name, health);
        for (Power power : powers.values()) {
            duplicate.addPower(power.duplicate());
        }
        return duplicate;
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", name, health);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        return this.instanceId.equals(((MutableStructure) o).instanceId);
    }

    @Override
    public int hashCode() {
        return instanceId.hashCode();
    }
}
