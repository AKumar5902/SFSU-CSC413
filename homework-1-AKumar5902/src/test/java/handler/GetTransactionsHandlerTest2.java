package handler;

import com.google.gson.reflect.TypeToken;
import dao.TransactionDao;
import dto.TransactionDto;
import org.testng.Assert;
import org.testng.annotations.Test;
import response.CustomHttpResponse;
import response.RestApiAppResponse;
import server.Server;

public class GetTransactionsHandlerTest2 {

    @Test(singleThreaded = true)
    public void getTransactionsTest2() {
        TransactionDao.reset();
        String userId = String.valueOf(Math.random());

        String messageId1 = String.valueOf(Math.random());
        TransactionDto transactionDto1 = new TransactionDto(messageId1);
        transactionDto1.setUserId(userId);
        transactionDto1.setAmount(Math.random());

        TransactionDto transactionDto2 = new TransactionDto();
        transactionDto2.setUserId(userId);
        TransactionDao transactionDao = TransactionDao.getInstance();

        transactionDao.put(transactionDto1);
        transactionDao.put(transactionDto2);
        String test1 = "GET /getTransactions?userId=" + Math.random() + " HTTP/1.1\n"
                + "Host: test\n"
                + "Connection: Keep-Alive\n"
                + "\n";
        CustomHttpResponse response = Server.processRequest(test1);
        RestApiAppResponse<TransactionDto> messages = GsonTool.GSON.fromJson(response.body,
                new TypeToken<RestApiAppResponse<TransactionDto>>() {
                }.getType());
        Assert.assertEquals(messages.data.size(), 0);
    }

}
