package jrh.game.structure;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import jrh.game.api.Power;
import jrh.game.api.Structure;
import jrh.game.asset.MutableStructureDeserializer;
import jrh.game.asset.MutableStructureSerializer;
import jrh.game.common.EntityId;
import jrh.game.common.StructureId;
import jrh.game.common.description.Description;
import jrh.game.structure.power.AbstractPower;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@JsonDeserialize(using = MutableStructureDeserializer.class)
@JsonSerialize(using = MutableStructureSerializer.class)
public class MutableStructure implements Structure {

    private final EntityId entityId = EntityId.randomEntityId();
    private final StructureId structureId;
    private final String name;
    private final String flavorText;
    private final ListMultimap<Class<? extends Power>, AbstractPower> powers = LinkedListMultimap.create();
    private int health;

    public MutableStructure(StructureId structureId, String name, int health) {
        this(structureId, name, health, null);
    }

    public MutableStructure(StructureId structureId, String name, int health, String flavorText) {
        this.structureId = structureId;
        this.name = name;
        this.health = health;
        this.flavorText = flavorText;
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
    public Optional<String> getFlavorText() {
        return Optional.ofNullable(flavorText);
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public Description getDescription() {
        return Description.of(
            getAllPowers().stream().map(Power::getDescription).collect(toList()));
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
    public Collection<Power> getAllPowers() {
        return Collections.unmodifiableCollection(powers.values());
    }

    @Override
    public List<Power> getPowers(Class<? extends Power> powerClass) {
        return powers.get(powerClass).stream().map(ap -> (Power) ap).collect(toList());
    }

    MutableStructure duplicate() {
        MutableStructure duplicate = new MutableStructure(structureId, name, health, flavorText);
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
