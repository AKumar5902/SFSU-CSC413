package handler;

import dao.TransactionDao;

import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;



public class GetTransactionsHandler implements BaseHandler {

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {

//        if (transactionDto == null) {
//            var res = new RestApiAppResponse<>(false, List.of(),
//                    "No transactions");
//            return new HttpResponseBuilder().setStatus(StatusCodes.OK)
//                    .setBody(res);
//        }
        // Prepare the filter for querying the database (for example, by userId or timestamp)
        Document filter = new Document();
//        if (transactionDto.getUserId() != null) {
//            filter.append("userId", transactionDto.getUserId());  // Assuming we filter by userId
//        }
//        List<TransactionDto> transactions = TransactionDao.getInstance().query(filter);
//        System.out.println(transactions.size());


        // If transactions are found, return them in the response
        var res = new RestApiAppResponse<>(true,TransactionDao.getInstance().query(filter),
                "Transactions retrieved successfully.");
        return new HttpResponseBuilder().setStatus(StatusCodes.OK).setBody(res);
    }
}
