package handler;



import dao.UserDao;
import dto.UserDto;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;
import java.util.List;

public class CreateUserHandler implements BaseHandler {

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        UserDto userDto = GsonTool.GSON.fromJson(request.getBody(), UserDto.class);
        String userName =userDto.getUserName();
        if(userName == null | userName.isEmpty()){
            var res = new RestApiAppResponse<>(false, List.of(), "Invalid User Name");
            return new HttpResponseBuilder().setStatus(StatusCodes.SERVER_ERROR)
                    .setBody(res);
        }
        Document filter = new Document("userName", userName);
        List<UserDto> existingUsers = UserDao.getInstance().query(filter);
        System.out.println(existingUsers.size());

        if(existingUsers.size()>=1){
            var res =new RestApiAppResponse<>(false, existingUsers, "Username already taken");
            System.out.println(res.message);
            return new HttpResponseBuilder().setStatus(StatusCodes.OK)
                    .setBody(res);
        }

        UserDto newUser = new UserDto();

//        if (userDto.getUniqueId() == null) {
//            newUser.setUniqueId(UUID.randomUUID().toString());
//        } else {
//            newUser.setUniqueId(userDto.getUniqueId());
//        }
        newUser.setUserName(userName);
        newUser.setPassword(DigestUtils.sha256Hex(userDto.getPassword()));
        newUser.setBalance(0d);
        newUser.toDocument();

        var userQuery = new Document().append("userName", userName)
                .append("password", DigestUtils.sha256Hex(userDto.getPassword()))
                .append("balance",0).append("uniqueId",newUser.getUniqueId());
        System.out.println(userQuery);
        UserDao.getInstance().put(newUser);


        var res = new RestApiAppResponse<>(true, List.of(), "User created");
        System.out.println(res.message);


        return new HttpResponseBuilder().setStatus(StatusCodes.OK).setBody(res);
    }
}
