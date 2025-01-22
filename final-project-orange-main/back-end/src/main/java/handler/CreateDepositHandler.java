package handler;

import com.mongodb.client.model.Filters;
import dao.TransactionDao;
import dao.UserDao;
import dto.TransactionDto;
import dto.TransactionType;
import dto.UserDto;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

import java.util.ArrayList;
import java.util.List;

public class CreateDepositHandler implements BaseHandler {

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        // pre
        var result = new HttpResponseBuilder()
            .setStatus(StatusCodes.SERVER_ERROR);

        var authResult = AuthFilter.doFilter(request);

        if (!authResult.isLoggedIn) {
            return result.setBody(
                    RestApiAppResponse.ofError("Attempting to deposit without logging in.")
            );
        }

        TransactionDto deposit = GsonTool.GSON
            .fromJson(request.getBody(), TransactionDto.class);

        Double amount = deposit.getAmount();

        if (amount == null) {
            return result.setBody(
                    RestApiAppResponse.ofError("Attempting to deposit null amount.")
            );
        }

        String userName = authResult.userName;

        List<UserDto> users = UserDao.getInstance()
            .query(
                Filters.eq("userName", userName)
            );

        if (users.isEmpty())
            return result.setBody(
                RestApiAppResponse.ofError(
                    "Missing user: got .userName: " + userName
                )
            );


        // main
        UserDto user = users.getFirst();

        user.setBalance(user.getBalance() + amount);

        UserDao.getInstance().put(user);

        deposit.setUserId(userName);
        deposit.setTransactionType(TransactionType.Deposit);

        TransactionDao.getInstance().put(deposit);

        var resultList = new ArrayList<TransactionDto>(1);
        resultList.add(deposit);

        return result.setStatus(StatusCodes.OK)
            .setBody(
                RestApiAppResponse.ofSuccess(resultList)
            );
    }

}