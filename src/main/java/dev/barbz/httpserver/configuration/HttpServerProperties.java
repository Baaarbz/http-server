package dev.barbz.httpserver.configuration;

public record HttpServerProperties(Integer port, Integer threads, String resourcesPath, Boolean securityEnabled) {
}
