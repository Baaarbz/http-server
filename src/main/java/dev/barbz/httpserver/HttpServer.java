package dev.barbz.httpserver;

import dev.barbz.httpserver.configuration.ConfigurationLoader;
import dev.barbz.httpserver.core.ListenerThread;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class HttpServer {

    /**
     * Run server method, this method will have the responsibility of orchestrate
     * the start of the HTTP server, reading the 'config.properties' and reading
     * the request to handle it
     *
     * @param args java user arguments
     */
    public static void main(String[] args) {
        ConfigurationLoader configurationLoader = new ConfigurationLoader();

        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(configurationLoader.properties().server().threads());
        executor.submit(() -> new ListenerThread(configurationLoader.properties()).start());
    }
}

