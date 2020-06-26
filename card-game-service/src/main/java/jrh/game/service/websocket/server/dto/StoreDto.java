package jrh.game.service.websocket.server.dto;

import jrh.game.api.Store;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class StoreDto {

    public final List<CardDto> row;

    private StoreDto(List<CardDto> row) {
        this.row = row;
    }

    public static StoreDto fromStore(Store store) {
        return new StoreDto(store.getRow().stream().map(CardDto::fromCard).collect(toList()));
    }
}
