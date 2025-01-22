package response;

import java.util.Map;

/**
 * Object responsible for making a response string
 */
public class CustomHttpResponse {
    // STATES
    public static boolean useCarriageReturn = true;
    public final Map<String, String> headers;
    public final String status;
    public final String version;
    public final String body;

    // CONSTRUCTOR
    public CustomHttpResponse(
        Map<String, String> headers, 
        String status, 
        String version,
        String body) {
        this.headers = headers;
        this.status = status;
        this.version = version;
        this.body = body;
    }

    // METHODS
    public String toString() {
        String result = version.concat(" ")
            .concat(status)
            .concat(getLineBreak());

        boolean bodyNecessary = body != null;
        
        //Iterator<Map.Entry<String,String>> 
        var it = headers.entrySet().iterator();

        while (it.hasNext()){
            var entry = it.next();

            result = result.concat(entry.getKey())
                .concat(": ")
                .concat(entry.getValue());

            if (it.hasNext() || bodyNecessary)
                result = result.concat(getLineBreak());
        }

        if (!bodyNecessary) return result;

        return result
            .concat(getLineBreak())
            .concat(body);
    }

    public static String getLineBreak(){
        return useCarriageReturn ? "\r\n" : "\n";
    }
}