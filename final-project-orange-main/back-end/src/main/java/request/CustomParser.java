package request;


import java.util.Arrays;

public class CustomParser {

    // extract java useable values from a raw http request string
    // https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages
    public static ParsedRequest parse(String request) {
        String[] lines = request.split("(\r\n|\r|\n)");
        String requestLine = lines[0];
        String[] requestParts = requestLine.split(" ");
        var result = new ParsedRequest();
        result.setMethod(requestParts[0]);

        var parts = requestParts[1].split("\\?");
        result.setPath(parts[0]);

        if (parts.length == 2) {
            // System.out.println(parts[1]);
            String[] queryParts = parts[1].split("&");
            for (String queryPart : queryParts) {
                String[] pair = queryPart.split("=");
                result.setQueryParam(pair[0], pair[1]);
            }
        }

        // HEADERS
        int i = 1;
        final int n = lines.length;

        for (; i < n; i++) {
            String line = lines[i];
            // System.out.println("i="+i+"|"+lines[i]+"|");
            if (line.isEmpty()) break;

            String[] headerPair = line.split(": ", 2);

            result.setHeaderValue(headerPair[0], headerPair[1]);

            // was cookie ever capitalized?
            if (!headerPair[0].equals("cookie")) continue;

            Arrays.stream(headerPair[1].split("; "))
                .forEach(s -> {
                    String[] pair = s.split("=");

                    result.setCookieValue(pair[0], pair[1]);
                });
        }

        // BODY
        if (i < n)
            result.setBody(
                CustomParser.stringConcat(lines, ++i)
            );


        return result;
    }

    /**
     * Genuinely don't know a way to do this without 
     * committing an error, so this approach is second 
     * best
     */
    public static String stringConcat(
        String[] array,
        int startAt
    ) {
        String result = "";
        final int n = array.length;

        for (; startAt < n; startAt++){
            result = result.concat(array[startAt]);

            if (startAt != n - 1) 
                result = result.concat("\n");

        }

        return result;
    }
}