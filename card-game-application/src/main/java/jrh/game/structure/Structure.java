package jrh.game.structure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jrh.game.asset.StructureDeserializer;
import jrh.game.asset.StructureSerializer;
import jrh.game.event.EventBus;
import jrh.game.match.api.Damageable;
import jrh.game.structure.power.Power;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@JsonDeserialize(using = StructureDeserializer.class)
@JsonSerialize(using = StructureSerializer.class)
public class Structure implements Damageable {

    private final UUID instanceId = UUID.randomUUID();
    private final StructureId structureId;
    private final String name;
    private final Map<Class<? extends Power>, Power> powers = new HashMap<>();
    private int health;

    @JsonCreator
    public Structure(@JsonProperty("id") StructureId structureId, @JsonProperty("name") String name, @JsonProperty("health") int health) {
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

    public UUID getInstanceId() {
        return instanceId;
    }

    public StructureId getStructureId() {
        return structureId;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void changeHealth(int amount) {
        health += amount;
    }

    public void addPower(Power power) {
        powers.put(power.getClass(), power);
        power.forStructure(this);
    }

    public boolean hasPower(Class<? extends Power> powerClass) {
        return powers.containsKey(powerClass);
    }

    public Collection<Power> getPowers() {
        return Collections.unmodifiableCollection(powers.values());
    }

    public Power getPower(Class<? extends Power> powerClass) {
        return powers.get(powerClass);
    }

    Structure duplicate() {
        Structure duplicate = new Structure(structureId, name, health);
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
        return this.instanceId.equals(((Structure) o).instanceId);
    }

    @Override
    public int hashCode() {
        return instanceId.hashCode();
    }
}
