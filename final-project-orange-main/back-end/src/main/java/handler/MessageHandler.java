package handler;

import dao.MessageDao;
import dao.UserDao;
import dto.MessageDto;
import dto.UserDto;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

public class MessageHandler implements BaseHandler {
    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        MessageDto messageDto = GsonTool.GSON.fromJson(request.getBody(), MessageDto.class);


        var authResult = AuthFilter.doFilter(request);

        if (!authResult.isLoggedIn) {
            return new HttpResponseBuilder().setStatus(StatusCodes.UNAUTHORIZED);
        }
        UserDao userDao = UserDao.getInstance();
        //  UserDto fromUser = userDao.query(new Document("userName", authResult.userName)).getFirst();
        UserDto toUser = userDao.query(new Document("userName", messageDto.toId)).getFirst();

        if (toUser == null) {
            var apiRes = new RestApiAppResponse<>(false, null, "Invalid user to message");
            return new HttpResponseBuilder().setStatus("400 Bad Request").setBody(apiRes);
        }

        if (messageDto.message == null || messageDto.getMessage().trim().isEmpty()) {
            var apiRes = new RestApiAppResponse<>(false, null, "Empty Message");
            return new HttpResponseBuilder().setStatus("400 Bad Request").setBody(apiRes);
        }


        messageDto.setToId(toUser.getUserName());
        messageDto.setMessage(messageDto.message);


        MessageDao messageDao = MessageDao.getInstance();
        messageDao.put(messageDto);


        var res = new RestApiAppResponse<>(true, null, "Message sent");
        return new HttpResponseBuilder().setBody(res).setStatus(StatusCodes.OK);
    }

}
