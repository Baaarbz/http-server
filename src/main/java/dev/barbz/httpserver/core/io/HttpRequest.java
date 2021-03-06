package dev.barbz.httpserver.core.io;

import dev.barbz.httpserver.core.common.HttpMethod;

import java.util.List;

public record HttpRequest(HttpMethod method, String path, List<String> headers) {
}
