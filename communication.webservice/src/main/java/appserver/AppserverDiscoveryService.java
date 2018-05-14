package appserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import message.AppserverConfigurationLoader;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Retrieves application server profiles from given URLs
 */
@Service
public class AppserverDiscoveryService {
    private static final String PATH = "applicationDiscovery";
    private static final int TIMEOUT_MS = 30 * 1000;

    private static final Logger log = LoggerFactory.getLogger(AppserverDiscoveryService.class);

    private List<ApplicationServerAttempts> appServerAttempts;
    private ConcurrentSkipListSet<ApplicationServerProfile> appServerProfiles;
    private RestTemplate restTemplate;

    public AppserverDiscoveryService() {
        appServerProfiles = new ConcurrentSkipListSet<>();

    }

    @Autowired(required = false)
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void init() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        }
        initAppserver();
        discoverAsync();
    }

    void initAppserver() {
        // This will generate a runtime exception if it fails. This is what we want since not
        // getting the application server base URIs should be considered as a hard failure.
        if (appServerAttempts == null) {
            List<URI> uris = AppserverConfigurationLoader.getInstance().getAppserverBaseURIs();
            appServerAttempts = new ArrayList<>();

            for (URI uri: uris) {
                try {
                    appServerAttempts.add(new ApplicationServerAttempts(uri));
                } catch (URISyntaxException e) {
                    throw new RuntimeException("Failed constructing application server URI from " + uri.toString(), e);
                }
            }
        }
    }

    /**
     * Run application servers discovery in a separate thread
     */
    void discoverAsync() {
        CompletableFuture.runAsync(this::discover);
    }

    /**
     * Run application servers discovery.
     * This method blocks the current thread until all urls will be processed concurrently.
     */
    void discover() {
        log.trace("ENTER application server discovery");

        if (appServerAttempts.isEmpty()) {
            log.info("No application server addresses found for discovery");
            return;
        }

        while (appServerAttempts.size() > 0) {
            List<ApplicationServerAttempts> copyOfAppServerAttempts = new ArrayList<>(appServerAttempts);

            for (ApplicationServerAttempts attempts: copyOfAppServerAttempts) {
                URI uri = attempts.attempt();
                ApplicationServerProfile profile = requestProfile(attempts.getBaseUri(), uri);

                if (profile != null) {
                    log.info("Discovered application server {}", profile);
                    appServerProfiles.add(profile);
                    appServerAttempts.remove(attempts);
                }
            }
        }

        log.trace("EXIT application server discovery");
    }

    private ApplicationServerProfile requestProfile(URI baseURI, URI uri) {
        log.trace("Discovering {}", uri.toString());

        ApplicationServerProfile applicationServerProfile = null;
        try {
            ApplicationDiscoveryMessage msg = restTemplate.getForEntity(uri, ApplicationDiscoveryMessage.class).getBody();
            applicationServerProfile = new ApplicationServerProfile(baseURI, msg);
        } catch (RestClientException e) {
            Throwable cause = e.getMostSpecificCause();
            String causeMessage = (cause != null) ? cause.toString() : e.toString();
            log.warn("Failed to discover {}, {}", uri.toString(), causeMessage);
        }
        return applicationServerProfile;
    }

    /**
     * Get application server profiles.
     *
     * @return read-only set of application server profiles
     */
    public Set<ApplicationServerProfile> get() {
        return Collections.unmodifiableSet(appServerProfiles);
    }

    private class ApplicationServerAttempts {

        private URI baseUri;

        private URI uri;

        private int attempts;

        private ApplicationServerAttempts(URI appserverBaseUri) throws URISyntaxException {
            baseUri = appserverBaseUri;
            uri = new URI(appserverBaseUri.toString() + PATH);
            attempts = 0;
        }

        private URI getBaseUri() {
            return baseUri;
        }

        private URI attempt() {
            if (attempts > 2) {
                try {
                    Thread.sleep(TIMEOUT_MS);
                } catch (InterruptedException e) {
                    return null;
                }
            }

            attempts++;
            return uri;
        }
    }
}