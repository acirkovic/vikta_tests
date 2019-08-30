import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class ClientClass {

    private HttpClient client;
    private URI uri;

    private Properties props = new Properties();
    private final String pathToProps= "src/main/resources/config.properties";

    final static Logger logger = Logger.getLogger(ClientClass.class);

    ClientClass() {
        client = HttpClients.createDefault();
    }

    public URI buildURI(String path) throws URISyntaxException, IOException {
        props.load(new FileInputStream(pathToProps));
        URI uri = new URIBuilder()
                .setScheme(props.getProperty("protocol"))
                .setHost(props.getProperty("host"))
                .setPort(Integer.parseInt(props.getProperty("port")))
                .setPath(props.getProperty("addressAPI"))
                .build();
        System.out.println(uri.toString());
        return uri;
    }

    public URI buildURI(String path, String id) throws URISyntaxException, IOException {
        props.load(new FileInputStream(pathToProps));
        URI uri = new URIBuilder()
                .setScheme(props.getProperty("protocol"))
                .setHost(props.getProperty("host"))
                .setPort(Integer.parseInt(props.getProperty("port")))
                .setPath(path)
                .setParameter("id", id)
                .build();
        System.out.println(uri.toString());
        return uri;
    }

    public JSONObject createAddress(String path) throws IOException, URISyntaxException {
        HttpPost postReq = new HttpPost(buildURI(path));
        JSONClass body = new JSONClass();
        body.fillJsonBodyAddress(props.getProperty("addressData"));
        logger.info("This is json body: " + body.toString());
        HttpEntity stringEntity = new StringEntity(body.toString(), ContentType.APPLICATION_JSON);
        postReq.setEntity(stringEntity);
        HttpResponse response = client.execute(postReq);
        String  jsonResponse = EntityUtils.toString(response.getEntity());
        logger.info("Body as string: " + jsonResponse);
        JSONObject responseBody = new JSONObject(jsonResponse);
        //AddressItem address = new AddressItem(responseBody);
        logger.info("Status line: " + response.getStatusLine());
        logger.info("Response body: " + responseBody.toString());
        //System.out.println(address.toString());
        return responseBody;
    }

    public JSONObject getAddressById(String path, String id) throws URISyntaxException, IOException {
        URI newUri = buildURI(path, id);
        HttpGet getReq = new HttpGet(newUri);
        HttpResponse response = client.execute(getReq);
        String jsonString = EntityUtils.toString(response.getEntity());
        JSONObject responseBody = new JSONObject(jsonString);
        logger.info("This is json body: " + responseBody.toString());
        return responseBody;
    }

    public void getImageById(String path, String id) throws IOException, URISyntaxException {
        HttpGet getImage = new HttpGet(buildURI(path, id));
        HttpResponse response = client.execute(getImage);
        String jsonString = EntityUtils.toString(response.getEntity());
        JSONObject responseBody = new JSONObject(jsonString);
        logger.info("This is json body: " + responseBody.toString());
    }

    public String getAddressPath() {
        return props.getProperty("addressAPI");
    }

    public String getImagePath() {
        return props.getProperty("imagePath");
    }

}
