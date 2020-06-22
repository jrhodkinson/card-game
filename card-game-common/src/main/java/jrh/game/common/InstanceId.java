package jrh.game.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.UUID;

public final class InstanceId {

    @JsonValue
    private final UUID instanceId;

    @JsonCreator
    private InstanceId(UUID instanceId) {
        this.instanceId = instanceId;
    }

    public static InstanceId randomInstanceId() {
        return new InstanceId(UUID.randomUUID());
    }

    public UUID toUUID() {
        return instanceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        InstanceId that = (InstanceId) o;

        return new EqualsBuilder().append(instanceId, that.instanceId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(instanceId).toHashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(instanceId);
    }
}
