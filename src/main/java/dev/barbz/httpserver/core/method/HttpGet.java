package dev.barbz.httpserver.core.method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.barbz.httpserver.configuration.HttpServerProperties;
import dev.barbz.httpserver.core.exception.HttpServerException;
import dev.barbz.httpserver.core.io.HttpModelError;
import dev.barbz.httpserver.core.io.HttpRequest;
import dev.barbz.httpserver.core.io.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static dev.barbz.httpserver.core.util.HttpStatus.INTERNAL_SERVER_ERROR;
import static dev.barbz.httpserver.core.util.HttpStatus.NOT_FOUND;
import static dev.barbz.httpserver.core.util.HttpStatus.OK;

public class HttpGet implements HttpHandler {

    private final HttpServerProperties properties;
    private final Socket client;

    public HttpGet(HttpServerProperties properties, Socket client) {
        this.properties = properties;
        this.client = client;
    }

    @Override
    public void handle(HttpRequest request) {
        Path filePath = filePath(request.path());
        ObjectMapper mapper = new ObjectMapper();
        HttpResponse response;

        try {
            if (Files.exists(filePath)) {
                response = new HttpResponse(OK, "", null);
            } else {
                HttpModelError error = new HttpModelError()
                        .shortMessage("Resource not found")
                        .detailedError("Can not find the resource: ".concat(filePath.toString()))
                        .status(NOT_FOUND);

                response = new HttpResponse(NOT_FOUND, mapper.writeValueAsString(error),
                        Collections.singletonList("Content-Type: application/json; charset=utf-8"));
            }
        } catch (JsonProcessingException e) {
            response = new HttpResponse(INTERNAL_SERVER_ERROR,
                    "<h1>Internal server error</h1>",
                    Collections.singletonList("Content-Type: text/html; charset=utf-8"));
        }

        sendResponse(response);
    }

    @Override
    public void sendResponse(HttpResponse response) {
        try {
            OutputStream os = client.getOutputStream();
            os.write(("HTTP/1.1 \r\n" + response.status().response()).getBytes());
            for (String header : response.headers()) {
                os.write(header.concat("\r\n").getBytes());
            }
            os.write("\r\n".getBytes());
            os.write(response.body().getBytes());
            os.write("\r\n\r\n".getBytes());
            os.flush();
            client.close();
        } catch (IOException e) {
            throw new HttpServerException("An error occur trying to send a message");
        }
    }

    private Path filePath(String requestPath) {
        requestPath = requestPath.equals("/")
                ? "/index.html"
                : requestPath;

        return Paths.get(properties.resourcesPath(), requestPath);
    }
}
