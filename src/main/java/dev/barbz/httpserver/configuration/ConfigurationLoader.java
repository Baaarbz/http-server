package dev.barbz.httpserver.configuration;

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
            instantiatesHttpServerProperties(properties);
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
    private void instantiatesHttpServerProperties(Properties properties) {
        int port = Integer.parseInt(properties.getProperty("server.port", "8080"));
        int threads = Integer.parseInt(properties.getProperty("server.threads", "4"));
        String resourcesPath = properties.getProperty("resources.path", "/");

        this.properties = new HttpServerProperties(port, threads, resourcesPath);
    }

    /**
     * Log basic configuration and custom banner.
     */
    private void logConfiguration() {
        // Load custom banner for HTTP Server.
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("banner.txt")) {
            if (isNull(inputStream)) {
                throw new FileNotFoundException("Custom banner not found in resources/");
            }
            // Print custom banner.
            System.out.println(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
            // Log useful properties.
            log.info("Server port: {}", properties.port());
            log.info("Thread pool size: {}", properties.threads());
            log.info("Resources path: {}", properties.resourcesPath());
        } catch (IOException e) {
            log.error("Can not read banner. {}", e.getMessage());
        }
    }
}
