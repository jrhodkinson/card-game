package jrh.game.service.websocket.server.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.common.description.AtomicDescription;
import jrh.game.common.description.Description;
import jrh.game.common.description.DescriptionContext;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class DescriptionDto {

    private static final DescriptionContext CONTEXT = DescriptionContext.defaultContext();

    @JsonValue
    public final List<DescriptionLine> description;

    private DescriptionDto(List<DescriptionLine> description) {
        this.description = description;
    }

    public static DescriptionDto fromDescription(Description description) {
        return new DescriptionDto(descriptionLines(description));
    }

    private static List<DescriptionLine> descriptionLines(Description description) {
        return description.getAtomicDescriptions().stream().map(DescriptionDto::descriptionTokens)
                .map(DescriptionLine::new).collect(toList());
    }

    private static List<DescriptionToken> descriptionTokens(AtomicDescription atomicDescription) {
        return atomicDescription.getPieces().stream()
                .map(dp -> new DescriptionToken(dp.get(CONTEXT), dp.getContext(CONTEXT).orElse(null)))
                .collect(toList());
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
        public final String context;

        private DescriptionToken(String token, String context) {
            this.token = token;
            this.context = context;
        }

        public String getToken() {
            return token;
        }

        public String getContext() {
            return context;
        }
    }
}
