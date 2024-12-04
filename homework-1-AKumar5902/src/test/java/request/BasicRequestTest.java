package request;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BasicRequestTest {

  @Test
  public void basicTest(){
    String test1 = "GET /hello HTTP/1.1\n"
        + "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\n"
        + "Accept-Language: en-us\n"
        + "Accept-Encoding: gzip, deflate\n"
        + "Connection: Keep-Alive";
    ParsedRequest request = CustomParser.parse(test1);
    Assert.assertEquals(request.getPath(), "/hello");
    Assert.assertEquals(request.getMethod(), "GET");
  }
}
