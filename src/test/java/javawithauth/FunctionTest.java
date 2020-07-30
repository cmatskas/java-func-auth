package javawithauth;

import com.microsoft.azure.functions.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


/**
 * Unit test for Function class.
 */
public class FunctionTest {
    /**
     * Unit test for HttpTriggerJava method.
     */
    @Test
    public void testHttpTriggerJava() throws Exception {
        // Setup
        @SuppressWarnings("unchecked")
        final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);

        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "Azure");
        doReturn(queryParams).when(req).getQueryParameters();

        final Optional<String> queryBody = Optional.empty();
        doReturn(queryBody).when(req).getBody();

        final Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Imh1Tjk1SXZQZmVocTM0R3pCRFoxR1hHaXJuTSIsImtpZCI6Imh1Tjk1SXZQZmVocTM0R3pCRFoxR1hHaXJuTSJ9.eyJhdWQiOiJhcGk6Ly84NDI2NzEzNC1kYTFjLTQxNzUtYWQxYS02Y2E5NmZkZTNmOTYiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9iNTVmMGM1MS02MWE3LTQ1YzMtODRkZi0zMzU2OWIyNDc3OTYvIiwiaWF0IjoxNTk2MDc3NjQ1LCJuYmYiOjE1OTYwNzc2NDUsImV4cCI6MTU5NjA4MTU0NSwiYWNyIjoiMSIsImFpbyI6IkFVUUF1LzhRQUFBQXF2SDBRZHQya2hkd3ZuM3YvMlQvWk1PNElmSmZyTUJqOCs4VlpLblZuL0tmSGY0QlEvM2pQUkFpckVwRFA1NW11K2svRGtQL1cwRWlSeTgvMmRGUkN3PT0iLCJhbXIiOlsicHdkIiwibWZhIl0sImFwcGlkIjoiYjJlOGVlOTgtMzUwOC00MGQ5LTg2MmUtMGVmODIzYmZjMThhIiwiYXBwaWRhY3IiOiIxIiwiZmFtaWx5X25hbWUiOiJNYXRza2FzIiwiZ2l2ZW5fbmFtZSI6IkNocmlzdG9zIiwiaXBhZGRyIjoiMTM3LjEzNS41My4yMTkiLCJuYW1lIjoiQ2hyaXN0b3MgTWF0c2thcyIsIm9pZCI6IjQ0OWU2ZTZhLWZkNjMtNDcwNS04MjkxLWJjMzJkZGI0N2IxZCIsInNjcCI6ImFjY2Vzc19hc191c2VyIiwic3ViIjoid1dpNG5tckk2aDlFZEZYY1dwYm95SjFhV2xUWXB5LXh6QVQzRktjaDV2OCIsInRpZCI6ImI1NWYwYzUxLTYxYTctNDVjMy04NGRmLTMzNTY5YjI0Nzc5NiIsInVuaXF1ZV9uYW1lIjoiY2hyaXN0b3NAY21hdHNrYXMub25taWNyb3NvZnQuY29tIiwidXBuIjoiY2hyaXN0b3NAY21hdHNrYXMub25taWNyb3NvZnQuY29tIiwidXRpIjoid1JqeGQwNTFVMGlYU2VnZ0w1VFRBQSIsInZlciI6IjEuMCJ9.H-j7WY0srhi3eoktRb2dBfTmzJNAU8bETqvZjftbVYR4S3xX6UWC-_lPdZbxt76bXOJl90Su5KJDk0a6rCYgOTRh94KkZJBcdv9ZzQKkHlDfWNNuMWFl8D7eA1ObmjTGd3Xe5Ah_jMJWtLC0-YsSPFKYCJKBMgFTmsUYvz4Lv7H1MrV-rW4nmjVyenN5bL3xJuxfTBU6_tl07o61H48ukDmjtnGMR5vGAehnhzi3jXj40-TehY8CmPZhzJUV6ACJ1qagatT_ai5ZXKPtOdcHpcHxHyyN5AH-C9QIqqLzrDYh6qmfnWGLMMibyp4QejKPLUU8GGxiZjkWyGQUgu16iw");
        doReturn(headers).when(req).getHeaders();

        doAnswer(new Answer<HttpResponseMessage.Builder>() {
            @Override
            public HttpResponseMessage.Builder answer(InvocationOnMock invocation) {
                HttpStatus status = (HttpStatus) invocation.getArguments()[0];
                return new HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status);
            }
        }).when(req).createResponseBuilder(any(HttpStatus.class));

        final ExecutionContext context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();

        // Invoke
        final HttpResponseMessage ret = new Function().run(req, context);

        // Verify
        assertEquals(ret.getStatus(), HttpStatus.OK);
    }
}
