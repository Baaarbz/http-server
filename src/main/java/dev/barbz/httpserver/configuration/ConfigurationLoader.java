package dev.barbz.httpserver.configuration;

import dev.barbz.httpserver.datasource.DatasourceUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static java.util.Objects.isNull;


/**
 * Configurator class loader.
 * Duty manager to read the properties and log
 * custom messages about the configuration.
 */
@Getter
@Slf4j
public class ConfigurationLoader {

    /**
     * HTTP Server properties, this class will hold
     * the information about server port...
     */
    private HttpServerProperties properties;

    /**
     * Public constructor
     */
    public ConfigurationLoader() {
        runConfiguration();
    }

    /**
     * Run all the configuration, logs the banner and useful
     * information about the HTTP Server, also instantiates the
     * HTTP Server properties.
     */
    public void runConfiguration() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            // Load properties
            Properties properties = new Properties();
            properties.load(inputStream);
            // Instantiates HTTP Server properties
            loadProperties(properties);
            // Log configuration and banner
            logConfiguration();
        } catch (IOException e) {
            // In case there are not file config.properties or can not read it, then
            // log the error and stop the application.
            log.error("Can not read properties. {}", e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Instantiates the properties of the HTTP server from Properties.
     *
     * @param properties properties of "resources/config.properties"
     */
    private void loadProperties(Properties properties) {
        HttpServerProperties.Server server = loadServerProperties(properties);
        HttpServerProperties.Security security = loadSecurityProperties(properties);
        HttpServerProperties.Datasource datasource = loadDatasourceProperties(properties);

        this.properties = new HttpServerProperties(server, security, datasource);
    }

    /**
     * Log basic configuration and custom banner.
     */
    private void logConfiguration() {
        // Load custom banner for HTTP Server.
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("banner.txt")) {
            if (isNull(inputStream)) {
                throw new FileNotFoundException("Custom banner not found in 'resources/'");
            }
            // Print custom banner and configuration.
            System.out.printf(IOUtils.toString(inputStream, StandardCharsets.UTF_8).concat("\n"),
                    properties.server().port(),
                    properties.server().threads(),
                    properties.server().resourcesPath(),
                    properties.security().enabled() ? "Yes" : "No",
                    DatasourceUtil.generateConnectionUrl(properties.datasource()),
                    properties.datasource().user(),
                    properties.datasource().password());
        } catch (IOException e) {
            log.error("Can not read banner. {}", e.getMessage());
        }
    }

    private HttpServerProperties.Server loadServerProperties(Properties properties) {
        // Server properties
        int port = Integer.parseInt(properties.getProperty("server.port", "8080"));
        int threads = Integer.parseInt(properties.getProperty("server.threads", "4"));
        String resourcesPath = properties.getProperty("server.resources.path", "/");

        return new HttpServerProperties.Server(port, threads, resourcesPath);
    }

    private HttpServerProperties.Security loadSecurityProperties(Properties properties) {
        // Security properties
        boolean enabled = Boolean.parseBoolean(properties.getProperty("security.enabled", "false"));
        String defaultUser = properties.getProperty("security.user", null);
        String defaultPwd = properties.getProperty("security.password", null);

        return new HttpServerProperties.Security(enabled, defaultUser, defaultPwd);
    }

    private HttpServerProperties.Datasource loadDatasourceProperties(Properties properties) {
        // Datasource properties
        String user = properties.getProperty("datasource.user", null);
        String pwd = properties.getProperty("datasource.password", null);
        String host = properties.getProperty("datasource.host", null);
        String port = properties.getProperty("datasource.port", "");
        String database = properties.getProperty("datasource.database", null);
        String type = properties.getProperty("datasource.type", null);

        return new HttpServerProperties.Datasource(user, pwd, host, port, type, database);
    }
}
