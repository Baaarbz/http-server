package dev.barbz.httpserver.core.util;

import dev.barbz.httpserver.core.exception.HttpServerException;
import dev.barbz.httpserver.core.io.HttpRequest;
import dev.barbz.httpserver.core.method.HttpGet;
import dev.barbz.httpserver.core.method.HttpHandler;

import static dev.barbz.httpserver.core.util.HttpStatus.METHOD_NOT_ALLOWED;

public class HttpUtil {

    public static void processRequest(HttpRequest request) {
        HttpHandler handler = switch (request.method()) {
            case GET -> new HttpGet();
            default -> {
                String detailedMessage = "This HTTP Serve do not handle your "
                        .concat(request.method().name())
                        .concat(" request.\nAllowed request: GET, POST, PUT, DELETE");
                throw new HttpServerException("Method not allowed.", detailedMessage, METHOD_NOT_ALLOWED);
            }
        };

        handler.handle(request);
    }
}
