package dev.barbz.httpserver.core.method;

import dev.barbz.httpserver.core.io.HttpRequest;

public interface HttpHandler {

    void handle(HttpRequest request);
}
