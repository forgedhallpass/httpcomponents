package forgedhallpass.httpcomponents.parser.utils;

import forgedhallpass.httpcomponents.parser.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public final class Codec {

    private Codec() {}

    public static String decode(final String value) {
        try {
            return URLDecoder.decode(value, Constants.ENCODING.name());
        } catch (final UnsupportedEncodingException e) {
            throw new IllegalStateException("Error while URL decoding the value: " + value, e);
        }
    }

    public static String encode(final String value) {
        try {
            return URLEncoder.encode(value, Constants.ENCODING.name());
        } catch (final UnsupportedEncodingException e) {
            throw new IllegalStateException("Error while URL encoding value: " + value, e);
        }
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

    private static Map<String, String> createRequestParameterMap(final String requestBody) {
        return createRequestParameterMap(requestBody, x -> x);
    }
}
