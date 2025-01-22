package handler;

// import com.mongodb.client.model.Filters;
import dao.AuthDao;
import dao.UserDao;
import dto.AuthDto;
import dto.UserDto;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class LoginDto {
    String userName;
    String password;
}

public class LoginHandler implements BaseHandler {
    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        HttpResponseBuilder result = new HttpResponseBuilder()
                .setStatus(StatusCodes.SERVER_ERROR);

        LoginDto loginDto = GsonTool.GSON
                .fromJson(request.getBody(), LoginDto.class);


        if (loginDto.userName == null) {
            return result.setBody(
                    RestApiAppResponse.ofError("Missing state: body.userName")
            );
        } else if (loginDto.password == null) {
            return result.setBody(
                    RestApiAppResponse.ofError("Missing state: body.password")
            );
        }

        // check if user exist by checking the password and username
        List<UserDto> users = UserDao.getInstance()
            .query(
                // Filters.eq("userName", loginDto.userName)
                    new Document("userName", loginDto.userName)
                        .append("password", DigestUtils.sha256Hex(loginDto.password))
            );

        if (users.isEmpty()) {
            return result.setBody(
                RestApiAppResponse.ofError("Attempting to log in as a non existant user")
            );
        }

        UserDto user = users.getFirst();

        // password
        /*
        if (!user.getPassword().equals(DigestUtils.sha256Hex(loginDto.password))) {
            return result.setStatus(StatusCodes.UNAUTHORIZED)
                    .setBody(
                            RestApiAppResponse.ofError("Wrong password")
                    );
        }
        */

        // Main:

        var resultList = new ArrayList<UserDto>(1);

        resultList.add(user);

        // necessary if user is logged in
        /* probably a test issue, apparently not supposed to use mock object?
        var auths = AuthDao.getInstance().query(
            // Filters.eq("userName",loginDto.userName)
            new Document("userName", loginDto.userName)
        );
        */

        AuthDto auth = //auths.isEmpty() ?
                new AuthDto() //:
                //auths.getFirst()
            ;
        auth.setUserName(loginDto.userName);
        auth.setExpireTime(
            Instant.now()
                .plusSeconds(86400)
                .toEpochMilli()
        );

        String hash = auth.getHash() == null
            ? AuthDto.getCookie(loginDto.userName)
            : auth.getHash();

        auth.setHash(hash);

        AuthDao.getInstance().put(auth);

        return result.setStatus(StatusCodes.OK)
            .setHeader("Set-Cookie", "auth=".concat(hash))
            .setBody(RestApiAppResponse.ofSuccess(resultList));
    }

}