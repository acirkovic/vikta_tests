import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class ClientClass {

    private HttpClient client;
    private Gson gson;
    private String addressGetList = "/list";

    private Properties props = new Properties();
    private final String pathToProps= "src/main/resources/config.properties";

    final static Logger logger = Logger.getLogger(ClientClass.class);

    ClientClass() {
        client = HttpClients.createDefault();
        gson = new Gson();
        try {
            props.load(new FileInputStream(pathToProps));
        } catch (IOException ioEx) {
            logger.info("Something wrong with file. Message: " + ioEx);
        }
    }

    public URI buildURI(String path) {
        try {
            URI uri = new URIBuilder()
                    .setScheme(props.getProperty("protocol"))
                    .setHost(props.getProperty("host"))
                    .setPort(Integer.parseInt(props.getProperty("port")))
                    .setPath(path)
                    .build();
            System.out.println(uri.toString());
            return uri;
        } catch (URISyntaxException uriEx) {
            logger.info("Syntax wrong at URI.");
            return null;
        }

    }

    public URI buildURI(String path, String id) {
        try {
            URI uri = new URIBuilder()
                    .setScheme(props.getProperty("protocol"))
                    .setHost(props.getProperty("host"))
                    .setPort(Integer.parseInt(props.getProperty("port")))
                    .setPath(path)
                    .setParameter("id", id)
                    .build();
            System.out.println(uri.toString());
            return uri;
        } catch (URISyntaxException uriEx) {
            logger.info("Syntax wrong at URI.");
            return null;
    }

}

    public AddressItem createAddress(String addressData, String street) throws IOException {
        HttpPost postReq = new HttpPost(buildURI(getAddressPath()));
        AddressItem requestAddress = new AddressItem(addressData, street);
        Gson gson = new Gson();
        String requestBody = gson.toJson(requestAddress);
        logger.info("Request body: " + requestAddress.toString());
        HttpEntity stringEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
        postReq.setEntity(stringEntity);
        HttpResponse response = client.execute(postReq);
        String  responseBody = EntityUtils.toString(response.getEntity());
        AddressItem responseAddress = gson.fromJson(responseBody, AddressItem.class);
        logger.info("Response message: " + response.getStatusLine().getReasonPhrase() + " "  + response.getStatusLine().getStatusCode());
        logger.info("Response body: " + responseAddress.toString());
        return responseAddress;
    }

    public AddressItem updateAddress(String editAddressData, String streetName, int id) throws IOException {
        HttpPut postReq = new HttpPut(buildURI(getAddressPath()));
        AddressItem requestAddress = new AddressItem(editAddressData, streetName);
        requestAddress.setId(id);
        Gson gson = new Gson();
        String requestBody = gson.toJson(requestAddress);
        logger.info("Request body: " + requestAddress.toString());
        HttpEntity stringEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
        postReq.setEntity(stringEntity);
        HttpResponse response = client.execute(postReq);
        String  responseBody = EntityUtils.toString(response.getEntity());
        AddressItem responseAddress = gson.fromJson(responseBody, AddressItem.class);
        logger.info("Response message: " + response.getStatusLine().getReasonPhrase() + " "  + response.getStatusLine().getStatusCode());
        logger.info("Response body: " + responseAddress.toString());
        return responseAddress;
    }

    public AddressItem getAddressById(String id) throws IOException {
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

    public int deleteAddressById(String id) throws IOException {
        HttpDelete deleteReq = new HttpDelete(buildURI(getAddressPath(), id));
        HttpResponse response = client.execute(deleteReq);
        StatusLine statusLine = response.getStatusLine();
        return  statusLine.getStatusCode();
    }

    public AddressItem getAddressByName(String streetName) throws IOException {
        HttpGet getReq = new HttpGet(buildURI(getAddressPath() + addressGetList));
        HttpResponse response = client.execute(getReq);
        String responseBody = EntityUtils.toString(response.getEntity());
        Gson gson = new Gson();
        AddressItem[] addressResponse = gson.fromJson(responseBody, AddressItem[].class);
        for (AddressItem adr: addressResponse)
            if (adr.getStreet().equalsIgnoreCase(streetName))
                return adr;
        return null;
    }

    public CardItem addPaymentCard(String ownerName) throws IOException {
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

    public CardItem getCardById(String id) throws IOException {
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

    public CategoryItem createNewCategory() throws IOException {
        HttpPost postRequest = new HttpPost(buildURI(getCategoryPath()));
        CategoryItem category = new CategoryItem(props.getProperty("categoryData"));
        Gson gson = new Gson();
        String requestBody = gson.toJson(category);
        logger.info("Request body: " + requestBody);
        postRequest.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
        HttpResponse response = client.execute(postRequest);
        CategoryItem responseCategory = gson.fromJson(EntityUtils.toString(response.getEntity()), CategoryItem.class);
        logger.info("Response body: " + responseCategory.toString());
        return responseCategory;
    }

    public CategoryItem editCategory(int id) throws IOException {
        HttpPut putRequest = new HttpPut(buildURI(getCategoryPath()));
        CategoryItem category = new CategoryItem(props.getProperty("editCategoryData"));
        category.setId(id);
        String requestBody = gson.toJson(category);
        putRequest.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
        HttpResponse response = client.execute(putRequest);
        CategoryItem categoryResponse = gson.fromJson(EntityUtils.toString(response.getEntity()), CategoryItem.class);
        return categoryResponse;
    }

    public int deleteCategoryById(int id) throws IOException {
        HttpDelete deleteRequest = new HttpDelete(buildURI(getCategoryPath(), String.valueOf(id)));
        HttpResponse response = client.execute(deleteRequest);
        return response.getStatusLine().getStatusCode();
    }

    public ImageItem createNewImage() throws IOException {
        HttpPost postRequest = new HttpPost(buildURI(getImagePath()));
        ImageItem image = new ImageItem(props.getProperty("imageData"));
        String requestBody = gson.toJson(image);
        logger.info("Request body image: " + requestBody);
        postRequest.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
        HttpResponse response = client.execute(postRequest);
        ImageItem responseImage = gson.fromJson(EntityUtils.toString(response.getEntity()), ImageItem.class);
        logger.info("Response body image: " + responseImage.toString());
        return responseImage;
    }

    public ImageItem editImage(int id) throws IOException {
        HttpPut putRequest = new HttpPut(buildURI(getImagePath()));
        ImageItem image = new ImageItem(props.getProperty("editImageData"));
        image.setId(id);
//        image.setCategoryIds(new int[5]);
//        image.setTags(new String[5]);
        String requestBody = gson.toJson(image);
        logger.info("Request image for put: " + image.toString());
        putRequest.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
        HttpResponse response = client.execute(putRequest);
        ImageItem responseImage = gson.fromJson(EntityUtils.toString(response.getEntity()), ImageItem.class);
        logger.info("Response image for put: " + responseImage.toString());
        return responseImage;
    }

    public int deleteImageById(int id) throws IOException {
        HttpDelete deleteRequest = new HttpDelete(buildURI(getImagePath(), String.valueOf(id)));
        HttpResponse response = client.execute(deleteRequest);
        return response.getStatusLine().getStatusCode();
    }

    public String getAddressPath() {
        return props.getProperty("addressAPI");
    }

    public String getNewAddressData() {
        return props.getProperty("addressData");
    }

    public String getEditAddressData() {
        return props.getProperty("editAddressData");
    }

    public String getCardPath() {
        return props.getProperty("cardAPI");
    }

    public String getCategoryPath() {
        return props.getProperty("categoryAPI");
    }

    public String getImagePath() {
        return props.getProperty("imageAPI");
    }

    public String getStatusCode(HttpResponse response) {
        return response.getStatusLine().toString();
    }


}
