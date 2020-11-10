package jrh.game.service;

import jrh.game.asset.AssetLibrary;
import jrh.game.asset.FileSystemAssetLibrary;
import jrh.game.common.User;
import jrh.game.match.MutableMatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    private void start() {
        AssetLibrary assetLibrary = assetLibrary();
        MutableMatch match = new MutableMatch(assetLibrary, new User("Hero"), new User("Villain"));
        Server server = new Server(7000);
        server.start(match, match.getEventBus());
        match.start();
    }

    private AssetLibrary assetLibrary() {
        AssetLibrary assetLibrary = null;
        try {
            return new FileSystemAssetLibrary();
        } catch (IOException e) {
            logger.error("Failed to load asset library, exiting.", e);
            System.exit(1);
            return null;
        }
    }

}
