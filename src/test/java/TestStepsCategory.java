import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class TestStepsCategory {

    private static ClientClass client = new ClientClass();
    private StorageClass storage = new StorageClass();

    @When("create new category")
    public void createCategory() throws IOException {
        storage.setCategoryitem(client.createNewCategory());
    }

    @Then("category appears in database")
    public void verifyCategoryCreated() {
        CategoryItem category1 = storage.getCategoryitem();
        storage.setCategoryitem(category1);
        assertThat(DbClient.checkIfItemExistsCategory(category1.getId()));
    }

    @When("edit category over API")
    public void editCategory() throws IOException {
        storage.setCategoryitem(client.editCategory(storage.getCategoryitem().getId()));
    }

    @Then("category is edited in DB")
    public void verifyCategoryEdited() {
        CategoryItem category = storage.getCategoryitem();
        assertThat(DbClient.findCategoryRecordById(category.getId()).equals(category));
    }

    @When("delete category")
    public void deleteCategory() throws IOException {
        int status = client.deleteCategoryById(storage.getCategoryitem().getId());
        storage.setStatusCode(status);
    }

    @Then("category isn't in database")
    public void verifyCategoryIsDeleted() {
        assertThat(DbClient.checkIfItemExistsCategory(storage.getStatusCode()) == false);
    }
}
