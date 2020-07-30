package javawithauth;

import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.azure.functions.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.microsoft.aad.msal4j.*;

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
        headers.put("authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Imh1Tjk1SXZQZmVocTM0R3pCRFoxR1hHaXJuTSIsImtpZCI6Imh1Tjk1SXZQZmVocTM0R3pCRFoxR1hHaXJuTSJ9.eyJhdWQiOiJhcGk6Ly84NDI2NzEzNC1kYTFjLTQxNzUtYWQxYS02Y2E5NmZkZTNmOTYiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9iNTVmMGM1MS02MWE3LTQ1YzMtODRkZi0zMzU2OWIyNDc3OTYvIiwiaWF0IjoxNTk2MDcxODc1LCJuYmYiOjE1OTYwNzE4NzUsImV4cCI6MTU5NjA3NTc3NSwiYWNyIjoiMSIsImFpbyI6IkFVUUF1LzhRQUFBQWtVbnNtbW01UlROTDVjaXZZNDVvQWxmdWk2S0JKTXNZYXc5MHhXb3R4L1NxMndLT2N5MkZ5TW1SYUxFMzh2S3JmZ1NOVjFoT0V6QXdjaHF3NEpQM05BPT0iLCJhbXIiOlsicHdkIiwibWZhIl0sImFwcGlkIjoiYjJlOGVlOTgtMzUwOC00MGQ5LTg2MmUtMGVmODIzYmZjMThhIiwiYXBwaWRhY3IiOiIxIiwiZmFtaWx5X25hbWUiOiJNYXRza2FzIiwiZ2l2ZW5fbmFtZSI6IkNocmlzdG9zIiwiaXBhZGRyIjoiMTM3LjEzNS41My4yMTkiLCJuYW1lIjoiQ2hyaXN0b3MgTWF0c2thcyIsIm9pZCI6IjQ0OWU2ZTZhLWZkNjMtNDcwNS04MjkxLWJjMzJkZGI0N2IxZCIsInNjcCI6ImFjY2Vzc19hc191c2VyIiwic3ViIjoid1dpNG5tckk2aDlFZEZYY1dwYm95SjFhV2xUWXB5LXh6QVQzRktjaDV2OCIsInRpZCI6ImI1NWYwYzUxLTYxYTctNDVjMy04NGRmLTMzNTY5YjI0Nzc5NiIsInVuaXF1ZV9uYW1lIjoiY2hyaXN0b3NAY21hdHNrYXMub25taWNyb3NvZnQuY29tIiwidXBuIjoiY2hyaXN0b3NAY21hdHNrYXMub25taWNyb3NvZnQuY29tIiwidXRpIjoiQ2tCaThfQnJ3a2FBRlpfYjNBclhBQSIsInZlciI6IjEuMCJ9.TzQQRhpriG5hJ9tHAjQ-d2duOt6dqDqVGWI3xXf5ywZrJTDJlINE4aVWtYLU3iXW9kB5PFTRdz-ZmTGPqL0E1ZeWGO8tkT1JaHP1VFXn5UIVJUWeOW4df1rX-jAfNxZyE-LvTcDQo6u14C_YrMaAvVXBNvNz-SAEil4bVp00sEAEqMN1Ima-fM859wP8Y4RnuOvmYt7ovgg44gzp-9Yi9_q-gAiACxwFazJ_E24CGtg2W8xOOUGH4lrAqfdRjisd-jD80NDf4K7iWvltRKAPv6qLYsLuevnQLQ1UO_WHKpsosJ9TSl943XNyZ0aYLfFvFMUrwnHvLJfW1CGsM_Anew");
        doReturn(headers).when(req).getHeaders();

        //doReturn(mock(IAuthenticationResult.class)).when();

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
