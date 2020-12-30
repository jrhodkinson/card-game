package jrh.game.service.websocket.server.dto;

import jrh.game.api.Store;

import java.util.List;

public class StoreDto {

    public final List<CardDto> row;
    public final CardDto next;

    private StoreDto(List<CardDto> row, CardDto next) {
        this.row = row;
        this.next = next;
    }

    public static class Factory {

        private final CardDto.Factory cardFactory;

        public Factory(CardDto.Factory cardFactory) {
            this.cardFactory = cardFactory;
        }

        public StoreDto storeDto(Store store) {
            return new StoreDto(cardFactory.cardDtos(store.getRow()), cardFactory.cardDto(store.getNext()));
        }
    }
}
