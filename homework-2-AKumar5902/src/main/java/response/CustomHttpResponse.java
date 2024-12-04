package response;

import java.util.Map;
import java.util.Map.Entry;

public class CustomHttpResponse {
    public final Map<String, String> headers;
    public final String status;
    public final String version;
    public final String body;

    public CustomHttpResponse(Map<String, String> headers, String status, String version,
                              String body) {
        this.headers = headers;
        this.status = status;
        this.version = version;
        this.body = body;
    }

    // TODO fill this out
    public String toString() {
        StringBuilder httpResponse = new StringBuilder();
        httpResponse.append(version).append(" ").append(status).append("\n");

        for (Map.Entry<String, String> header : headers.entrySet()) {
            httpResponse.append(header.getKey()).append(": ")
                    .append(header.getValue()).append("\n");
        }
        if(body !=null) {
            httpResponse.append("\n").append(body);
        }
        return httpResponse.toString();
    }
}
