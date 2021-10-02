package dev.barbz.httpserver.core;

import dev.barbz.httpserver.configuration.HttpServerProperties;
import dev.barbz.httpserver.core.io.HttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ListenerThread extends Thread {

    private final HttpServerProperties serverProperties;

    public ListenerThread(HttpServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(serverProperties.port())) {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                handleClient(socket);
            }
        } catch (IOException e) {
            log.error("Error setting up socket", e);
        }
    }

    /**
     * Handle client request, all the request received will read and
     * parsed.
     *
     * @param client socket client
     * @throws IOException thrown if an error occur trying to read the
     *                     received request.
     */
    private synchronized static void handleClient(Socket client) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        StringBuilder requestBuilder = new StringBuilder();

        // Request ends with one empty line (\r\n).
        // Client will send empty line, but inputStream will be still open,
        // we have to read it until one, empty line arrives.
        String line;
        while (!(line = br.readLine()).isBlank()) {
            requestBuilder.append(line)
                    .append("\r\n");
        }
        HttpRequest request = parseRequest(requestBuilder.toString());
    }

    /**
     * Parse the request, extracting the method, path, headers...
     *
     * @param request request as string
     */
    private synchronized static HttpRequest parseRequest(String request) {
        String[] requestLines = request.split("\r\n");
        // The request type (method, path and version) is contained in the first
        // line of the request.
        String[] requestType = requestLines[0].split(" ");
        String method = requestType[0];
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
