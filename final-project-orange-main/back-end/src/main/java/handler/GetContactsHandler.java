
package handler;

import dao.ContactDao;
import dao.UserDao;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

public class GetContactsHandler implements BaseHandler{

	@Override
	public HttpResponseBuilder handleRequest(ParsedRequest request) {
		var result = new HttpResponseBuilder()
				.setStatus(StatusCodes.UNAUTHORIZED);
		// pre
		var authCheck = AuthFilter.doFilter(request);
		if (!authCheck.isLoggedIn)
			return result.setBody(RestApiAppResponse.ofError("Attempting to log in" +
					" as a non-user."));

		// Main
		var user = UserDao.getInstance()
				.query(new Document("userName", authCheck.userName))
				.getFirst();
		var queriedList = ContactDao.getInstance()
				.query(new Document("owner",user.getUniqueId()));

		return result.setStatus(StatusCodes.OK)
				.setBody(RestApiAppResponse.ofSuccess(queriedList));
	}

}