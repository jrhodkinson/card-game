package jrh.game.structure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jrh.game.match.api.Damageable;

import java.util.UUID;

public class Structure implements Damageable {

    private final UUID instanceId = UUID.randomUUID();
    private final StructureId structureId;
    private final String name;
    private int health;

    @JsonCreator
    public Structure(@JsonProperty("id") StructureId structureId, @JsonProperty("name") String name, @JsonProperty("health") int health) {
        this.structureId = structureId;
        this.name = name;
        this.health = health;
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

    Structure duplicate() {
        return new Structure(structureId, name, health);
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
