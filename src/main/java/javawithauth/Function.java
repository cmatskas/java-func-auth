package javawithauth;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import com.microsoft.aad.msal4j.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Function {

    private final static String CLIENT_ID = "84267134-da1c-4175-ad1a-6ca96fde3f96";
    private final static String AUTHORITY = "https://login.microsoftonline.com/cmatskas.onmicrosoft.com";
    private final static String CLIENT_SECRET = "zFWh.J.pqP~LSUq50Y1W3hsXt~~J4EJ1m_";
    private final static Set<String> SCOPE = Collections.singleton("https://graph.microsoft.com/user.read");

    /**
     * This function listens at endpoint "/api/GetGraphDataForUser". Two ways to invoke it
     * using "curl" command in bash: curl "{your host}/api/GetGraphDataForUser"
     * 
     * @throws Exception
     */
    @FunctionName("GetGraphDataForUser")
    public HttpResponseMessage run(@HttpTrigger(name = "req", methods = { HttpMethod.GET,
            HttpMethod.POST }, authLevel = AuthorizationLevel.ANONYMOUS) final HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) throws Exception {
        context.getLogger().info("Java HTTP trigger processed a request.");

        final String jwt = getJwtFromHeader(request);
        if (jwt == null) {
            return request.createResponseBuilder(HttpStatus.UNAUTHORIZED)
                .body("No Authorization header present")
                .build();
        }

        final IAuthenticationResult authResult = ValidateToken(jwt);
        java.net.http.HttpResponse<String> response = callMicrosoftGraphMeEndpoint(authResult);
        return request.createResponseBuilder(HttpStatus.OK).body(response.body()).build();
    }

    private static String getJwtFromHeader(final HttpRequestMessage<Optional<String>> request) {
        final Map<String, String> headers = request.getHeaders();
        final String authHeader = headers.get("authorization");
        final String[] authHeaderParts = authHeader.split(" ");
        return authHeaderParts[1];
    }

    private static IAuthenticationResult ValidateToken(final String authToken) throws Exception {

        final IClientCredential credential = ClientCredentialFactory.createFromSecret(CLIENT_SECRET);
        final ConfidentialClientApplication cca = ConfidentialClientApplication.builder(CLIENT_ID, credential)
                .authority(AUTHORITY).build();

        IAuthenticationResult result;
        try {
            final OnBehalfOfParameters parameters = OnBehalfOfParameters
                        .builder(SCOPE, new UserAssertion(authToken)).build();
            result = cca.acquireToken(parameters).join();
        } 
        catch (final Exception ex) {
                throw ex;
        }
        return result;
    }

    private java.net.http.HttpResponse<String> callMicrosoftGraphMeEndpoint(final IAuthenticationResult authResult) throws Exception{
        
        java.net.http.HttpResponse<String> response = null;
        try {
            
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create("https://graph.microsoft.com/beta/me"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + authResult.accessToken())
                .build();

                final HttpClient client = HttpClient.newHttpClient();
                response = client.send(request, BodyHandlers.ofString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return response;
    }
}
