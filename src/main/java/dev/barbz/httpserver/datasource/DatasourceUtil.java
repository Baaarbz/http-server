package dev.barbz.httpserver.datasource;

import dev.barbz.httpserver.configuration.HttpServerProperties;

public class DatasourceUtil {

    public static String generateConnectionUrl(HttpServerProperties.Datasource properties) {
        return  "jdbc:"
                .concat(properties.type())
                .concat("://")
                .concat(properties.host())
                .concat(":")
                .concat(properties.port())
                .concat("/")
                .concat(properties.database());
    }
}
