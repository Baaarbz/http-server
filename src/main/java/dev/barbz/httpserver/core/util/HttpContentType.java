package dev.barbz.httpserver.core.util;

public enum HttpContentType {
    CSS("text/css") {},
    HTML("text/html"),
    JAVASCRIPT("application/javascript"),
    JSON("application/json");

    public String header() {
        return "Content-Type: "
                .concat(mimeType)
                .concat("; charset=utf-8");
    }

    private String mimeType;

    HttpContentType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Retrieve the HTTP Content Type enum
     * from the MIME Type
     *
     * @param mimeType status value (text/html)
     * @return HTTP Content Type header
     */
    public static HttpContentType statusOfMimeType(String mimeType) {
        for (HttpContentType httpContentType : values()) {
            if (httpContentType.mimeType.equals(mimeType)) {
                return httpContentType;
            }
        }
        return null;
    }
}
