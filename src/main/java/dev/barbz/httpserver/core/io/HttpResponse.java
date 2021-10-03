package dev.barbz.httpserver.core.io;

import java.util.List;

public record HttpResponse(Integer status, String body, List<String> headers) {
}
