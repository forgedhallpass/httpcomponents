package forgedhallpass.httpcomponents.parser.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public final class RequestUtil {

    private RequestUtil() {}

    public static Map<String, String> createRequestParameterMap(final String requestBody) {
        return createRequestParameterMap(requestBody, x -> x);
    }

    public static Map<String, String> createRequestParameterMap(final String requestBody, final UnaryOperator<String> valueTransformer) {
        final Map<String, String> result = new HashMap<>();
        for (final String values : requestBody.split("&")) {
            final String[] keyValues = values.split("=");
            final String key = keyValues[0];
            if (keyValues.length == 1) {
                result.put(key, "");
            } else if (keyValues.length == 2) {
                result.put(key, valueTransformer.apply(keyValues[1]));
            } else {
                throw new IllegalStateException("Something went wrong with this request: " + requestBody);
            }
        }
        return result;
    }
}
