package forgedhallpass.httpcomponents.burp.plugin.helper.request.model;

import forgedhallpass.httpcomponents.parser.model.common.HttpMethod;
import forgedhallpass.httpcomponents.parser.model.common.HttpProtocol;
import forgedhallpass.httpcomponents.parser.model.request.HttpRequest;
import forgedhallpass.httpcomponents.parser.model.request.HttpRequestHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BurpHttpRequest extends HttpRequest {

    private List<int[]> payloadInsertionPoints;

    public BurpHttpRequest(final HttpMethod httpMethod, final String path, final HttpRequestHeaders headers) {
        super(httpMethod, path, headers);
    }

    public BurpHttpRequest(final HttpMethod httpMethod, final String path, final HttpProtocol httpProtocol, final HttpRequestHeaders headers) {
        super(httpMethod, path, httpProtocol, headers);
    }

    public BurpHttpRequest(final HttpMethod httpMethod, final String path, final HttpRequestHeaders headers, final Optional<String> body) {
        super(httpMethod, path, headers, body);
    }

    public BurpHttpRequest(final HttpMethod httpMethod, final String path, final HttpProtocol httpProtocol, final HttpRequestHeaders headers, final Optional<String> body) {
        super(httpMethod, path, httpProtocol, headers, body);
    }

    public List<int[]> getPayloadInsertionPoints() {
        return payloadInsertionPoints;
    }

    public void setPayloadInsertionPoints(final List<int[]> payloadInsertionPoints) {
        this.payloadInsertionPoints = new ArrayList<>(payloadInsertionPoints);
    }
}
