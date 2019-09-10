import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class TestStepsImages {
    private static ClientClass client = new ClientClass();
    private StorageClass storage = new StorageClass();

    @When("creating new image from API")
    public void createNewImage() throws IOException {
        storage.setImageItem(client.createNewImage());
    }

    @Then("image appears in DB")
    public void verifyImageCreated() {
        assertThat(DbClient.checkIfItemExistsImage(storage.getImageItem().getId()));
    }

    @When("edit image from API")
    public void editImage() throws IOException {
        storage.setImageItem(client.editImage(storage.getImageItem().getId()));
    }

    @Then("image is edited in DB")
    public void verifyImageEdited() {
        ImageItem image = storage.getImageItem();
        assertThat(DbClient.findImageRecordById(image.getId()).equals(image));
    }

    @When("delete image from API")
    public void deleteImageById() throws IOException {
        storage.setStatusCode(client.deleteImageById(storage.getImageItem().getId()));
    }

    @Then("")
    public void verifyImageIsDeleted() {
        assertThat(DbClient.checkIfItemExistsImage(storage.getImageItem().getId())).isFalse();
        assertThat(storage.getStatusCode() == 200);
    }


}
