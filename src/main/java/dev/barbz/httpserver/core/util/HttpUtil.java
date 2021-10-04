package dev.barbz.httpserver.core.util;

import dev.barbz.httpserver.configuration.HttpServerProperties;
import dev.barbz.httpserver.core.exception.HttpServerException;
import dev.barbz.httpserver.core.io.HttpRequest;
import dev.barbz.httpserver.core.method.HttpGet;
import dev.barbz.httpserver.core.method.HttpHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class HttpUtil {

    public static void processRequest(HttpRequest request, HttpServerProperties properties, Socket client) {
        HttpHandler handler = switch (request.method()) {
            case GET -> new HttpGet(properties, client);
            default -> throw new HttpServerException("This HTTP Serve do not handle your "
                    .concat(request.method().name())
                    .concat(" request.\nAllowed request: GET, POST, PUT, DELETE"));
        };

        handler.handle(request);
    }

    /**
     * Parse the request, extracting the method, path, headers...
     *
     * @param request request as string
     */
    public synchronized static HttpRequest parseRequest(String request) {
        String[] requestLines = request.split("\r\n");
        // The request type (method, path and version) is contained in the first
        // line of the request.
        String[] requestType = requestLines[0].split(" ");
        HttpMethod method = HttpMethod.valueOf(requestType[0]);
        String path = requestType[1];

        // The header comes in the request after the 2 line, then we can read the headers
        // Now we only want to read the 'Accept' 'Content' or  'Authorization' header
        List<String> headers = Arrays.asList(requestLines).subList(2, requestLines.length).stream()
                .filter((String h) ->
                        (h.startsWith("Accept") || h.startsWith("Content") || h.startsWith("Authorization")))
                .collect(Collectors.toList());

        log.debug("Method: {}, Path: {}, Headers:", method, path);
        for (String header : headers) {
            log.debug("\t {}", header);
        }

        return new HttpRequest(method, path, headers);
    }
}
