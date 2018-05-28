package appserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.hibernate.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import entity.MessagePayload;
import message.ApplicationMessage;
import message.ApplicationMessages;
import message.MessagePayloadPersister;

public abstract class ApplicationServiceMessagePusher<T extends MessagePayload, U extends MessagePayload, M extends ApplicationMessage>
        implements MessagePayloadPersister<T, U> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceMessagePusher.class);

    private final AppserverDiscoveryService service;
    private final Class<T> payloadClass;
    private final List<M> messages;
    private final List<U> samples;

    private ApplicationServerProfile profile;

    public ApplicationServiceMessagePusher(AppserverDiscoveryService service, Class<T> payloadClass) {
        this.service = service;
        this.payloadClass = payloadClass;
        messages = new ArrayList<>();
        samples = new ArrayList<>();
    }

    protected abstract U buildPayloadToBePersisted(Session session, T originalPayload);

    protected abstract M buildApplicationMessage(Session session, T originalPayload);

    @Override
    public U persistPayload(Session session, T messagePayload) {
        if (profile == null) {
            profile = findServerProfile();
        }
        if (profile == null) {
            logger.debug("Still no profile available, cannot do anything at the moment.");
            return null;
        }

        samples.add(buildPayloadToBePersisted(session, messagePayload));
        messages.add(buildApplicationMessage(session, messagePayload));
        return null;
    }

    @Override
    public Class<T> getPayloadClass() {
        return payloadClass;
    }

    public List<U> postProcess() {
        if (profile == null) {
            logger.warn("No application server available");
            return new ArrayList<>();
        }
        if (messages.isEmpty()) {
            return new ArrayList<>();
        }

        CloseableHttpResponse response = null;
        List<ApplicationMessage> appMessages = new ArrayList<>(messages);
        messages.clear();
        List<U> outSamples = new ArrayList<>(samples);
        samples.clear();
        String uri = profile.getUri().toString() + "messages";

        try {
            ObjectMapper mapper = new ObjectMapper();
            ApplicationMessages messages = new ApplicationMessages();
            messages.setMessages(appMessages);
            byte[] bytes = mapper.writeValueAsBytes(messages);

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
            HttpPost post = new HttpPost(uri);
            ByteArrayEntity entity = new ByteArrayEntity(bytes);
            post.setEntity(entity);
            post.addHeader("Content-Type", "application/json");
            post.addHeader("Accept", "application/json");
            response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();

            Object[] params = {uri,
                    statusCode,
                    appMessages.get(0).getSequenceNumber(),
                    appMessages.get(appMessages.size() - 1).getSequenceNumber(),
                    appMessages.size()};

            if (statusCode != HttpStatus.CREATED.value()) {
                return new ArrayList<>();
            } else {
                return outSamples;
            }
        } catch (IOException e) {
            Object[] params = {uri,
                    appMessages.get(0).getSequenceNumber(),
                    appMessages.get(appMessages.size() - 1).getSequenceNumber(),
                    appMessages.size()};
            return new ArrayList<>();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException ignore) {
                }
            }
        }
    }

    private ApplicationServerProfile findServerProfile() {
        Set<ApplicationServerProfile> profiles = service.get();
        for (ApplicationServerProfile profile : profiles) {
            if (profile.getProfile().getApplicationType() == ApplicationType.TECH_SUPPORT) {
                return profile;
            }
        }
        return null;
    }
}
