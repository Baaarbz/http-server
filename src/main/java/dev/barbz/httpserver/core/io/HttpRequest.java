package dev.barbz.httpserver.core.io;

import java.util.List;

public record HttpRequest(String method, String path, List<String> headers) {
}
