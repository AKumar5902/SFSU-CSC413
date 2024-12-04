package request;


public class CustomParser {

    // extract java useable values from a raw http request string
    // https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages
    public static ParsedRequest parse(String request) {
        System.out.println("request is': " + request);
        String[] lines = request.split("\n");


        /* for debugging
        System.out.println("Printing all the request info");
        for(int i=0; i<lines.length; i++){
            System.out.println("i=" + i);
            System.out.println(lines[i]);
        }
        System.out.println("Printing lines length:" + lines.length);
         */
        String[] requestLineParts = lines[0].split(" ");

        String method = requestLineParts[0];

        String queries = requestLineParts[1];

        //String version = requestLineParts[2];

        ParsedRequest parsedRequest = new ParsedRequest();

        parsedRequest.setMethod(method);

        String[] pathParts = queries.split("\\?");

        parsedRequest.setPath(pathParts[0]);

        if (pathParts.length > 1) {
            String queryString = pathParts[1];
            String[] queryParams = queryString.split("&");
            for (String param : queryParams) {
                String[] keyValue = param.split("=", 2);
                if (keyValue.length == 2) {
                    parsedRequest.setQueryParam(keyValue[0], keyValue[1]);
                }
            }
        }


        int i = 1;
        while (i < lines.length && !lines[i].isEmpty()) {
            String[] headerParts = lines[i].split(": ", 2);
            if (headerParts.length == 2) {
                parsedRequest.setHeaderMap(headerParts[0], headerParts[1]);
            }
            i++;
        }

        StringBuilder bodyBuilder = new StringBuilder();
        for (int j = i; j < lines.length; j++) {
            bodyBuilder.append(lines[j]).append("\n");
        }
        parsedRequest.setBody(bodyBuilder.toString().trim());

        return parsedRequest;

    }
}
