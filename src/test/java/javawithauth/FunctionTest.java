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
        headers.put("authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Imh1Tjk1SXZQZmVocTM0R3pCRFoxR1hHaXJuTSIsImtpZCI6Imh1Tjk1SXZQZmVocTM0R3pCRFoxR1hHaXJuTSJ9.eyJhdWQiOiJhcGk6Ly84NDI2NzEzNC1kYTFjLTQxNzUtYWQxYS02Y2E5NmZkZTNmOTYiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9iNTVmMGM1MS02MWE3LTQ1YzMtODRkZi0zMzU2OWIyNDc3OTYvIiwiaWF0IjoxNTk2NjU4MjE2LCJuYmYiOjE1OTY2NTgyMTYsImV4cCI6MTU5NjY2MjExNiwiYWNyIjoiMSIsImFpbyI6IkFVUUF1LzhRQUFBQXpzUkU3VWQyRDF1YlVzSFkzRitSYjB0aHFqT3gySzlOM2JIV3JoaGJVOUpwK05uOWs5Q21rdHZwenE0SzNsMVQ4NGpYNGYyOFFTZDdwOUIveGFXamFnPT0iLCJhbXIiOlsicHdkIiwibWZhIl0sImFwcGlkIjoiYjJlOGVlOTgtMzUwOC00MGQ5LTg2MmUtMGVmODIzYmZjMThhIiwiYXBwaWRhY3IiOiIxIiwiZmFtaWx5X25hbWUiOiJNYXRza2FzIiwiZ2l2ZW5fbmFtZSI6IkNocmlzdG9zIiwiaXBhZGRyIjoiMTM3LjEzNS40OS44MSIsIm5hbWUiOiJDaHJpc3RvcyBNYXRza2FzIiwib2lkIjoiNDQ5ZTZlNmEtZmQ2My00NzA1LTgyOTEtYmMzMmRkYjQ3YjFkIiwic2NwIjoiYWNjZXNzX2FzX3VzZXIiLCJzdWIiOiJ3V2k0bm1ySTZoOUVkRlhjV3Bib3lKMWFXbFRZcHkteHpBVDNGS2NoNXY4IiwidGlkIjoiYjU1ZjBjNTEtNjFhNy00NWMzLTg0ZGYtMzM1NjliMjQ3Nzk2IiwidW5pcXVlX25hbWUiOiJjaHJpc3Rvc0BjbWF0c2thcy5vbm1pY3Jvc29mdC5jb20iLCJ1cG4iOiJjaHJpc3Rvc0BjbWF0c2thcy5vbm1pY3Jvc29mdC5jb20iLCJ1dGkiOiJ6SVVXRXQybk5rSzVEVmQ5cmFNTUFBIiwidmVyIjoiMS4wIn0.oQzamXmp3BF9FwShXrKeH3i5nPVAeXahiSeN0kT8mdPir1RkM7Er2OaqNCquAc785-D98KQnGK7IqS7dp1PeWjO1m6DWfAvELxnYkxHOsrMXIWAM8Azfx3-6qtvPIwcpfcIsZ2QLRAxwMyy3ppEOd8z9e4v1Z3Kci_j7uHQ80Tm1PADh9RQnonvJ0q-V8vvXAt2xrRkxgn61XEHKLEJBQqmGyuIiKIC043eeSv0bYFZJe8h9i9IDXhbHt6qj9yVHV25jtC2NP8ghdrgfCH8i56OcTGCSd3cKW5V9EFznkb_i43w6vnDTl1oK_7955B9afXd19SdThF4rxfpGI0zFqw");
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
