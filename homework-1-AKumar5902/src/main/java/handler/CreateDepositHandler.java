package handler;

import dao.TransactionDao;
import dao.UserDao;
import dto.TransactionDto;
import dto.TransactionType;
import dto.UserDto;
import request.ParsedRequest;
import response.ResponseBuilder;
import response.RestApiAppResponse;

import java.util.UUID;


public class CreateDepositHandler implements BaseHandler {

    @Override
    public ResponseBuilder handleRequest(ParsedRequest request) {
        // TODO
        TransactionDto transactionDto =
                GsonTool.GSON.fromJson(request.getBody(), TransactionDto.class);
        System.out.println(transactionDto.getUniqueId());
        TransactionDto transactionDto1 = new TransactionDto(transactionDto.getUniqueId());
        if(transactionDto.getUniqueId()==null) {
            transactionDto1.setUniqueId(UUID.randomUUID().toString());
        }

        UserDto userA = UserDao.getInstance().get(transactionDto.getUserId());
        if (userA == null) {
            var res = new RestApiAppResponse<>(false, null, "Invalid User");
            return new ResponseBuilder()
                    .setStatus("400 Bad Request")
                    .setBody(GsonTool.GSON.toJson(res));
        }

        if (transactionDto.getAmount() < 0) {
            var res = new RestApiAppResponse<>(false, null,
                    "Amount being deposited must be greater than 0");
            return new ResponseBuilder()
                    .setStatus("400 Bad Request")
                    .setBody(GsonTool.GSON.toJson(res));
        }
        transactionDto1.setTransactionType(TransactionType.Deposit);
        transactionDto1.setTimestamp(transactionDto.getTimestamp());
        transactionDto1.setAmount(transactionDto.getAmount());
        userA.setBalance(userA.getBalance() + transactionDto.getAmount());

        TransactionDao.getInstance().put(transactionDto1);
        UserDao.getInstance().put(userA);

        var res = new RestApiAppResponse<>(true, UserDao.getInstance().getAll(),
                "Deposit successful");

        return new ResponseBuilder()
                .setStatus("200 OK")
                .setBody(GsonTool.GSON.toJson(res));
    }

}
