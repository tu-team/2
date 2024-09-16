package message;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;


public class AppserverConfigurationLoader {

    public static final String APPSERVER_CONFIGURATION_FILE = "appserver.properties";

    private static final String PROPERTY_PREFIX = "appserver.";

    private static final String PROPERTY_POSTFIX = ".base.uri";

    private static final String TU_SERVER_BASE_URI = "tu.server.base.uri";

    private static final Logger logger = LoggerFactory.getLogger(AppserverConfigurationLoader.class);

    private static Object lock = new Object();

    private static volatile AppserverConfigurationLoader loader;

    private URI tuServerBaseURI;

    private List<URI> appserverBaseURIs;

    private AppserverConfigurationLoader() {
        super();
    }

    public URI getTuServerBaseURI() {
        return tuServerBaseURI;
    }

    public List<URI> getAppserverBaseURIs() {
        return new ArrayList<>(appserverBaseURIs);
    }

    public static AppserverConfigurationLoader getInstance() {
        AppserverConfigurationLoader local = loader;

        if (local == null) {
            synchronized(lock) {
                local = loader;
                if (local == null) {
                    local = loader = new AppserverConfigurationLoader();
                    local.init();
                }
            }
        }

        return local;
    }

    public static void clearInstance() {
        loader = null;
    }

    private void init() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream is = loader.getResourceAsStream(APPSERVER_CONFIGURATION_FILE);

            if (is == null) {
                throw new RuntimeException("Cannot find the appserver configuration file " +
                        APPSERVER_CONFIGURATION_FILE +
                        " in the classpath.");
            }

            Properties properties = new Properties();
            properties.load(is);
            TreeSet<URI> uniqueURIs = new TreeSet<>();
            HashSet<String> uniqueIPPorts = new HashSet<>();
            int index = 1;

            String tuValue = properties.getProperty(TU_SERVER_BASE_URI);

            boolean found = true;

            while (found) {
                String propertyName = PROPERTY_PREFIX + index + PROPERTY_POSTFIX;
                String value = properties.getProperty(propertyName);

                if (value != null) {
                    if (!value.endsWith("/")) {
                        value += "/";
                    }

                    URI uri = new URI(value);
                    String hostPort = uri.getHost() + uri.getPort();

                    if (!uri.getScheme().equals("http")) {
                        throw new RuntimeException("Invalid scheme for appserver base URI: " + value);
                    }

                    if (uniqueIPPorts.contains(hostPort)) {
                        throw new RuntimeException("Duplicate appserver base URI: " + value);
                    }

                    uniqueURIs.add(uri);
                    uniqueIPPorts.add(hostPort);
                    index++;
                } else {
                    found = false;
                }
            }

            if (uniqueURIs.size() > 0 && tuValue == null) {
                throw new RuntimeException("Cannot find TU server base URI " +
                        APPSERVER_CONFIGURATION_FILE);
            }

            if (tuValue != null) {
                if (!tuValue.endsWith("/")) {
                    tuValue += "/";
                }

                tuServerBaseURI = new URI(tuValue);

                if (!tuServerBaseURI.getScheme().equals("http")) {
                    throw new RuntimeException("Invalid scheme for TU server base URI: " + tuValue);
                }
            }

            appserverBaseURIs = new ArrayList<>(uniqueURIs);
        } catch (IOException | URISyntaxException e) {
            logger.error("Failed to load appserver configuration: {0}.", e, e.getMessage());
            throw new RuntimeException("Failed to load appserver configuration: " + e.getMessage(), e);
        }
    }
}