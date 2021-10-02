package dev.barbz.httpserver.configuration;

public record HttpServerProperties(int port, int threads, String resourcesPath) {
}
