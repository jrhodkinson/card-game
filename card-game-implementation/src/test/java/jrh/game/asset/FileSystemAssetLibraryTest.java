package jrh.game.asset;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class FileSystemAssetLibraryTest {

    @Test
    public void allAssetsAreLoadable() throws IOException {
        FileSystemAssetLibrary fileSystemAssetLibrary = new FileSystemAssetLibrary();
        assertThat(fileSystemAssetLibrary.getAllCardIds(), hasSize(greaterThan(1)));
        assertThat(fileSystemAssetLibrary.getAllStructureIds(), hasSize(greaterThan(1)));
    }
}
