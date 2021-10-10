package dev.barbz.httpserver.infrastructure;

import dev.barbz.httpserver.configuration.HttpServerProperties;

public record DatabaseConfiguration(HttpServerProperties.Datasource datasource) {

    public void initConnection() {

    }
}
