package jrh.game.service;

import jrh.game.card.store.Database;
import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.MapConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.SystemConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ServiceConfiguration {

    private static final Logger logger = LogManager.getLogger(ServiceConfiguration.class);

    private static final String VERSION = "game.version";
    private static final String ENVIRONMENT = "environment";
    private static final String ENABLE_PLAY = "enable.play";
    private static final String DATABASE_HOST = "database.host";
    private static final String DATABASE_PORT = "database.port";

    private final Configuration configuration;

    public ServiceConfiguration() {
        configuration = buildConfiguration();
    }

    public String version() {
        return configuration.getString(VERSION);
    }

    public Environment environment() {
        return Environment.valueOf(configuration.getString(ENVIRONMENT));
    }

    public Database database() {
        String host = configuration.getString(DATABASE_HOST);
        int port = configuration.getInt(DATABASE_PORT);
        return Database.instance(host, port);
    }

    public boolean shouldEnablePlay() {
        return configuration.getBoolean(ENABLE_PLAY);
    }

    private Configuration buildConfiguration() {
        CompositeConfiguration cc = new CompositeConfiguration();
        cc.addConfiguration(propertiesFile());
        cc.addConfiguration(environmentVariables());
        cc.addConfiguration(new SystemConfiguration());
        cc.addConfiguration(new MapConfiguration(defaults()));
        cc.setThrowExceptionOnMissing(false);
        return cc;
    }

    private Configuration propertiesFile() {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(params.properties().setFileName("service.properties"));
        try {
            return builder.getConfiguration();
        } catch (ConfigurationException e) {
            logger.info("Error loading properties configuration. Exiting.", e);
            System.exit(1);
        }
        return null;
    }

    private Configuration environmentVariables() {
        BaseConfiguration configuration = new BaseConfiguration();
        for (Map.Entry<String, String> config : System.getenv().entrySet()) {
            configuration.addProperty(config.getKey().replaceAll("_", ".").toLowerCase(Locale.ENGLISH),
                    config.getValue());
        }
        return configuration;
    }

    private Map<String, String> defaults() {
        Map<String, String> defaults = new HashMap<>();
        defaults.put(ENVIRONMENT, Environment.DEVELOPMENT.toString());
        defaults.put(ENABLE_PLAY, "true");
        return defaults;
    }
}
