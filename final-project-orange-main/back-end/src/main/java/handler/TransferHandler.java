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

public class TransferHandler implements BaseHandler {
    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        var result = new HttpResponseBuilder()
            .setStatus(StatusCodes.SERVER_ERROR);

        // pre
        var authResult = AuthFilter.doFilter(request);

        if (!authResult.isLoggedIn) {
            return result.setBody(
                    RestApiAppResponse.ofError("Attempting to transfer without logging in.")
            );
        }

        var transfer = GsonTool.GSON
            .fromJson(request.getBody(), TransactionDto.class);

        double amount = transfer.getAmount();
        String userToName = transfer.getToId();
        String userFromName = authResult.userName;

        if (userFromName == null) {
            return result.setBody(
                RestApiAppResponse.ofError("Attempting to transfer with a null fromId.")
            );
        }
        else if (userToName == null) {
            return result.setBody(
                RestApiAppResponse.ofError("Attempting to transfer to a null toId.")
            );
        }

        var usersFromQuery = UserDao.getInstance()
            .query(Filters.eq("userName", userFromName));
        var usersToQuery = UserDao.getInstance()
            .query(Filters.eq("userName", userToName));

        if (usersFromQuery.isEmpty()) {
            return result.setBody(
                RestApiAppResponse.ofError(
                    "Attempting to transfer money from a user that doesn't exist"
                )
            );
        }
        else if (usersToQuery.isEmpty()) {
            return result.setBody(
                RestApiAppResponse.ofError(
                    "Attempting to transfer money to a user that doesn't exist"
                )
            );
        }

        UserDto userFrom = usersFromQuery.getFirst();

        double newBalance = userFrom.getBalance() - amount;

        if (newBalance < 0)
            return result.setBody(
                RestApiAppResponse.ofError("Attempting to transfer debt.")
            );

        // main
        userFrom.setBalance(newBalance);

        UserDto userTo = usersToQuery.getFirst();
        userTo.setBalance(userTo.getBalance() + amount);

        UserDao.getInstance().put(userFrom);
        UserDao.getInstance().put(userTo);

        transfer.setUserId(userFromName);
        transfer.setTransactionType(TransactionType.Transfer);

        TransactionDao.getInstance().put(transfer);

        var resultList = new ArrayList<TransactionDto>(1);
        resultList.add(transfer);

        return result.setStatus(StatusCodes.OK)
            .setBody(
                RestApiAppResponse.ofSuccess(resultList)
            );
    }

}