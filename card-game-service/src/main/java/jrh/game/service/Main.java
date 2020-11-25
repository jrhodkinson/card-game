package jrh.game.service;

import jrh.game.asset.AssetLibrary;
import jrh.game.asset.FileSystemAssetLibrary;
import jrh.game.card.store.Database;
import jrh.game.service.account.Accounts;
import jrh.game.service.lobby.MatchManager;
import jrh.game.service.lobby.MatchQueue;
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

    // TODO cancel match if player never connects
    // TODO forfeit match if player loses connection
    // TODO turn timer
    // TODO allow player to play 2 games in a row (remove from MatchManager)

    private void start() {
        Database database = Database.instance();
        Accounts accounts = new Accounts(database.accountStore());
        MatchManager matchManager = new MatchManager(assetLibrary(), accounts);
        MatchQueue matchQueue = new MatchQueue();
        Matchmaker matchmaker = new Matchmaker(matchManager, matchQueue);
        Server server = new Server(matchManager, matchQueue, accounts);

        matchmaker.start();
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
