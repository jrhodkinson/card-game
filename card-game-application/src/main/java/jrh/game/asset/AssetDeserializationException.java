package jrh.game.asset;

import java.io.IOException;

public class AssetDeserializationException extends IOException {

    public AssetDeserializationException() {
    }

    public AssetDeserializationException(String message) {
        super(message);
    }

    public AssetDeserializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssetDeserializationException(Throwable cause) {
        super(cause);
    }
}
