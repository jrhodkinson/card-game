package jrh.game.util;

import java.io.File;
import java.nio.file.Paths;

public class Constants {

    public static final File CARDS_DIRECTORY = Paths.get("card-game-application/src/main/resources/cards").toFile();

    public static final int STORE_SIZE = 7;

    public static final int HEALTH = 10;

    public static final int INITIAL_HAND_SIZE = 5;
    public static final int INITIAL_MONEY = 7;
    public static final int INITIAL_DAMAGE = 3;
}
