package handler;

import dao.AuthDao;
import dao.UserDao;
import dto.AuthDto;
import dto.UserDto;
import org.apache.commons.codec.digest.DigestUtils;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

import java.util.List;

class LoginDto {
    String userName;
    String password;
}

public class LoginHandler implements BaseHandler {

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        LoginDto userDto = GsonTool.GSON.fromJson(request.getBody(), LoginDto.class);
        UserDao userDao=UserDao.getInstance();
        UserDto userDto1=new UserDto();
        userDto1.setUserName(userDto.userName);
        userDto1.setPassword(DigestUtils.sha256Hex(userDto.password));
        userDao.put(userDto1);
        List<UserDto> list = userDao.query(userDto1.toDocument());
        System.out.println(list.size());
        AuthDao authDao= AuthDao.getInstance();
        AuthDto authDto = new AuthDto();
        authDto.setUserName(userDto.userName);
        authDto.setHash(DigestUtils.sha256Hex(userDto.password));
        authDao.put(authDto);




//        if(existingUsers.isEmpty()){
//            var res =new RestApiAppResponse<>(false, existingUsers, "User doesn't exist");
//            System.out.println(res.message);
//            return new HttpResponseBuilder().setStatus(StatusCodes.UNAUTHORIZED)
//                    .setBody(res);
//        }
//        if(existingUsers.get(0).getPassword()!= userDto.password){
//            var res = new RestApiAppResponse<>(false, List.of(), "Password doesn't match");
//            return new HttpResponseBuilder().setStatus(StatusCodes.OK)
//                    .setBody(res);
//        }




        var res = new RestApiAppResponse<>(true, List.of(), "Login successful");

        return new HttpResponseBuilder().setStatus(StatusCodes.OK).setBody(res)
                .setHeader("Set-Cookie", authDto.getHash());
    }
}
