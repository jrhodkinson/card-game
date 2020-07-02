package jrh.game.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.UUID;

public final class EntityId {

    @JsonValue
    private final UUID entityId;

    @JsonCreator
    private EntityId(UUID entityId) {
        this.entityId = entityId;
    }

    public static EntityId randomEntityId() {
        return new EntityId(UUID.randomUUID());
    }

    public UUID toUUID() {
        return entityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        EntityId that = (EntityId) o;

        return new EqualsBuilder().append(entityId, that.entityId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(entityId).toHashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(entityId);
    }
}
