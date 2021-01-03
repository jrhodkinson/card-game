package jrh.game.service;

import jrh.game.asset.ConcreteAssetLibrary;
import jrh.game.asset.FileSystemAssetLibrary;
import jrh.game.card.store.Database;
import jrh.game.service.account.Accounts;
import jrh.game.service.account.Sessions;
import jrh.game.service.lobby.MatchManager;
import jrh.game.service.lobby.MatchQueue;
import jrh.game.service.lobby.Matchmaker;
import jrh.game.service.lobby.SlowQueueAlerter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.Instant;

import static jrh.game.service.Environment.DEVELOPMENT;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    private void start() {
        ServiceConfiguration configuration = new ServiceConfiguration();
        String version = configuration.version();

        logger
            .info("Started {} version={} at {} in environment={}", getClass(), version, Instant.now(),
                    configuration.environment());

        Cookies cookies = new Cookies(configuration.environment());
        Sessions sessions = new Sessions();
        Database database = configuration.database();
        Accounts accounts = new Accounts(database.accountStore());

        logger.info("Starting Server");
        ConcreteAssetLibrary assetLibrary = assetLibrary();
        MatchManager matchManager = new MatchManager(assetLibrary, accounts, database.historicMatchStore(), version);
        MatchQueue matchQueue = new MatchQueue();
        Matchmaker matchmaker = new Matchmaker(matchManager, matchQueue);
        Server server = new Server(version, cookies, sessions, matchManager, matchQueue, accounts, assetLibrary);
        matchmaker.start();
        server.start();

        new SlowQueueAlerter(configuration.alertService(), matchQueue, configuration.lookingForGroupChannel(), accounts, configuration.url());

        if (configuration.environment().equals(DEVELOPMENT)) {
            logger.info("In development environment, so queueing jack and terry.");
            matchQueue.join(accounts.getAccountIdByName("jack").orElseThrow());
            matchQueue.join(accounts.getAccountIdByName("terry").orElseThrow());
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
