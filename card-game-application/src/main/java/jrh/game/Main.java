package jrh.game;

import jrh.game.card.Library;

import java.io.File;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        File library = Paths.get("card-game-application/src/main/resources/cards").toFile();
        Library.dumpCards(library);
//        Application application = new Application();
//        application.start();
    }
}
