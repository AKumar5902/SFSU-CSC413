package handler;

import dao.AuthDao;
//import dto.AuthDto;
//import org.bson.BsonDocument;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

public class MiscHandlers {
    /**
     * Handler responsible for logging out
     */
    public static class LogOutHandler implements BaseHandler{
        @Override
        public HttpResponseBuilder handleRequest(ParsedRequest request) {
            // pre
            var result = new HttpResponseBuilder()
                .setStatus(StatusCodes.NOT_FOUND);

            var auth = AuthFilter.doFilter(request);

            if (!auth.isLoggedIn) {
                return result.setBody(
                    RestApiAppResponse.ofError("Attempting to log out of no user.")
                );
            }

            // main
            AuthDao.getInstance()
                .delete(auth.authDto);

            return result.setStatus(StatusCodes.OK)
                .setBody(RestApiAppResponse.ofSuccess(null));
        }
    }


}