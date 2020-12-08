package jrh.game.common.description;

public class KeywordDescriptionPiece implements DescriptionPiece {

    private final Keyword keyword;

    public KeywordDescriptionPiece(Keyword keyword) {
        this.keyword = keyword;
    }

    @Override
    public String get(DescriptionContext descriptionContext) {
        return keyword.toString().toLowerCase();
    }
}
