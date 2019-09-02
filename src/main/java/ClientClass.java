import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.swing.plaf.synth.SynthUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class ClientClass {

    private HttpClient client;
    private URI uri;
    private String addressGetList = "/list";

    private Properties props = new Properties();
    private final String pathToProps= "src/main/resources/config.properties";

    final static Logger logger = Logger.getLogger(ClientClass.class);

    ClientClass() throws IOException {
        client = HttpClients.createDefault();
        props.load(new FileInputStream(pathToProps));
    }

    public URI buildURI(String path) throws URISyntaxException, IOException {
        URI uri = new URIBuilder()
                .setScheme(props.getProperty("protocol"))
                .setHost(props.getProperty("host"))
                .setPort(Integer.parseInt(props.getProperty("port")))
                .setPath(path)
                .build();
        System.out.println(uri.toString());
        return uri;
    }

    public URI buildURI(String path, String id) throws URISyntaxException, IOException {
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

    public AddressItem createAddress(String street) throws IOException, URISyntaxException {
        HttpPost postReq = new HttpPost(buildURI(getAddressPath()));
        AddressItem requestAddress = new AddressItem(props.getProperty("addressData"), street);
        Gson gson = new Gson();
        String requestBody = gson.toJson(requestAddress);
        logger.info("Request body: " + requestAddress.toString());
        HttpEntity stringEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
        postReq.setEntity(stringEntity);
        HttpResponse response = client.execute(postReq);
        String  responseBody = EntityUtils.toString(response.getEntity());
        AddressItem responseAddress = gson.fromJson(responseBody, AddressItem.class);
        logger.info("Response body: " + responseAddress.toString());
        return responseAddress;
    }

    public AddressItem getAddressById(String id) throws URISyntaxException, IOException {
        HttpGet getReq = new HttpGet(buildURI(getAddressPath(), id));
        HttpResponse response = client.execute(getReq);
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println("This is response code: " + response.getStatusLine());
        if (getStatusCode(response).contains("200") || getStatusCode(response).contains("201")) {
            logger.info(responseBody);
            Gson gson = new Gson();
            AddressItem addressResponse = gson.fromJson(responseBody, AddressItem.class);
            logger.info("This is json body: " + addressResponse.toString());
            return addressResponse;
        }
        else
            return null;
    }

    public int deleteAddressById(String id) throws IOException, URISyntaxException {
        HttpDelete deleteReq = new HttpDelete(buildURI(getAddressPath(), id));
        HttpResponse response = client.execute(deleteReq);
        StatusLine statusLine = response.getStatusLine();
        return  statusLine.getStatusCode();
    }

    public AddressItem getAddressByName(String streetName) throws IOException, URISyntaxException {
        HttpGet getReq = new HttpGet(buildURI(getAddressPath() + addressGetList));
        HttpResponse response = client.execute(getReq);
        String responseBody = EntityUtils.toString(response.getEntity());
        Gson gson = new Gson();
        AddressItem[] addressResponse = gson.fromJson(responseBody, AddressItem[].class);
        for (AddressItem adr: addressResponse)
            if (adr.getStreetName().equalsIgnoreCase(streetName))
                return adr;
        return null;
    }

    public CardItem addPaymentCard(String ownerName) throws IOException, URISyntaxException {
        HttpPost postRequest = new HttpPost(buildURI(getCardPath()));
        CardItem requestCard = new CardItem(props.getProperty("cardData"), ownerName);
        System.out.println(requestCard.toString());
        Gson gson = new Gson();
        String requestBody = gson.toJson(requestCard);
        logger.info("Request body: " + requestCard.toString());
        HttpEntity stringEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
        postRequest.setEntity(stringEntity);
        HttpResponse response = client.execute(postRequest);
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println("This is reponse body card: " + responseBody);
        CardItem responseItem = gson.fromJson(responseBody, CardItem.class);
        logger.info("Response body: " + responseItem.toString());
        return responseItem;
    }

    public CardItem getCardById(String id) throws IOException, URISyntaxException {
        HttpGet getRequest = new HttpGet(buildURI(getCardPath(), id));
        HttpResponse response = client.execute(getRequest);
        String responseBody = EntityUtils.toString(response.getEntity());
        if (getStatusCode(response).contains("200") || getStatusCode(response).contains("201")) {
            Gson gson = new Gson();
            CardItem responseItem = gson.fromJson(responseBody, CardItem.class);
            return responseItem;
        }
        else
            return null;

    }

    public String getAddressPath() {
        return props.getProperty("addressAPI");
    }

    public String getCardPath() {
        return props.getProperty("cardAPI");
    }

    public String getStatusCode(HttpResponse response) {
        return response.getStatusLine().toString();
    }

}
