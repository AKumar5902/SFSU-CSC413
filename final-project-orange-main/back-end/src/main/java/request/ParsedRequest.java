package request;

import java.util.HashMap;
import java.util.Map;

public class ParsedRequest {
    // STATES
    private String path;
    private Map<String, String> queryMap = new HashMap<>();
    private Map<String, String> headerMap = new HashMap<>();
    private Map<String, String> cookieMap = new HashMap<>();

    private String method;
    private String body;

    // METHODS
    public String getQueryParam(String key) {
        return queryMap.get(key);
    }

    public String getHeaderValue(String key) {
        return headerMap.get(key);
    }

    public void setQueryParam(String key, String value) {
        this.queryMap.put(key, value);
    }

    public void setHeaderValue(String key, String value) {
        this.headerMap.put(key, value);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {return path;}

    public String getMethod() {return method;}

    public String getBody() {return body;}

    public void setBody(String body) {
        this.body = body;
    }

    public void setCookieValue(String key, String value) {
        this.cookieMap.put(key, value);
    }

    public String getCookieValue(String key) {
        return cookieMap.get(key);
    }

    public String toString() {
        String result = (method == null ? "NO_METHOD" : method)
            .concat(" ")
            .concat(path);

        // do queries
        if (!queryMap.isEmpty()) {
            result = result.concat("?");
            result = queryMap.entrySet()
                .stream()
                .reduce(
                    result,
                    (s, entry) -> s.concat(entry.getKey())
                        .concat("=")
                        .concat(entry.getValue()),
                    (s, s2) -> s + "&" + s2);

            /*
            var it = queryMap.entrySet()
                .iterator();

            while(it.hasNext()){
                final var v = it.next();
                result = result.concat(v.getKey())
                    .concat("=")
                    .concat(v.getValue());

                if (it.hasNext())
                    result = result.concat("&");
            }
            //*/
        }

        result = result.concat(" ")
            .concat("HTTP/1.1"); // do this later, somehow

        // Headers
        result = headerMap.entrySet()
            .stream()
            .reduce(
                result,
                (s, entry) -> s.concat("\n")
                    .concat(entry.getKey())
                    .concat(": ")
                    .concat(entry.getValue()),
                (s, e) -> s + e
            );

        // cookies if applicable
        if (headerMap.get("Cookie") == null && !cookieMap.isEmpty()){
            result = result.concat("\nCookie: ");
            result = cookieMap.entrySet()
                .stream()
                .reduce(
                    result,
                    (s, entry) -> s.concat(entry.getKey())
                        .concat("=")
                        .concat(entry.getValue()),
                    (s, s2) -> s + "," + s2
                );
        }

        // Body
        if (body != null)
            result = result.concat("\n\n")
                .concat(body);

        return result;
    }
}