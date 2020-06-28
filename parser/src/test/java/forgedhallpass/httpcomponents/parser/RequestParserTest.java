package forgedhallpass.httpcomponents.parser;

import forgedhallpass.httpcomponents.parser.model.request.HttpRequest;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class RequestParserTest {

    @Test
    public void testParseHttpRequest() {
        final String request = "POST /testApp/testService HTTP/1.1\r\n" +
                               "Host: localhost:8443\r\n" +
                               "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0\r\n" +
                               "Accept: */*\r\n" +
                               "Accept-Language: en-US,en;q=0.5\r\n" +
                               "Accept-Encoding: gzip, deflate\r\n" +
                               "Content-Type: application/x-www-form-urlencoded; charset=UTF-8\r\n" +
                               "X-Requested-With: XMLHttpRequest\r\n" +
                               "Content-Length: 240\r\n" +
                               "Origin: http://localhost:8443\r\n" +
                               "DNT: 1\n" +
                               "Connection: close\n" +
                               "Referer: http://localhost:8443/testApp/testService/testPath\r\n" +
                               "Cookie: JSESSIONID=someRandomSessionId\r\n" +
                               "\r\n" +
                               "testPayload=%7B%22filter%22:%7B%22name%22:%22testObject%22%7D,%22sort%22:%7B%22column%22:%22testObjectColumn%22,%22direction%22:%22ASC%22%7D%7D";

        final HttpRequest httpRequest = RequestParser.parse(request);
        Assert.assertThat(httpRequest.toString(), is(request));
    }
}