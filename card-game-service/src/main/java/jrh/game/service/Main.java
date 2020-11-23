package jrh.game.service;

import jrh.game.asset.AssetLibrary;
import jrh.game.asset.FileSystemAssetLibrary;
import jrh.game.service.account.Accounts;
import jrh.game.service.lobby.MatchManager;
import jrh.game.service.lobby.Matchmaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    // TODO remove LOGIN message
    // TODO stop switching active user

    private void start() {
        Accounts accounts = new Accounts();
        MatchManager matchManager = new MatchManager(assetLibrary(), accounts);
        Matchmaker matchmaker = new Matchmaker(matchManager);
        Server server = new Server(matchManager, matchmaker, accounts);
        server.start();
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
