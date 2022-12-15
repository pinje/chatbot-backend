package sem3chatbot.backend.business.ServiceImpl;

import com.azure.identity.DeviceCodeCredential;
import com.azure.identity.DeviceCodeInfo;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.MessageCollectionPage;
import lombok.AllArgsConstructor;
import okhttp3.Request;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.MSGraphService;

import java.util.Properties;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class MSGraphServiceImpl implements MSGraphService {
    private Properties properties;
    private DeviceCodeCredential deviceCodeCredential;
    private GraphServiceClient<Request> userClient;


    @Override
    public void initializeGraph(Properties properties, Consumer<DeviceCodeInfo> consumer) throws Exception {

    }

    @Override
    public String getUserToken() throws Exception {
        return null;
    }

    @Override
    public User getUser() throws Exception {
        return null;
    }

    @Override
    public MessageCollectionPage getInbox() throws Exception {
        return null;
    }

    @Override
    public void initializeGraphForAppOnlyAuth() throws Exception {

    }
}
