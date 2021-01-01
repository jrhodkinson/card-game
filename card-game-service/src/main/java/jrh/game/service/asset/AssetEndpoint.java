package jrh.game.service.asset;

import io.javalin.Javalin;
import io.javalin.http.Context;
import jrh.game.api.Card;
import jrh.game.api.Structure;
import jrh.game.asset.ConcreteAssetLibrary;
import jrh.game.service.dto.CardDto;
import jrh.game.service.dto.StructureDto;

import java.util.Comparator;
import java.util.Optional;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;
import static jrh.game.service.account.AccountRole.ANYONE;

public class AssetEndpoint {

    private final ConcreteAssetLibrary assetLibrary;
    private final CardDto.Factory cardFactory;
    private final StructureDto.Factory structureFactory;

    public AssetEndpoint(Javalin javalin, ConcreteAssetLibrary assetLibrary, CardDto.Factory cardFactory, StructureDto.Factory structureFactory) {
        this.assetLibrary = assetLibrary;
        this.cardFactory = cardFactory;
        this.structureFactory = structureFactory;
        registerRoutes(javalin);
    }

    private void registerRoutes(Javalin javalin) {
        javalin.routes(() -> {
            path("assets", () -> {
                get("cards", this::cards, singleton(ANYONE));
                get("structures", this::structures, singleton(ANYONE));
            });
        });
    }

    private void cards(Context context) {
        context.json(assetLibrary.getAllCardIds().stream()
            .map(assetLibrary::getCardImpl)
            .map(Optional::orElseThrow)
            .sorted(Comparator.comparing(Card::getName))
            .map(cardFactory::cardDto)
            .collect(toList()));
    }

    private void structures(Context context) {
        context.json(assetLibrary.getAllStructureIds().stream()
            .map(assetLibrary::getMutableStructure)
            .map(Optional::orElseThrow)
            .sorted(Comparator.comparing(Structure::getName))
            .map(structureFactory::structureDto)
            .collect(toList()));
    }
}
