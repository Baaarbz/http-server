package dev.barbz.httpserver.core.util;

import dev.barbz.httpserver.core.common.HttpContentType;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    public static byte[] retrieveFile(Path filePath) throws IOException {
        return Files.readAllBytes(filePath);
    }

    public static HttpContentType retrieveContentType(Path filePath) throws IOException {
        String extension = FilenameUtils.getExtension(filePath.toString());
        return HttpContentType.contentTypeOfExtension(extension);
    }

    public static Path filePath(String requestPath, String resourcesPath) {
        if (requestPath.startsWith("/webapp")) {
            resourcesPath = System.getProperty("user.dir");
        } else {
            requestPath = requestPath.equals("/")
                    ? "/index.html"
                    : requestPath;
        }

        return Paths.get(resourcesPath, requestPath);
    }

    public static Path filePath(String filename) {
        return Paths.get(System.getProperty("user.dir").concat("/").concat(filename));
    }
}
