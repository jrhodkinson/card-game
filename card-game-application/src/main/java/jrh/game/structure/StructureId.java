package jrh.game.structure;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class StructureId {

    private final String structureId;

    public StructureId(String structureId) {
        this.structureId = structureId;
    }

    @JsonValue
    @Override
    public String toString() {
        return structureId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        StructureId that = (StructureId) o;

        return new EqualsBuilder()
                .append(structureId, that.structureId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(structureId)
                .toHashCode();
    }
}
