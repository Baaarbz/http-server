package dev.barbz.httpserver.server;

import java.util.List;

public record HttpResponse(int status, String content, List<String> headers) {
}
