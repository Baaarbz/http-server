package dev.barbz.httpserver.core.exception;

import dev.barbz.httpserver.core.util.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HttpServerException extends RuntimeException {

    private String shortMessage;
    private String detailedError;
    private HttpStatus status;

}
