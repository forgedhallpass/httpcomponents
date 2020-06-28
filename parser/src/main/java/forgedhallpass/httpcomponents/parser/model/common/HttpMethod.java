package forgedhallpass.httpcomponents.parser.model.common;

import java.util.Arrays;

public enum HttpMethod {
    GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, CONNECT, PATCH;

    public static HttpMethod from(final String input) {
        return Arrays.stream(values())
                     .filter(value -> input.equalsIgnoreCase(value.toString()))
                     .findAny()
                     .orElseThrow(() -> new IllegalStateException("Unsupported HTTP method"));
    }
}