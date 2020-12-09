package jrh.game.common.description;

import java.util.Optional;

public class KeywordDescriptionPiece implements DescriptionPiece {

    private final Keyword keyword;

    public KeywordDescriptionPiece(Keyword keyword) {
        this.keyword = keyword;
    }

    @Override
    public String get(DescriptionContext descriptionContext) {
        return keyword.toString().toLowerCase();
    }

    @Override
    public Optional<String> getContext(DescriptionContext descriptionContext) {
        return Optional.ofNullable(descriptionContext.getHelp(keyword));
    }
}
