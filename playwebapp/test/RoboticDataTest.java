import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.core.Is;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static org.junit.Assert.assertThat;

public class RoboticDataTest {

    private final static String HOST = "http://localhost:9000";

    @Test
    public void sendRoboticData() throws IOException {
        while (true) {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(HOST + "/robotic");
            httpPost.setEntity(new StringEntity(generateSpikeData(), ContentType.APPLICATION_JSON));
            HttpResponse response = httpClient.execute(httpPost);
            String content = readIS(response.getEntity().getContent());
            System.out.println(content);
            assertThat(content, response.getStatusLine().getStatusCode(), Is.is(200));
        }
    }

    private String readIS(InputStream inputStream) {
        try {
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateSpikeData() {
        return "{\"channel\": 0, \"data\" : [{\"time\": " + System.currentTimeMillis() + ", \"data\": \"" + UUID.randomUUID() + "\" }]}";
    }

}
