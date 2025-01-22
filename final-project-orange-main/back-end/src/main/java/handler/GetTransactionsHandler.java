package handler;

import com.mongodb.client.model.Filters;
import dao.TransactionDao;
import dto.TransactionDto;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;
import java.util.List;

public class GetTransactionsHandler implements BaseHandler {

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        // pre
        var result = new HttpResponseBuilder()
            .setStatus(StatusCodes.SERVER_ERROR);

        var auth = AuthFilter.doFilter(request);

        if (!auth.isLoggedIn)
            return result.setBody(
                RestApiAppResponse.ofError(
                    "Attempting to get transactions of unlogged in user"
                )
            );

        // main
        String userName = auth.userName;

        List<TransactionDto> list = TransactionDao.getInstance()
            .query(
                Filters.eq("userName", userName)
            );

        return result.setStatus(StatusCodes.OK)
            .setBody(
                RestApiAppResponse.ofSuccess(list)
            );
    }

}