package forgedhallpass.httpcomponents.burp.plugin.helper.request.model;

import forgedhallpass.httpcomponents.parser.model.request.HttpRequestHeaders;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.Objects;

public class BurpHttpRequestHeaders {

    private HttpRequestHeaders httpRequestHeaders;

    public BurpHttpRequestHeaders(final HttpRequestHeaders httpRequestHeaders) {
        this.httpRequestHeaders = httpRequestHeaders;
    }

    private int setHeader(final String headerName, final String value) {
        final String headerValue = httpRequestHeaders.getHeader(headerName);

        httpRequestHeaders.setHeader(headerName, value);
        final int headerPrefixLength = headerName.length() + ": ".length();
        final int newValueLength = value.length();

        int result = headerPrefixLength;
        if (Objects.isNull(headerValue)) {
            result +=  newValueLength;
        } else {
            final int currentValueLength = headerValue.length();
            final int mod = newValueLength - currentValueLength;
            result += newValueLength > currentValueLength ? mod : -mod;
        }
        return result;
    }

    private void setHost(final String host) {
        this.setHost(host, 80);
    }

    private void setHost(final String host, final int port) {
        httpRequestHeaders.setHost(host, port);
    }

    private void setContentLength(final int contentLength) {
        this.setHeader(HttpRequestHeaders.CONTENT_LENGTH, String.valueOf(contentLength));
    }

    private Map<String, String> getHeaders() {
        return httpRequestHeaders.getHeaders();
    }

    private String getHeader(final String headerName) {
        return httpRequestHeaders.getHeader(headerName);
    }

    private String getHost() {
        return httpRequestHeaders.getHost();
    }

    private Pair<String, Integer> getHostAndPort() {
        return httpRequestHeaders.getHostAndPort();
    }

    private Map<String, String> getCookies() {
        return httpRequestHeaders.getCookies();
    }

    private Integer getContentLength() {
        return httpRequestHeaders.getContentLength();
    }


}
