package jrh.game.service;

import jrh.game.card.store.Database;
import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.SystemConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Map;

public class ServiceConfiguration {

    private static final Logger logger = LogManager.getLogger(ServiceConfiguration.class);

    private static final String DATABASE_HOST = "database.host";
    private static final String DATABASE_PORT = "database.port";

    private final Configuration configuration;

    public ServiceConfiguration() {
        configuration = buildConfiguration();
    }

    public Database database() {
        String host = configuration.getString(DATABASE_HOST);
        int port = configuration.getInt(DATABASE_PORT);
        return Database.instance(host, port);
    }

    private Configuration buildConfiguration() {
        CompositeConfiguration cc = new CompositeConfiguration();
        cc.addConfiguration(environment());
        cc.addConfiguration(new SystemConfiguration());
        cc.setThrowExceptionOnMissing(false);
        return cc;
    }

    private Configuration environment() {
        BaseConfiguration configuration = new BaseConfiguration();
        for (Map.Entry<String, String> config : System.getenv().entrySet()) {
            configuration.addProperty(config.getKey().replaceAll("_", ".").toLowerCase(Locale.ENGLISH),
                    config.getValue());
        }
        return configuration;
    }
}
