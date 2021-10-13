package dev.barbz.httpserver.configuration;

public record HttpServerProperties(Server server, Security security) {

    public static record Server(Integer port, Integer threads, String resourcesPath) {
    }

    public static record Security(Boolean enabled, String user, String password) {
    }
}
