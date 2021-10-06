package dev.barbz.httpserver.core.io;

import dev.barbz.httpserver.core.util.HttpStatus;

import java.util.List;

public record HttpResponse(HttpStatus status, byte[] body, List<String> headers) {
}
