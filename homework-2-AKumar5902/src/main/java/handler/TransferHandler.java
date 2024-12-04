package handler;

import dao.TransactionDao;
import dao.UserDao;
import dto.TransactionDto;
import dto.TransactionType;
import dto.TransferRequestDto;

import dto.UserDto;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

import java.util.List;

public class TransferHandler implements BaseHandler {

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        TransferRequestDto transferRequestDto = GsonTool.GSON.fromJson(request.getBody(),
                TransferRequestDto.class);

        Document filter = new Document("userName", transferRequestDto.fromId);
        List<UserDto> fromIdList = UserDao.getInstance().query(filter);
        if (fromIdList == null) {
            var res = new RestApiAppResponse<>(false, List.of(), "Invalid from id");
            return new HttpResponseBuilder()
                    .setStatus(StatusCodes.SERVER_ERROR)
                    .setBody(res);
        }
        Document filterTwo = new Document("userName", transferRequestDto.toId);
        List<UserDto> toIdList = UserDao.getInstance().query(filterTwo);
        if (toIdList == null) {
            var res = new RestApiAppResponse<>(false, List.of(), "Invalid to id");
            return new HttpResponseBuilder()
                    .setStatus(StatusCodes.SERVER_ERROR)
                    .setBody(res);
        }
        if (toIdList.get(0).getBalance() < transferRequestDto.amount) {
            var res = new RestApiAppResponse<>(false, null, "Not enough funds.");
            return new HttpResponseBuilder()
                    .setStatus(StatusCodes.SERVER_ERROR)
                    .setBody(res);
        }
        var transactionQuery = new Document().append("amount", transferRequestDto.amount)
                .append("transactionType", TransactionType.Transfer)
                .append("fromId", fromIdList.get(0).getUserName())
                .append("toId", transferRequestDto.toId);

        UserDto user = toIdList.get(0);
        user.setUserName(fromIdList.get(0).getUserName());
        TransactionDto transaction = new TransactionDto();
        transaction.setUserId(fromIdList.get(0).getUserName());
        transaction.setTransactionType(TransactionType.Transfer);
        transaction.setToId(transferRequestDto.toId);
        transaction.setAmount(transferRequestDto.amount);
        TransactionDao.getInstance().put(transaction);

        System.out.println(transactionQuery);


        UserDao.getInstance().put(user);

        var res = new RestApiAppResponse<>(true, List.of(), "Transfer successful");

        return new HttpResponseBuilder().setStatus(StatusCodes.OK).setBody(res);


    }
}