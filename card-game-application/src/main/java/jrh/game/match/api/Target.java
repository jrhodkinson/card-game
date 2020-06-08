package jrh.game.match.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Target {
    @JsonProperty("self") SELF,
    @JsonProperty("other") OTHER,
    @JsonProperty("own-structures") OWN_STRUCTURES,
    @JsonProperty("other-structures") OTHER_STRUCTURES
}
