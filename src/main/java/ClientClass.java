
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ClientClass {

    HttpClient client;
    URI uri;

    ClientClass(String path) throws URISyntaxException {
        client = HttpClients.createDefault();
        uri = new URIBuilder()
                .setScheme("http")
                .setHost("localhost")
                .setPort(5054)
                .setPath(path)
                .build();
        System.out.println(uri.toString());
    }

    ClientClass(String path, String query) throws URISyntaxException {
        client = HttpClients.createDefault();
        uri = new URIBuilder()
                .setScheme("http")
                .setHost("localhost")
                .setPort(5054)
                .setPath(path)
                .setParameter("id", query)
                .build();
    }

    public void getUsers(String url) throws IOException {
        HttpGet getRequest = new HttpGet(url);
        HttpResponse response = client.execute(getRequest);
        System.out.println("This is status line: " + response.getStatusLine());
        String json = EntityUtils.toString(response.getEntity());
        System.out.println("Body: " + json);
    }

    public String createAddress(String streetName) throws IOException {
        HttpPost postReq = new HttpPost(uri);
        String json = "{ \"addressNickname\": \"tesla\", \"cityName\": \"beograd\", \"id\": 0, \"postalCode\": \"11000\", \"regionName\": \"string\", \"street\": \"" + streetName + "\", \"streetAdditional\": \"string\", \"userId\": 50}";
        System.out.println(json);
        HttpEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        postReq.setEntity(stringEntity);
        HttpResponse response = client.execute(postReq);
        String jsonResponse = EntityUtils.toString(response.getEntity());
        System.out.println("This is status line: " + response.getStatusLine());
        System.out.println("Body: " + jsonResponse);
        String id = Utilities.extractIdFromResponse(jsonResponse);
        System.out.println(id);
        return id;
    }

    public void getAddressById(String id) throws URISyntaxException, IOException {
        String query = "id=" + id;
        URI newUri = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), query, null);
        HttpGet getReq = new HttpGet(newUri);
        System.out.println(newUri.toString());
        HttpResponse response = client.execute(getReq);
        System.out.println("This is status line: " + response.getStatusLine());
        String jsonResponse = EntityUtils.toString(response.getEntity());
        System.out.println(jsonResponse);
        assertThat(id).isEqualTo(Utilities.extractIdFromResponse(jsonResponse));
    }
}
