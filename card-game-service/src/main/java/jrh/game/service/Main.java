package jrh.game.service;

import jrh.game.asset.ConcreteAssetLibrary;
import jrh.game.asset.FileSystemAssetLibrary;
import jrh.game.card.store.Database;
import jrh.game.service.account.Accounts;
import jrh.game.service.account.Sessions;
import jrh.game.service.lobby.MatchManager;
import jrh.game.service.lobby.MatchQueue;
import jrh.game.service.lobby.Matchmaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.Instant;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    private void start() {
        ServiceConfiguration configuration = new ServiceConfiguration();
        String version = configuration.version();

        logger.info("Started {} version={} at {} in environment={}", getClass(), version, Instant.now(),
                configuration.environment());

        Cookies cookies = new Cookies(configuration.environment());
        Sessions sessions = new Sessions();
        Database database = configuration.database();
        Accounts accounts = new Accounts(database.accountStore());
        if (configuration.shouldEnablePlay()) {
            logger.info("Starting Server");
            ConcreteAssetLibrary assetLibrary = assetLibrary();
            MatchManager matchManager = new MatchManager(assetLibrary, accounts);
            MatchQueue matchQueue = new MatchQueue();
            Matchmaker matchmaker = new Matchmaker(matchManager, matchQueue);
            Server server = new Server(version, cookies, sessions, matchManager, matchQueue, accounts, assetLibrary);
            matchmaker.start();
            server.start();
        } else {
            logger.info("Starting NoPlayServer");
            NoPlayServer noPlayServer = new NoPlayServer(version, cookies, sessions, accounts);
            noPlayServer.start();
        }
    }

    private ConcreteAssetLibrary assetLibrary() {
        ConcreteAssetLibrary assetLibrary = null;
        try {
            return new FileSystemAssetLibrary();
        } catch (IOException e) {
            logger.error("Failed to load asset library, exiting.", e);
            System.exit(1);
            return null;
        }
    }

}
