package dev.barbz.httpserver.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HttpServerException extends RuntimeException {

    private String message;

}
