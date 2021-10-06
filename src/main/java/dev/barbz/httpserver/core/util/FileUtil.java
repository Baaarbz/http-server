package dev.barbz.httpserver.core.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {

    public static byte[] retrieveFile(Path filePath) throws IOException {
        return Files.readAllBytes(filePath);
    }

    public static String guessMIMEType(Path filePath) throws IOException {
        return Files.probeContentType(filePath);
    }
}
