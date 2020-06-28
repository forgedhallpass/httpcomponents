package forgedhallpass.httpcomponents.burp.plugin.helper.utils;

import java.util.ArrayList;
import java.util.List;

public final class HttpMessageUtils {

    private HttpMessageUtils() {}

    public static void calculatePositions(final StringBuilder requestBodyBuilder, final String payloadPositionMarker, final List<Integer> indexes) {
        for (int i = 0; i < requestBodyBuilder.length(); i++) {
            final char currentChar = requestBodyBuilder.charAt(i);
            if (currentChar == payloadPositionMarker.charAt(0)) {
                final int lookAheadIndex = i + payloadPositionMarker.length();
                if (lookAheadIndex <= requestBodyBuilder.length()) {
                    final String currentCharacters = requestBodyBuilder.substring(i, lookAheadIndex);
                    if (currentCharacters.equals(payloadPositionMarker)) {
                        indexes.add(i);
                        requestBodyBuilder.delete(i, lookAheadIndex); // delete the position marker in-place
                        --i; // test the same index again in the next iteration because there is new character in place of the deleted one
                    }
                }
            }
        }
    }

    public static List<int[]> toCoordinates(final List<Integer> indexes) {
        final int markersSize = indexes.size();
        final List<int[]> result = new ArrayList<>(markersSize / 2);
        if ((markersSize % 2) == 0) { // two indexes define a payload insertion point
            for (int i = 0; i < markersSize; i++) {
                result.add(new int[]{indexes.get(i), indexes.get(++i)});
            }
        } else {
            throw new IllegalStateException("Un-even number of marker characters");
        }
        return result;
    }
}
