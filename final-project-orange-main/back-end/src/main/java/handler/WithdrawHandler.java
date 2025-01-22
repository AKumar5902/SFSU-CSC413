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

public class WithdrawHandler implements BaseHandler {
    // TODO: complete

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        // pre
        var result = new HttpResponseBuilder()
                .setStatus(StatusCodes.SERVER_ERROR);

        var authResult = AuthFilter.doFilter(request);

        if (!authResult.isLoggedIn)
            return result.setBody(
                RestApiAppResponse.ofError("Attempting to withdraw without logging in.")
            );

        TransactionDto withdraw = GsonTool.GSON
                .fromJson(request.getBody(), TransactionDto.class);

        Double amount = withdraw.getAmount();

        if (amount == null)
            return result.setBody(
                RestApiAppResponse.ofError("Attempting to withdraw null amount.")
            );

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

        UserDto user = users.getFirst();

        double newBalance = user.getBalance() - amount;

        if (newBalance < 0)
            return result.setBody(
                RestApiAppResponse.ofError("Attempting to withdraw debt.")
            );

        // main

        user.setBalance(newBalance);

        UserDao.getInstance().put(user);

        withdraw.setUserId(userName);
        withdraw.setTransactionType(TransactionType.Withdraw);

        TransactionDao.getInstance().put(withdraw);

        var resultList = new ArrayList<TransactionDto>(1);
        resultList.add(withdraw);

        return result.setStatus(StatusCodes.OK)
            .setBody(
                RestApiAppResponse.ofSuccess(resultList)
            );
    }
}