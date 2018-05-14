package appserver;

import java.net.URI;

public class ApplicationServerProfile implements Comparable<ApplicationServerProfile> {
    private URI uri;
    private ApplicationDiscoveryMessage profile;

    public ApplicationServerProfile(URI uri, ApplicationDiscoveryMessage profile) {
        this.uri = uri;
        this.profile = profile;
    }

    public URI getUri() {
        return uri;
    }

    public ApplicationDiscoveryMessage getProfile() {
        return profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationServerProfile that = (ApplicationServerProfile) o;

        return uri != null ? uri.equals(that.uri) : that.uri == null;
    }

    @Override
    public int hashCode() {
        return uri != null ? uri.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ApplicationServerProfile{" +
                "uri=" + uri +
                ", profile=" + profile +
                '}';
    }

    @Override
    public int compareTo(ApplicationServerProfile other) {
        return uri.compareTo(other.uri);
    }
}