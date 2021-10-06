package dev.barbz.httpserver.core.method;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.barbz.httpserver.configuration.HttpServerProperties;
import dev.barbz.httpserver.core.io.HttpModelError;
import dev.barbz.httpserver.core.io.HttpRequest;
import dev.barbz.httpserver.core.io.HttpResponse;
import dev.barbz.httpserver.core.util.FileUtil;
import dev.barbz.httpserver.core.util.HttpContentType;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import static dev.barbz.httpserver.core.util.HttpStatus.NOT_FOUND;
import static dev.barbz.httpserver.core.util.HttpStatus.OK;

public record HttpGet(HttpServerProperties properties,
                      Socket client) implements HttpHandler {

    @Override
    public void handle(HttpRequest request) {
        Path filePath = filePath(request.path(), properties.resourcesPath());
        ObjectMapper mapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        HttpResponse response;

        try {
            if (Files.exists(filePath)) {
                String mimeType = FileUtil.guessMIMEType(filePath);
                HttpContentType contentType = HttpContentType.statusOfMimeType(mimeType);
                assert contentType != null;
                String header = contentType.header();
                response = new HttpResponse(OK, FileUtil.retrieveFile(filePath), Collections.singletonList(header));
            } else {
                HttpModelError error = new HttpModelError()
                        .shortMessage("Resource not found")
                        .detailedError("Can not find the resource: ".concat(filePath.toString()))
                        .status(NOT_FOUND);

                response = new HttpResponse(NOT_FOUND, mapper.writeValueAsString(error).getBytes(),
                        Collections.singletonList("Content-Type: application/json; charset=utf-8"));
            }
            sendResponse(response, client);
        } catch (IOException e) {
            send500Error(client);
        }
    }
}
