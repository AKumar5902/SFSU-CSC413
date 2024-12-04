package handler;

import dao.UserDao;
import dto.TransferRequestDto;
import dto.UserDto;
import request.ParsedRequest;
import response.ResponseBuilder;
import response.RestApiAppResponse;

import java.util.List;


public class TransferHandler implements BaseHandler {

    @Override
    public ResponseBuilder handleRequest(ParsedRequest request) {
        // TODO
        TransferRequestDto transferRequestDto =
                GsonTool.GSON.fromJson(request.getBody(), TransferRequestDto.class);
        //System.out.println(transferRequestDto.fromId);

        //initialize users and check if they aren't null
        UserDto userA = UserDao.getInstance().get(transferRequestDto.fromId);
        UserDto userB = UserDao.getInstance().get(transferRequestDto.toId);
        if (userA == null) {
            var res = new RestApiAppResponse<>(false, null, "Invalid from user.");
            return new ResponseBuilder().setStatus("400 Bad Request").
                    setBody(GsonTool.GSON.toJson(res));

        }
        if (userB == null) {
            var res = new RestApiAppResponse<>(false, null, "Invalid user to transfer.");
            return new ResponseBuilder().setStatus("400 Bad Request")
                    .setBody(GsonTool.GSON.toJson(res));
        }
        //check if user has enough balance
        if (userA.getBalance() < transferRequestDto.amount) {
            var res = new RestApiAppResponse<>(false, null, "Not enough funds.");
            return new ResponseBuilder().setStatus("400 Bad Request")
                    .setBody(GsonTool.GSON.toJson(res));
        }

        //remove from userA and put into userB account
        userA.setBalance(userA.getBalance() - transferRequestDto.amount);
        userB.setBalance(userB.getBalance() + transferRequestDto.amount);


        // Save the updated state of both users
        UserDao.getInstance().put(userA);
        UserDao.getInstance().put(userB);

        var res = new RestApiAppResponse<>(true, List.of(), null);


        //Return a success response
        return new ResponseBuilder().setStatus("200 OK").setBody(GsonTool.GSON.toJson(res));

    }


}