package dev.barbz.httpserver.core.method;

import dev.barbz.httpserver.core.io.HttpRequest;
import dev.barbz.httpserver.core.io.HttpResponse;
import dev.barbz.httpserver.core.util.FileUtil;
import dev.barbz.httpserver.core.util.HttpContentType;
import dev.barbz.httpserver.core.util.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import static dev.barbz.httpserver.core.util.FileUtil.filePath;

public interface HttpHandler {

    void handle(HttpRequest request);

    default void sendResponse(HttpResponse response, Socket client) {
        try {
            OutputStream os = client.getOutputStream();
            os.write("HTTP/1.1 ".getBytes());
            os.write(response.status().response().getBytes());
            os.write("\r\n".getBytes());
            for (String header : response.headers()) {
                os.write(header.getBytes());
                os.write("\r\n".getBytes());
            }
            os.write("\r\n".getBytes());
            os.write(response.body());
            os.write("\r\n".getBytes());
            os.write("\r\n".getBytes());
            os.flush();
            client.close();
        } catch (IOException e) {
            send500Error(client);
        }
    }

    default void send500Error(Socket client) {
        try {
            byte[] file = FileUtil.retrieveFile(filePath("webapp/error/500-error.html"));
            HttpContentType contentType = FileUtil.retrieveContentType(filePath("webapp/error/500-error.html"));

            HttpResponse response = new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, file, contentType.header());

            sendResponse(response, client);
        } catch (IOException e) {
            System.out.printf("Can not read 500-error.html:\n%s\n", e.getMessage());
        }
    }
}
