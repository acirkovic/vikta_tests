import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class TestSteps {

    private static ClientClass client = new ClientClass();;
    private JSONObject jsonResponse1 = null;

    @Given("user wants to add new address")
    public void createClient() {

    }

    @When("the name for street is $streetName")
    public void changeNameOfStreet() throws IOException, URISyntaxException {
        jsonResponse1 = client.createAddress(client.getAddressPath());
    }

    @Then("address is successfully created")
    public void assertStreet() throws IOException, URISyntaxException {
        int id = jsonResponse1.getInt("id");
        JSONObject addressResponse = client.getAddressById(client.getAddressPath(), String.valueOf(id));
        assertThat(jsonResponse1.get("id")).isEqualTo(addressResponse.get("id"));
    }

    @Given("a user wants to fetch an image")
    public void imageO() {

    }

    @When("image id is $id")
    public void getImage(@Named("id") int id) throws IOException, URISyntaxException {
        //client.getImageById(client.getImagePath(), String.valueOf(id));
    }
}
