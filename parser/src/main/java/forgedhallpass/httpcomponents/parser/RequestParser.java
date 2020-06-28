package forgedhallpass.httpcomponents.parser;

import forgedhallpass.httpcomponents.parser.model.common.HttpMethod;
import forgedhallpass.httpcomponents.parser.model.common.HttpProtocol;
import forgedhallpass.httpcomponents.parser.model.request.HttpRequest;
import forgedhallpass.httpcomponents.parser.model.request.HttpRequestHeaders;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestParser {

//    private static final String HTTP_METHODS_REGEX_SNIPPET = Stream.of(HttpMethod.values()).map(HttpMethod::name).collect(Collectors.joining("|"));
//    private static final Pattern HTTP_REQUEST_PATTERN = Pattern.compile("^(?<httpMethod>" + HTTP_METHODS_REGEX_SNIPPET + ") (?<path>/.*?) (?<protocol>HTTP/1\\.[01])?\r?\n(?<headers>[a-zA-Z]+: .*\r?\n)+\r?\n(?<requestBody>.*)$",
//                                                                        Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

    private static final Pattern HTTP_REQUEST_PATTERN = Pattern.compile("^(?<httpMethod>GET|HEAD|POST|PUT|DELETE|TRACE|OPTIONS|CONNECT|PATCH) (?<path>/.*?) (?<protocol>HTTP/1\\.[01])?\r?\n(?<headers>[a-zA-Z]+: .*\r?\n)+\r?\n(?<requestBody>.*)$",
                                                                        Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

    private RequestParser() {}

    public static HttpRequest parse(final String request) {
        final Matcher matcher = HTTP_REQUEST_PATTERN.matcher(request);

        if (matcher.matches()) {
            final String httpMethod = matcher.group("httpMethod");
            final String path = matcher.group("path");
            final String protocol = matcher.group("protocol");
            final String headers = matcher.group("headers");
            final String requestBody = matcher.group("requestBody");

            if (StringUtils.isAnyBlank(httpMethod, path, protocol, headers)) {
                throw new IllegalArgumentException("The request is not valid: \n" + request);
            }

            return new HttpRequest(HttpMethod.from(httpMethod),
                                   path,
                                   HttpProtocol.from(protocol),
                                   HttpRequestHeaders.from(headers),
                                   Optional.ofNullable(requestBody));
        } else {
            throw new IllegalArgumentException("The request is not valid: \n" + request);
        }
    }
}
