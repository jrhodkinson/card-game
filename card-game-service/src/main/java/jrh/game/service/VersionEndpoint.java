package jrh.game.service;

import io.javalin.Javalin;
import io.javalin.http.Context;

import static java.util.Collections.singleton;
import static jrh.game.service.account.AccountRole.ANYONE;

public class VersionEndpoint {

    private final VersionResponse versionResponse;

    public VersionEndpoint(Javalin javalin, String version) {
        this.versionResponse = new VersionResponse(version);
        registerRoutes(javalin);
    }

    private void registerRoutes(Javalin javalin) {
        javalin.get("version", this::version, singleton(ANYONE));
    }

    private void version(Context context) {
        context.json(versionResponse);
    }

    private static class VersionResponse {

        private final String version;

        private VersionResponse(String version) {
            this.version = version;
        }

        public String getVersion() {
            return version;
        }
    }
}
