package dev.barbz.httpserver.core;

import dev.barbz.httpserver.configuration.HttpServerProperties;
import dev.barbz.httpserver.core.io.HttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static dev.barbz.httpserver.core.util.HttpUtil.parseRequest;
import static dev.barbz.httpserver.core.util.HttpUtil.processRequest;

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
                Socket client = serverSocket.accept();
                handleRequest(client);
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
    private synchronized void handleRequest(Socket client) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        StringBuilder requestBuilder = new StringBuilder();

        // Request ends with one empty line (\r\n).
        // Client will send empty line, but inputStream will be still open,
        // we have to read it until one, empty line arrives.
        String line;

        while ((line = br.readLine()) != null && !line.isEmpty()) {
            requestBuilder.append(line)
                    .append("\r\n");
        }
        if (!requestBuilder.isEmpty()) {
            HttpRequest request = parseRequest(requestBuilder.toString());
            processRequest(request, serverProperties, client);
        }
    }
}
