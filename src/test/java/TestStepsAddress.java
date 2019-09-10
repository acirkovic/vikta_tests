import org.hibernate.Session;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class TestStepsAddress {

    private static ClientClass client = new ClientClass();
    private StorageClass storage = new StorageClass();

    @When("the name for street is $streetName")
    public void changeNameOfStreet(@Named("streetName") String street) throws IOException {
        storage.setAddressItem(client.createAddress(client.getNewAddressData(), street));
    }

    @Then("address is successfully created")
    public void assertStreet() {
        assertThat(DbClient.checkIfItemExistsAddress(storage.getAddressItem().getId()));
    }

    @When("edit address via API and name is $streetName")
    public void editAddress(@Named("streetName") String streetName) throws IOException {
        storage.setAddressItem(client.updateAddress(client.getEditAddressData(), streetName, storage.getAddressItem().getId()));
    }

    @Then("changes are saved in DB")
    public void assertAddressIsEdited() {
        AddressItem address1 = storage.getAddressItem();
        int id = address1.getId();
        AddressItem address2 = DbClient.findAddressRecordById(id);
        storage.setAddressItem(address2);
        assertThat(address2.equals(address1));
    }

    @When("delete through API and street name is $streetName")
    public void deleteAddress(@Named("streetName") String streetName) throws IOException {
        AddressItem addressToDelete = storage.getAddressItem();
        int statusCode = client.deleteAddressById(String.valueOf(addressToDelete.getId()));
        storage.setStatusCode(statusCode);
    }

    @Then("street is deleted, and no entry is in database")
    public void assertDeleteIsSuccessful() {
        AddressItem deletedAddress = storage.getAddressItem();
        assertThat(DbClient.checkIfItemExistsAddress(deletedAddress.getId()) == false);
        assertThat(storage.getStatusCode()).isEqualTo(200);
    }


}
