import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class TestSteps {

    ClientClass client;
    String id;

    @Given("the path is $path")
    public void createClient(@Named("path") String path) throws URISyntaxException {
        client = new ClientClass(path);
    }

    @When("the name for street is $streetName")
    public void changeNameOfStreet(@Named("streetName") String streetName) throws IOException {
        id = client.createAddress(streetName);
    }

    @Then("addresss is changed to $streetName")
    public void assertStreet(@Named("streetName") String streetName) throws IOException, URISyntaxException {
        client.getAddressById(id);
    }
}
