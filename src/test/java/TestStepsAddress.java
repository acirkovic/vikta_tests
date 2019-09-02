import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class TestStepsAddress {

    private static ClientClass client;

    static {
        try {
            client = new ClientClass();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    private StorageClass storage = new StorageClass();

    @Given("user wants to add new address")
    public void createClient() {

    }

    @When("the name for street is $streetName")
    public void changeNameOfStreet(@Named("streetName") String street) throws IOException, URISyntaxException {
        storage.addToStorage("address1", client.createAddress(street));
    }

    @Then("address is successfully created")
    public void assertStreet() throws IOException, URISyntaxException {
        AddressItem address1 = (AddressItem) storage.getFromStorage("address1");
        int id = address1.getID();
        AddressItem address2 = client.getAddressById(String.valueOf(id));
        storage.addToStorage("address2", address2);
        assertThat(address2.getID()).isEqualTo(id);
    }

    @Then("status code is 200")
    public void assertStatusCode() {

    }

    @Given("user wants to delete address")
    public void wantToDelete() {

    }

    @When("street name is $streetName")
    public void deleteAddress(@Named("streetName") String streetName) throws IOException, URISyntaxException {
        AddressItem addressToDelete = client.getAddressByName(streetName);
        storage.addToStorage("addressToDelete", addressToDelete);
        int statusCode = client.deleteAddressById(String.valueOf(addressToDelete.getID()));
        storage.addToStorage("deleteStatusCode", statusCode);
    }

    @Then("street is successfully deleted")
    public void assertDeleteIsSuccessful() throws IOException, URISyntaxException {
        AddressItem deletedAddress = (AddressItem) storage.getFromStorage("addressToDelete");
        AddressItem address2 = client.getAddressById(String.valueOf(deletedAddress.getID()));
        assertThat(address2).isNull();
        assertThat(storage.getFromStorage("deleteStatusCode")).isEqualTo(200);
    }


}
