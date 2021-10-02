package dev.barbz.httpserver.server;

import java.util.List;

public record HttpRequest(String method, String path, List<String> headers) {
}
