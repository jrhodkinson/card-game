package jrh.game.service.lobby;

import jrh.game.asset.AssetLibrary;
import jrh.game.common.User;
import jrh.game.match.MutableMatch;

public class MatchManager {

    private final AssetLibrary assetLibrary;

    public MatchManager(AssetLibrary assetLibrary) {
        this.assetLibrary = assetLibrary;
    }

    public MatchAndEventBus newMatch() {
        MutableMatch mutableMatch = new MutableMatch(assetLibrary, new User("Hero"), new User("Villain"));
        return MatchAndEventBus.from(mutableMatch);
    }
}
