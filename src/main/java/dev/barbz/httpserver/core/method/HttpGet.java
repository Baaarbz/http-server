package dev.barbz.httpserver.core.method;

import dev.barbz.httpserver.configuration.HttpServerProperties;
import dev.barbz.httpserver.core.io.HttpRequest;
import dev.barbz.httpserver.core.io.HttpResponse;
import dev.barbz.httpserver.core.util.FileUtil;
import dev.barbz.httpserver.core.util.HttpContentType;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

import static dev.barbz.httpserver.core.util.FileUtil.filePath;
import static dev.barbz.httpserver.core.util.HttpStatus.OK;

public record HttpGet(HttpServerProperties properties, Socket client) implements HttpHandler {

    @Override
    public void handle(HttpRequest request) {
        Path filePath = filePath(request.path(), properties.resourcesPath());
        HttpResponse response;

        try {
            if (!Files.exists(filePath)) {
                send404Error(client);
                return;
            }
            HttpContentType contentType = FileUtil.retrieveContentType(filePath);
            response = new HttpResponse(OK, FileUtil.retrieveFile(filePath), contentType.header());
            sendResponse(response, client);
        } catch (IOException e) {
            send500Error(client);
        }
    }
}
