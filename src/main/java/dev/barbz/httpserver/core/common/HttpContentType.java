package dev.barbz.httpserver.core.common;

public enum HttpContentType {
    CSS("text/css", "css"),
    TEXT("text/plain", "txt"),
    HTML("text/html", "html"),
    JAVASCRIPT("application/javascript", "js"),
    JSON("application/json", "json");

    public String header() {
        return "Content-Type: "
                .concat(mimeType)
                .concat("; charset=utf-8");
    }

    private final String mimeType;

    private final String extension;

    HttpContentType(String mimeType, String extension) {
        this.mimeType = mimeType;
        this.extension = extension;
    }

    /**
     * Retrieve the HTTP Content Type enum
     * from the MIME Type
     *
     * @param mimeType status value (text/html)
     * @return HTTP Content Type header
     */
    public static HttpContentType contentTypeOfMimeType(String mimeType) {
        for (HttpContentType httpContentType : values()) {
            if (httpContentType.mimeType.equals(mimeType)) {
                return httpContentType;
            }
        }
        return TEXT;
    }

    /**
     * Retrieve the HTTP Content Type enum
     * from the file extension
     *
     * @param extension file extension (js)
     * @return HTTP Content Type header
     */
    public static HttpContentType contentTypeOfExtension(String extension) {
        for (HttpContentType httpContentType : values()) {
            if (httpContentType.extension.equals(extension)) {
                return httpContentType;
            }
        }
        return TEXT;
    }
}
