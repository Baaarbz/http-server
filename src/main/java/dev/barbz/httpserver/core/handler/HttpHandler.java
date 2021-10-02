package dev.barbz.httpserver.core.handler;

import dev.barbz.httpserver.core.io.HttpRequest;

public interface HttpHandler {

    void handle(HttpRequest request);
}
