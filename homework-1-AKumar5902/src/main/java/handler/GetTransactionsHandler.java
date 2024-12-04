package handler;

import dao.TransactionDao;
import dto.TransactionDto;
import request.ParsedRequest;
import response.ResponseBuilder;
import response.RestApiAppResponse;

import java.util.List;

public class GetTransactionsHandler implements BaseHandler {

    @Override
    public ResponseBuilder handleRequest(ParsedRequest request) {
        // TODO
        TransactionDto transactionDto =
                GsonTool.GSON.fromJson(request.getBody(), TransactionDto.class);
        if (transactionDto == null) {
            var res = new RestApiAppResponse<>(false, List.of(),
                    "No transactions");
            return new ResponseBuilder().setStatus("400 Bad Request")
                    .setBody(GsonTool.GSON.toJson(res));
        }

        TransactionDto userA = TransactionDao.getInstance().get(transactionDto.getUserId());


        if (userA == null) {
            var res = new RestApiAppResponse<>(false, null,
                    "Invalid User Name");
            return new ResponseBuilder().setStatus("400 Bad Request")
                    .setBody(GsonTool.GSON.toJson(res));
        }


        var res = new RestApiAppResponse<>(true, TransactionDao.getInstance().getAll(), null);


        return new ResponseBuilder().setStatus("200 OK").setBody(GsonTool.GSON.toJson(res));
    }

}