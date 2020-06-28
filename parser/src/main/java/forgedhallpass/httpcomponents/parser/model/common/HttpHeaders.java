package forgedhallpass.httpcomponents.parser.model.common;

import forgedhallpass.httpcomponents.parser.Constants;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class HttpHeaders {

    private final Map<String, String> headers;

    public HttpHeaders(final String headers) {
        final String[] splitHeaders = headers.split(Constants.CRLF);
        this.headers = new LinkedHashMap<>(splitHeaders.length);

        for (final String header : splitHeaders) {
            final int separatorIndex = header.indexOf(":");
            final String headerName = header.substring(0, separatorIndex);
            final String headerValue = header.substring(separatorIndex + 1).trim();

            if (handleHeader(headerName, headerValue)) {
                continue;
            } else {
                this.headers.put(headerName, headerValue);
            }
        }
    }

    public String getHeader(final String headerName) {
        return headers.get(headerName);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeader(final String headerName, final String value) {
        this.headers.compute(headerName, (k, v) -> value);
    }

    @Override
    public String toString() {
        return this.headers.entrySet().stream()
                           .map(e -> e.getKey() + ": " + e.getValue() + Constants.CRLF)
                           .collect(Collectors.joining());
    }

    public abstract boolean handleHeader(final String headerName, final String headerValue);
}
