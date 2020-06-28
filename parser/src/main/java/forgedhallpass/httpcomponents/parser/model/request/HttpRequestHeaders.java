package forgedhallpass.httpcomponents.parser.model.request;

import forgedhallpass.httpcomponents.parser.Constants;
import forgedhallpass.httpcomponents.parser.model.common.HttpHeaders;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestHeaders extends HttpHeaders {

    public static final String CONTENT_LENGTH = "Content-Length";
    static final String HOST = "Host";
    static final String USER_AGENT = "User-Agent";
    static final String CONTENT_TYPE = "Content-Type";
    private static final String COOKIE = "Cookie";
    private Pair<String, Integer> hostAndPort;
    private Map<String, String> cookies;

    private HttpRequestHeaders(final String headers) {
        super(headers);
    }

    public static HttpRequestHeaders from(final String headers) {
        return new HttpRequestHeaders(headers);
    }

    public String getHost() {
        return getHeader(HOST);
    }

    public void setHost(final String host) {
        setHost(host, 80);
    }

    public void setHost(final String host, final int port) {
        this.hostAndPort = Pair.of(host, port);
    }

    public Integer getContentLength() {
        return Integer.valueOf(getHeader(CONTENT_LENGTH));
    }

    public void setContentLength(final int contentLength) {
        setHeader(CONTENT_LENGTH, String.valueOf(contentLength));
    }

    public Pair<String, Integer> getHostAndPort() {
        return hostAndPort;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookie(final String key, final String value) {
        this.cookies.compute(key, (k, v) -> value);
    }

    @Override
    public String toString() {
        final String cookieHeaderValue = this.cookies.entrySet().stream().map(e -> e.getKey() + '=' + e.getValue()).collect(Collectors.joining("; "));
        return HOST + ": " + this.hostAndPort.getKey() + ':' + this.hostAndPort.getValue() + Constants.CRLF +
               super.toString() +
               COOKIE + ": " + cookieHeaderValue + Constants.CRLF;
    }

    @Override
    public boolean handleHeader(final String headerName, final String headerValue) {
        final boolean result;

        if (HOST.equalsIgnoreCase(headerName)) {
            handleHost(headerValue);
            result = true;
        } else if (COOKIE.equalsIgnoreCase(headerName)) {
            handleCookies(headerValue);
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    private void handleHost(final String headerValue) {
        if (headerValue.contains(":")) {
            final String[] hostPort = headerValue.split(":");
            this.hostAndPort = Pair.of(hostPort[0], Integer.valueOf(hostPort[1]));
        } else {
            this.hostAndPort = Pair.of(headerValue, 80);
        }
    }

    private void handleCookies(final String headerValue) {
        if (headerValue.contains(";")) {
            this.cookies = Arrays.stream(headerValue.split(";"))
                                 .map(this::getCookiePair)
                                 .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        } else {
            final Pair<String, String> cookiePair = getCookiePair(headerValue);
            cookies = new HashMap<>(Collections.singletonMap(cookiePair.getKey(), cookiePair.getValue()));
        }
    }

    private Pair<String, String> getCookiePair(final String cookieValue) {
        final String[] cookie = cookieValue.split("=");
        return Pair.of(cookie[0].trim(), cookie[1]);
    }
}
