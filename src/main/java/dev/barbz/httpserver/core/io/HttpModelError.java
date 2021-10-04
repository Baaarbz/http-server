package dev.barbz.httpserver.core.io;

import dev.barbz.httpserver.core.util.HttpStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HttpModelError {
    private String shortMessage;
    private String detailedError;
    private HttpStatus status;
}
