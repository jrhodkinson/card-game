package jrh.game.service.websocket.server.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.common.description.AtomicDescription;
import jrh.game.common.description.Description;
import jrh.game.common.description.DescriptionContext;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class DescriptionDto {

    @JsonValue
    public final List<DescriptionLine> description;

    private DescriptionDto(List<DescriptionLine> description) {
        this.description = description;
    }

    public static class Factory {

        private final DescriptionContext descriptionContext;

        public Factory(DescriptionContext descriptionContext) {
            this.descriptionContext = descriptionContext;
        }

        public DescriptionDto descriptionDto(Description description) {
            return new DescriptionDto(descriptionLines(description));
        }

        private List<DescriptionLine> descriptionLines(Description description) {
            return description.getAtomicDescriptions().stream().map(this::descriptionTokens)
                .map(DescriptionLine::new).collect(toList());
        }

        private List<DescriptionToken> descriptionTokens(AtomicDescription atomicDescription) {
            return atomicDescription.getPieces().stream()
                .map(dp -> new DescriptionToken(dp.get(descriptionContext), dp.getType(), dp.getHelp(descriptionContext).orElse(null)))
                .collect(toList());
        }
    }

    public static class DescriptionLine {

        @JsonValue
        public final List<DescriptionToken> tokens;

        private DescriptionLine(List<DescriptionToken> tokens) {
            this.tokens = tokens;
        }
    }

    public static class DescriptionToken {

        public final String token;
        public final String type;
        public final String context;

        private DescriptionToken(String token, String type, String context) {
            this.token = token;
            this.type = type;
            this.context = context;
        }

        public String getToken() {
            return token;
        }

        public String getType() {
            return type;
        }

        public String getContext() {
            return context;
        }
    }
}
