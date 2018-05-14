package appserver;

import java.io.Serializable;

public class ApplicationDiscoveryMessage implements Serializable {
    private static final long serialVersionUID = -815698132872870155L;
    private String m_applicationName;
    private ApplicationType m_applicationType;
    private String m_applicationUri;

    public String getApplicationName() {
        return m_applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.m_applicationName = applicationName;
    }

    public ApplicationType getApplicationType() {
        return m_applicationType;
    }

    public void setApplicationType(ApplicationType applicationType) {
        this.m_applicationType = applicationType;
    }

    public String getApplicationUri() {
        return m_applicationUri;
    }

    public void setApplicationUri(String applicationUri) {
        this.m_applicationUri = applicationUri;
    }

    @Override
    public String toString() {
        return "ApplicationDiscoveryMessage{" +
                "m_applicationName='" + m_applicationName + '\'' +
                ", m_applicationType=" + m_applicationType +
                ", m_applicationUri='" + m_applicationUri + '\'' +
                '}';
    }
}