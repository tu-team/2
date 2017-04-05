import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.core.Is;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.assertThat;

public class RoboticDataTest {

    private final static String HOST = "http://localhost:9000";

    @Test
    public void sendRoboticData() throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        while (true) {
            HttpPost httpPost = new HttpPost(HOST + "/robotic");
            httpPost.setEntity(new StringEntity(generateSpikeData(), ContentType.APPLICATION_JSON));
            HttpResponse response = httpClient.execute(httpPost);
            assertThat(response.getStatusLine().getStatusCode(), Is.is(200));
        }
    }

    private String generateSpikeData() {
        return "{\"channel\": 0, \"data\" : [{\"time\": " + System.currentTimeMillis() + ", \"data\": \"" + UUID.randomUUID() + "\" }]}";
    }

}
