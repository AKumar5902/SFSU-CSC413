package handler;

import dao.ContactDao;
import dao.UserDao;
import dto.ContactDto;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

import java.util.List;

public class AddContactHandler implements BaseHandler{

	@Override
	public HttpResponseBuilder handleRequest(ParsedRequest request) {
		var result = new HttpResponseBuilder()
				.setStatus(StatusCodes.UNAUTHORIZED);
		// pre
		var authCheck = AuthFilter.doFilter(request);
		if (!authCheck.isLoggedIn)
			return result.setBody(RestApiAppResponse.ofError("Attempting to log in" +
					" as a non-user."));

		var contactDto = GsonTool.GSON.fromJson(request.getBody(), ContactDto.class);
		if (contactDto.getDisplayName() == null)
			return result.setBody(RestApiAppResponse.ofError("Attempting to send i" +
					"nvalid body data."));

		// main
		var owner = UserDao.getInstance()
				.query(new Document("userName", authCheck.userName))
				.getFirst();

		contactDto.setOwner(owner.getUniqueId());
		ContactDao.getInstance().put(contactDto);

		return result.setStatus(StatusCodes.OK)
				.setBody(
						RestApiAppResponse.ofSuccess(List.of(contactDto))
				);
	}
	
}
