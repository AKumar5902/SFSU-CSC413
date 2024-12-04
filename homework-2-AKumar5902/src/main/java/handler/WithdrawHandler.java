package handler;

import dao.TransactionDao;
import dao.UserDao;
import dto.TransactionDto;
import dto.TransactionType;
import dto.UserDto;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

import java.util.List;

public class WithdrawHandler implements BaseHandler {

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        TransactionDto transactionDto = GsonTool.GSON.fromJson(request.getBody(),
                TransactionDto.class);
        System.out.println(transactionDto.getUserId());
        System.out.println(transactionDto.getUniqueId());
        System.out.println(transactionDto.getAmount());
        System.out.println(transactionDto.getTransactionType());
        transactionDto.setTransactionType(TransactionType.Withdraw);

//        var transquery =TransactionDao.getInstance();
//        transquery.put(transactionDto);
        Document filter = new Document("userName", transactionDto.getUserId());
        List<UserDto> existingUsers = UserDao.getInstance().query(filter);
        if (existingUsers == null) {
            var res = new RestApiAppResponse<>(false, List.of(), "Invalid User");
            return new HttpResponseBuilder()
                    .setStatus(StatusCodes.SERVER_ERROR)
                    .setBody(res);
        }
        UserDto user = existingUsers.get(0);
        double currentBalance = user.getBalance();
        if (transactionDto.getAmount() < 0) {
            var res = new RestApiAppResponse<>(false, List.of(),
                    "Amount being deposited must be greater than 0");
            return new HttpResponseBuilder()
                    .setStatus(StatusCodes.SERVER_ERROR)
                    .setBody(res);
        }
        transactionDto.setUserId(existingUsers.get(0).getUserName());
        transactionDto.setTransactionType(TransactionType.Withdraw);
        transactionDto.setTimestamp(transactionDto.getTimestamp());
        transactionDto.setAmount(transactionDto.getAmount());

        var transactionQuery = new Document().append("amount", transactionDto.getAmount()
                        +currentBalance)
                .append("transactionType", TransactionType.Withdraw)
                .append("userId", existingUsers.get(0).getUserName())
                .append("timestamp", transactionDto.getTimestamp())
                .append("uniqueId", transactionDto.getUniqueId());

        System.out.println(transactionQuery);

        TransactionDao.getInstance().put(transactionDto);


        var res = new RestApiAppResponse<>(true, List.of(), "Withdrawal successful");

        return new HttpResponseBuilder().setStatus(StatusCodes.OK).setBody(res);
    }

}
