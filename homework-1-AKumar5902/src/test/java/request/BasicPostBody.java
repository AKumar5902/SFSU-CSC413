package request;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BasicPostBody {

  @Test
  public void postTest(){
    String test1 = "POST /hello/world HTTP/1.1\n"
        + "Accept-Language: en-us\n"
        + "Accept-Encoding: gzip, deflate\n"
        + "Connection: Keep-Alive";
    ParsedRequest request = CustomParser.parse(test1);
    Assert.assertEquals(request.getPath(), "/hello/world");
    Assert.assertEquals(request.getMethod(), "POST");
  }
}
