package BDDTest;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CategoryDeleteTestStepdefs {


    @Given("the category with id {string} is existed in the system:")
    public void theCategoryWithIdIsExistedInTheSystem(String categoryId) {
    }

    @When("user requests to delete the category with id {string}")
    public void userRequestsToDeleteTheCategoryWithId(String arg0) {
    }

    @And("request to find the category with id {string}")
    public void requestToFindTheCategoryWithId(String arg0) {
    }

    @Then("the system should output an error code of no category instance founded: {string}")
    public void theSystemShouldOutputAnErrorCodeOfNoCategoryInstanceFounded(String arg0) {
    }

    @When("user requests to delete the category with title {string}")
    public void userRequestsToDeleteTheCategoryWithTitle(String arg0) {
    }

    @And("request to find all categories")
    public void requestToFindAllCategories() {
    }

    @Then("the category with id {string} will not found in the all categories result")
    public void theCategoryWithIdWillNotFoundInTheAllCategoriesResult(String arg0) {
    }
}
