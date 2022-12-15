package sem3chatbot.backend.business.ServiceImpl;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.DeviceCodeCredential;
import com.azure.identity.DeviceCodeCredentialBuilder;
import com.azure.identity.DeviceCodeInfo;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.MessageCollectionPage;
import lombok.AllArgsConstructor;
import okhttp3.Request;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.MSGraphService;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class MSGraphServiceImpl implements MSGraphService {
    //we keep state, such as device code and user client data in these fields
    //which are assigned via initalizeGraph().
    private Properties _properties;
    private DeviceCodeCredential _deviceCodeCredential;
    private GraphServiceClient<Request> _userClient;


    @Override
    public void initializeGraph(Properties properties, Consumer<DeviceCodeInfo> consumer) throws Exception {
        //add custom exception for this
        if (properties == null) {
            throw new Exception("Properties cannot be null");
        }

        _properties = properties;

        final String clientId = properties.getProperty("app.clientId");
        final String authTenantId = properties.getProperty("app.authTenant");
        final List<String> graphUserScopes = Arrays
                .asList(properties.getProperty("app.graphUserScopes").split(","));

        //prompts activation
        _deviceCodeCredential = new DeviceCodeCredentialBuilder()
                .clientId(clientId)
                .tenantId(authTenantId)
                .challengeConsumer(consumer)
                .build();

        final TokenCredentialAuthProvider authProvider =
                new TokenCredentialAuthProvider(graphUserScopes, _deviceCodeCredential);

        _userClient = GraphServiceClient.builder()
                .authenticationProvider(authProvider)
                .buildClient();
    }

    @Override
    public String getUserToken() throws Exception {
        //add custom exception for this
        if (_deviceCodeCredential == null) {
            throw new Exception("Graph has not been initialized for user auth");
        }

        //tokens and scopes are immutable to prevent modification
        final String[] graphUserScopes = _properties.getProperty("app.graphUserScopes").split(",");

        final TokenRequestContext context = new TokenRequestContext();
        context.addScopes(graphUserScopes);

        final AccessToken token = _deviceCodeCredential.getToken(context).block();
        return token.getToken();
    }

    @Override
    public User getUser() throws Exception {
        //add custom exception for this
        if (_userClient == null) {
            throw new Exception("Graph has not been initialized for user auth");
        }

        return _userClient.me()
                .buildRequest()
                .select("displayName,mail,userPrincipalName")
                .get();
    }

    @Override
    public MessageCollectionPage getInbox() throws Exception {
        //add custom exception for this
        if (_userClient == null) {
            throw new Exception("Graph has not been initialized for user auth");
        }

        // we only query the latest 25 messages to prevent unnecessary overhead
        return _userClient.me()
                .mailFolders("inbox")
                .messages()
                .buildRequest()
                .select("from,isRead,receivedDateTime,subject")
                .top(25)
                //order by descending to get the latest mails
                .orderBy("receivedDateTime DESC")
                .get();
    }

    @Override
    public void initializeGraphForAppOnlyAuth() throws Exception {
    //need a client secret to make this work(AAD activation).
    }
}
