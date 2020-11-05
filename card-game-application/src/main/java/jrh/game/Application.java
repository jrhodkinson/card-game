package jrh.game;

import jrh.game.asset.AssetLibrary;
import jrh.game.asset.FileSystemAssetLibrary;
import jrh.game.common.User;
import jrh.game.match.MutableMatch;
import jrh.game.service.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Application {

    private static final Logger logger = LogManager.getLogger(Application.class);
    private static final String OPTION_FORMAT = "%2d: %-32s";

    void start() {
        AssetLibrary assetLibrary = null;
        try {
            assetLibrary = new FileSystemAssetLibrary();
        } catch (IOException e) {
            logger.error("Failed to load asset library, exiting.", e);
            System.exit(1);
        }
        MutableMatch match = new MutableMatch(assetLibrary, new User("Hero"), new User("Villain"));
        Server server = new Server(7000);
        server.start(match, match.getEventBus());
        match.start();
    }
}
