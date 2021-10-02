package dev.barbz.httpserver;

import dev.barbz.httpserver.server.HttpServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

    public static void main(String[] args) {
        HttpServer.run();
    }
}
