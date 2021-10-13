package dev.barbz.httpserver.core.method;

import dev.barbz.httpserver.configuration.HttpServerProperties;
import dev.barbz.httpserver.core.io.HttpRequest;

import java.net.Socket;

public record HttpPost(HttpServerProperties properties, Socket client) implements HttpHandler  {
    @Override
    public void handle(HttpRequest request) {

    }
}
