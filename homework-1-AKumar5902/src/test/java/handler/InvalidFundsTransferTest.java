package handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.UserDao;
import dto.TransferRequestDto;
import dto.UserDto;
import org.testng.Assert;
import org.testng.annotations.Test;
import response.CustomHttpResponse;
import response.RestApiAppResponse;
import server.Server;
import testutils.TestUtils;

public class InvalidFundsTransferTest {
    private static final Gson gson = new Gson();

    @Test(singleThreaded = true)
    public void transferTest4() {
        UserDao.reset();
        UserDto user1 = TestUtils.createUser();
        UserDto user2 = TestUtils.createUser();

        TransferRequestDto requestDto = new TransferRequestDto(Math.random() * 100, user1.getUniqueId(), user2.getUniqueId());
        String test1 = "GET /transfer HTTP/1.1\n"
                + "Host: test\n"
                + "Connection: Keep-Alive\n"
                + "\n"
                + gson.toJson(requestDto);
        CustomHttpResponse response = Server.processRequest(test1);
        Assert.assertEquals(response.status, "400 Bad Request");
        RestApiAppResponse<?> userRes = GsonTool.GSON.fromJson(response.body,
                new TypeToken<RestApiAppResponse<?>>() {
                }.getType());
        Assert.assertEquals(userRes.message, "Not enough funds.");
    }
}
