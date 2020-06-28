package forgedhallpass.httpcomponents.burp.plugin.helper;

public class HttpMessage {

    private final String value;
    private final String host;
    private final int port;
    private boolean isRequest;
    private final boolean isHttps;

    public HttpMessage(final String value, final String host, final int port, final boolean isRequest, final boolean isHttps) {
        this.value = value;
        this.host = host;
        this.port = port;
        this.isRequest = isRequest;
        this.isHttps = isHttps;
    }

    public boolean isRequest() {
        return isRequest;
    }

    public String getValue() {
        return value;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isHttps() {
        return isHttps;
    }
}
