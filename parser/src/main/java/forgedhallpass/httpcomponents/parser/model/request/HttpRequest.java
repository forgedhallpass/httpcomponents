package forgedhallpass.httpcomponents.parser.model.request;

import forgedhallpass.httpcomponents.parser.Constants;
import forgedhallpass.httpcomponents.parser.model.common.HttpHeaders;
import forgedhallpass.httpcomponents.parser.model.common.HttpMethod;
import forgedhallpass.httpcomponents.parser.model.common.HttpProtocol;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Optional;

public class HttpRequest {
    private final HttpMethod httpMethod;
    private final String path;
    private final HttpProtocol httpProtocol;
    private final HttpRequestHeaders headers;
    private Optional<String> body;

    public HttpRequest(final HttpMethod httpMethod, final String path, final HttpRequestHeaders headers) {
        this(httpMethod, path, HttpProtocol.HTTP_1_1, headers, Optional.empty());
    }

    public HttpRequest(final HttpMethod httpMethod, final String path, final HttpProtocol httpProtocol, final HttpRequestHeaders headers) {
        this(httpMethod, path, httpProtocol, headers, Optional.empty());
    }

    public HttpRequest(final HttpMethod httpMethod, final String path, final HttpRequestHeaders headers, final Optional<String> body) {
        this(httpMethod, path, HttpProtocol.HTTP_1_1, headers, body);
    }

    public HttpRequest(HttpMethod httpMethod, final String path, final HttpProtocol httpProtocol, final HttpRequestHeaders headers, final Optional<String> body) {
        validateRequest(httpMethod, headers, body);

        this.httpMethod = httpMethod;
        this.path = path;
        this.httpProtocol = httpProtocol;
        this.headers = headers;
        this.body = body;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public HttpProtocol getHttpProtocol() {
        return httpProtocol;
    }

    public HttpRequestHeaders getHeaders() {
        return headers;
    }

    public Optional<String> getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = Optional.of(body);
    }

    @Override
    public String toString() {
        return getHttpMethod().toString() + ' ' + getPath() + ' ' + getHttpProtocol() + Constants.CRLF +
               getHeaders() +
               Constants.CRLF +
               this.getBody().orElse(StringUtils.EMPTY);
    }

    /**
     * in HTTP 1.1 protocol
     * 1. request line HTTP method /path HTTP/1.1
     * methods: (every web server must implement at least GET and HEAD)
     * GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, CONNECT, PATCH
     * 2. request headers (only HOST is mandatory)
     * 3. <CR><LF> (no other whitespaces)
     * 4. request body (no other new line characters are allowed. should be url encoded)
     */
    private void validateRequest(final HttpMethod httpMethod, final HttpHeaders headers, final Optional<String> body) {
        switch (httpMethod) {
            case POST:
            case PUT:
            case PATCH:
                if (!body.isPresent()) {
                    System.err.println("WARN - Based on the specification this HTTP method should have an request body");
                }
                break;
            case TRACE:
                if (body.isPresent()) {
                    System.out.printf("WARN - '%s' is not expected to have a request body%n", HttpMethod.TRACE);
                }
                break;
            case GET:
            case HEAD:
            case DELETE:
            case OPTIONS:
            case CONNECT:
            default:
                break;
        }

        if (headers.getHeaders().size() < 1 && Objects.isNull(headers.getHeader("Host"))) {
            System.err.println("WARN - The HOST header is mandatory");
        }
    }
}
