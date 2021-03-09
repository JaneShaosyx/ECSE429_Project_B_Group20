package BDDTest;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TodoDeleteTestStepdefs  {
    @Given("the todo with id {string} is existed in the system:")
    public void theTodoWithIdIsExistedInTheSystem(String arg0) {
    }

    @When("user requests to delete the todo with id {string}")
    public void userRequestsToDeleteTheTodoWithId(String arg0) {
    }

    @And("request to find the todo with id {string}")
    public void requestToFindTheTodoWithId(String arg0) {
    }

    @Then("the system should output an error code of no todo instance founded: {string}")
    public void theSystemShouldOutputAnErrorCodeOfNoTodoInstanceFounded(String arg0) {
    }

    @When("user requests to delete the todo with title {string}")
    public void userRequestsToDeleteTheTodoWithTitle(String arg0) {
    }

    @Then("the todo with id {string} will not found in the all categories result")
    public void theTodoWithIdWillNotFoundInTheAllCategoriesResult(String arg0) {
    }

    @Then("Then the system should output an error code of no todo instance founded: {string}")
    public void thenTheSystemShouldOutputAnErrorCodeOfNoTodoInstanceFounded(String arg0) {
    }
}
