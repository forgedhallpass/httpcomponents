package forgedhallpass.httpcomponents.parser.model.common;

import java.util.stream.Stream;

public enum HttpProtocol {
    HTTP_1_1("HTTP/1.1"), HTTP_1_0("HTTP/1.0");

    private final String value;
    HttpProtocol(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static HttpProtocol from(final String input) {
        return Stream.of(values())
                     .filter(value -> input.equalsIgnoreCase(value.getValue()))
                     .findAny()
                     .orElseThrow(() -> new IllegalArgumentException("Invalid HTTP protocol: " + input));
    }

    @Override
    public String toString() {
        return this.value;
    }
}
