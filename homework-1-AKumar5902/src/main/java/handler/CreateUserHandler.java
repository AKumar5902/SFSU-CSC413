package handler;

import dao.UserDao;
import dto.UserDto;
import java.util.UUID;

import request.ParsedRequest;
import response.ResponseBuilder;
import response.RestApiAppResponse;

public class CreateUserHandler implements BaseHandler {

    @Override
    public ResponseBuilder handleRequest(ParsedRequest request) {
        // TODO
        UserDto userDto = GsonTool.GSON.fromJson(request.getBody(), UserDto.class);
        String userName =userDto.getUserName();
        if(userName == null){
            var res = new RestApiAppResponse<>(false, null, "Invalid User Name");
            return new ResponseBuilder().setStatus("400 Bad Request").setBody(res.toString());
        }

        UserDto newUser = new UserDto();
        newUser.setUniqueId(UUID.randomUUID().toString());
        newUser.setUserName(userName);
        newUser.setBalance(0d);
        UserDao.getInstance().put(newUser);
        var res = new RestApiAppResponse<>(true,UserDao.getInstance().getAll(), "User created");


        return new ResponseBuilder().setStatus("200 OK").setBody(res.toString());
    }
}
