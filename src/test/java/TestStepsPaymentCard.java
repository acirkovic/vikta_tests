import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class TestStepsPaymentCard {
    private static ClientClass client;

    static {
        try {
            client = new ClientClass();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    private StorageClass storage = new StorageClass();

    @Given("a user wants to add new payment card")
    public void userWantsToAddCard() {

    }

    @When("he provides card number and owned name $name")
    public void userAddsCard(@Named("name") String ownerName) throws IOException, URISyntaxException {
        storage.addToStorage("card1", client.addPaymentCard(ownerName));
    }

    @Then("card is successfully added")
    public void cardIsAdded() throws IOException, URISyntaxException {
        CardItem card1 = (CardItem) storage.getFromStorage("card1");
        int id = card1.getID();
        CardItem card2 = client.getCardById(String.valueOf(id));
        storage.addToStorage("address2", card2);
        assertThat(card2.getID()).isEqualTo(id);
    }
}
