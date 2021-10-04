package dev.barbz.httpserver.core.method;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.barbz.httpserver.core.io.HttpRequest;
import dev.barbz.httpserver.core.io.HttpResponse;

public interface HttpHandler {

    void handle(HttpRequest request);

    void sendResponse(HttpResponse response);
}
