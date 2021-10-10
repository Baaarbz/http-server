package dev.barbz.httpserver.configuration;

public record HttpServerProperties(Server server, Security security, Datasource datasource) {

    public static record Server(Integer port, Integer threads, String resourcesPath) {
    }

    public static record Security(Boolean enabled, String defaultUser, String defaultPassword) {
    }

    public static record Datasource(String user, String password, String host, String port, String type,
                                    String database) {
    }
}
