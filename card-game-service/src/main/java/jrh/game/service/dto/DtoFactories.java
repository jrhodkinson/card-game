package jrh.game.service.dto;

import jrh.game.common.asset.AssetLibrary;
import jrh.game.common.description.DescriptionContext;

public class DtoFactories {

    private final DescriptionDto.Factory descriptionFactory;
    private final CardDto.Factory cardFactory;
    private final StructureDto.Factory structureFactory;
    private final PlayerDto.Factory playerFactory;
    private final TurnDto.Factory turnFactory;
    private final StoreDto.Factory storeFactory;
    private final MatchDto.Factory matchFactory;

    public DtoFactories(AssetLibrary assetLibrary) {
        DescriptionContext descriptionContext = new DescriptionContext(assetLibrary);
        descriptionFactory = new DescriptionDto.Factory(descriptionContext);
        cardFactory = new CardDto.Factory(descriptionFactory);
        structureFactory = new StructureDto.Factory(descriptionFactory);
        playerFactory = new PlayerDto.Factory(cardFactory, structureFactory);
        turnFactory = new TurnDto.Factory(cardFactory);
        storeFactory = new StoreDto.Factory(cardFactory);
        matchFactory = new MatchDto.Factory(playerFactory, turnFactory, storeFactory);
    }

    public MatchDto.Factory matchFactory() {
        return matchFactory;
    }

    public CardDto.Factory cardFactory() {
        return cardFactory;
    }

    public StructureDto.Factory structureFactory() {
        return structureFactory;
    }
}
