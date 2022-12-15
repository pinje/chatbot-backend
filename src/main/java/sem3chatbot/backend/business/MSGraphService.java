package sem3chatbot.backend.business;


import com.azure.identity.DeviceCodeInfo;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.MessageCollectionPage;

import java.util.Properties;
import java.util.function.Consumer;

public interface MSGraphService {
    /**
     initializes the MSGraph according to oAuth properties, defined in oAuth.properties
     @param properties oAuth property bundle specifying clientId, clientSecret (if present) and tenantId(if present)
     @param consumer A function allowing the user to meet the device code challenge.
     @throws Exception */
    void initializeGraph(Properties properties, Consumer<DeviceCodeInfo> consumer) throws Exception;

    /**
     * Gets the JWT with the user context credentials
     * @return A string representing a signed JWT, containing user data as payload
     * @throws Exception
     */
    String getUserToken() throws Exception;

    /**
     * Gets the currently authenticated user data
     * @return An Azure User Object containing all the queried data
     * @throws Exception
     */
    User getUser() throws Exception;

    /**
     * Gets the top-most mails in the currently authenticated user's Microsoft Outlook inbox
     * @return A paginated MessageCollection with a given number of top-most mails received
     * @throws Exception
     */
    MessageCollectionPage getInbox() throws Exception;

    /**
     * Initializes a graph with Azure client secret authentication instead of only clientId
     * @throws Exception
     */
    void initializeGraphForAppOnlyAuth() throws Exception;
}
