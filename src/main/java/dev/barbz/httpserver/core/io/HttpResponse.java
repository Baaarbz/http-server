package dev.barbz.httpserver.core.io;

import dev.barbz.httpserver.core.util.HttpStatus;

public record HttpResponse(HttpStatus status, byte[] body, String... headers) {
}
