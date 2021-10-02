package dev.barbz.httpserver.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
public class HttpServer {

    /**
     * Run server method, this method will have the responsibility of orchestrate
     * the start of the HTTP server, reading the 'config.properties' and reading
     * the request to handle it
     * */
    public static void run() {
        ConfigurationLoader configurationLoader = new ConfigurationLoader();

        try (ServerSocket serverSocket = new ServerSocket(configurationLoader.properties().port())) {
            while (true) {
                try (Socket client = serverSocket.accept()) {
                    handleClient(client);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Handle client request, all the request received will read and
     * parsed.
     *
     * @param client socket client
     * @throws IOException thrown if an error occur trying to read the
     *                     received request.
     */
    private static void handleClient(Socket client) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        StringBuilder requestBuilder = new StringBuilder();

        // Request ends with one empty line (\r\n).
        // Client will send empty line, but inputStream will be still open,
        // we have to read it until one, empty line arrives.
        String line;
        while (!(line = br.readLine()).isBlank()) {
            requestBuilder.append(line)
                    .append("\r\n");
        }
        HttpRequest request = parseRequest(requestBuilder.toString());
    }

    /**
     * Parse the request, extracting the method, path, headers...
     *
     * @param request request as string
     */
    private static HttpRequest parseRequest(String request) {
        String[] requestLines = request.split("\r\n");
        // The request type (method, path and version) is contained in the first
        // line of the request.
        String[] requestType = requestLines[0].split(" ");
        String method = requestType[0];
        String path = requestType[1];

        // The header comes in the request after the 2 line, then we can read the headers
        // Now we only want to read the 'Accept' 'Content' or  'Authorization' header
        List<String> headers = Arrays.asList(requestLines).subList(2, requestLines.length).stream()
                .filter((String h) ->
                        (h.startsWith("Accept") || h.startsWith("Content") || h.startsWith("Authorization")))
                .collect(Collectors.toList());

        log.debug("Method: {}, Path: {}, Headers:", method, path);
        for (String header : headers) {
            log.debug("\t {}", header);
        }

        return new HttpRequest(method, path, headers);
    }

    /**
     * Configurator class loader.
     * Duty manager to read the properties and log
     * custom messages about the configuration.
     */
    @Getter
    private static class ConfigurationLoader {

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
            this.properties = new HttpServerProperties()
                    .port(Integer.parseInt(properties.getProperty("server.port", "8080")));
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
            } catch (IOException e) {
                log.error("Can not read banner. {}", e.getMessage());
            }
        }
    }
}

