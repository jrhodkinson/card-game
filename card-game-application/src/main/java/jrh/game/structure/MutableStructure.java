package jrh.game.structure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jrh.game.api.Power;
import jrh.game.api.Structure;
import jrh.game.asset.MutableStructureDeserializer;
import jrh.game.asset.MutableStructureSerializer;
import jrh.game.common.EntityId;
import jrh.game.common.StructureId;
import jrh.game.structure.power.AbstractPower;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@JsonDeserialize(using = MutableStructureDeserializer.class)
@JsonSerialize(using = MutableStructureSerializer.class)
public class MutableStructure implements Structure {

    private final EntityId entityId = EntityId.randomEntityId();
    private final StructureId structureId;
    private final String name;
    private final Map<Class<? extends AbstractPower>, AbstractPower> powers = new HashMap<>();
    private int health;

    @JsonCreator
    public MutableStructure(@JsonProperty("id") StructureId structureId, @JsonProperty("name") String name,
            @JsonProperty("health") int health) {
        this.structureId = structureId;
        this.name = name;
        this.health = health;
    }

    @Override
    public EntityId getEntityId() {
        return entityId;
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

    public void addPower(AbstractPower power) {
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
        for (AbstractPower power : powers.values()) {
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
        return this.entityId.equals(((MutableStructure) o).entityId);
    }

    @Override
    public int hashCode() {
        return entityId.hashCode();
    }
}
