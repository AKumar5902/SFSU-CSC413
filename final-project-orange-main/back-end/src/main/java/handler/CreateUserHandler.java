package handler;

import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;

//import com.mongodb.client.model.Filters;
import dao.UserDao;
import dto.UserDto;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

public class CreateUserHandler implements BaseHandler {
    
    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        HttpResponseBuilder result = new HttpResponseBuilder();
        UserDto userRequest = GsonTool
            .GSON
            .fromJson(request.getBody(), UserDto.class);
        
        result.setStatus(StatusCodes.SERVER_ERROR);

        /*
        if (!request.getMethod().equals("POST"))
            return result.setStatus(StatusCodes.UNAUTHORIZED)
                .setBody(
                    RestApiAppResponse.ofError(
                        "Bad Method: Expected POST, got: " + request.getMethod()
                    )
                );
        */

        String userName = userRequest.getUserName(),
            passwordNow = userRequest.getPassword();

        // System.out.println(userName + "|" + passwordNow);
        // check all states
        if (userName == null) {
            return result.setBody(
                RestApiAppResponse.ofError("Expected body.userName")
            );
        } else if (passwordNow == null) {
            return result.setBody(
                RestApiAppResponse.ofError("Expected body.password")
            );
        }

        // check presence
        if (!UserDao.getInstance()
            .query(
                new Document("userName", userName)
            ).isEmpty()) {
            return result
                .setStatus(StatusCodes.OK)
                .setBody(
                    RestApiAppResponse.ofError("Username already taken")
                );
        }
        
        // Main
        userRequest.setPassword(DigestUtils.sha256Hex(passwordNow));
        /// userRequest.setUniqueId(Instant.now().toString());
        System.out.println("met");
        UserDao.getInstance().put(userRequest);
        System.out.println("met");

        ArrayList<UserDto> resultList =
            new ArrayList<>();

        resultList.add(userRequest);

        return result.setStatus(StatusCodes.OK)
            .setBody(
                RestApiAppResponse.ofSuccess(resultList)
            );
    }
}