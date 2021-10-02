package dev.barbz.httpserver;

import dev.barbz.httpserver.config.HttpServerConfigurator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

    public static void main(String[] args) {
        HttpServerConfigurator.run();
    }
}
