package dev.barbz.httpserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HttpServerException extends RuntimeException {

    private String message;

}
