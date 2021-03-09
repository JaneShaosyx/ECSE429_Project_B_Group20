package BDDTest;


import domain.Category;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class GetCategoryTestStepdefs {

    private HttpResponse<JsonNode> getCategoryByIdReponse;
    private List<Category> oldCategoryList;
    private List<Category> nowCategoryList;

    @Given("the category with id {string} and title {string} is existed in the system:")
    public void theCategoryWithIdAndTitleIsExistedInTheSystem(String categoryId, String categoryTitle) {
        boolean isExists = getAllCategory().stream().filter(cate -> categoryId.equals(cate.getId()) && categoryTitle.equals(cate.getTitle()))
                .findAny().isPresent();
        assertTrue(isExists);
    }


    @When("user requests to get a category with id {string}")
    public void userRequestsToGetACategoryWithId(String categoryId) {
        this.getCategoryByIdReponse = getResponseById(categoryId);
    }


    @Then("only the category with id {string} and title {string} should be returned")
    public void onlyTheCategoryWithIdAndTitleShouldBeReturned(String categoryId, String categoryTitle) {
        List<Category> categorylist = convertToCategoryList(this.getCategoryByIdReponse.getBody().getObject());
        assertEquals(categorylist.size(), 1);
        Category category = categorylist.get(0);
        assertEquals(category.getId(), categoryId);
        assertEquals(category.getTitle(), categoryTitle);
    }

    @Given("the category list size is known and does not include the category with the given title {string}")
    public void theCategoryListSizeIsKnownAndDoesNotIncludeTheCategoryWithTheGivenTitle(String categoryTitle) {
        List<Category> categoryList = getAllCategory();
        assertFalse(categoryList.stream().filter(cate -> categoryTitle.equals(cate.getTitle())).findAny().isPresent());
        this.oldCategoryList = categoryList;
    }

    @When("user add a new category with title {string}")
    public void userAddANewCategoryWithTitle(String categoryTitle) {

        HttpResponse<JsonNode> response = createCategory(categoryTitle, null);
        Category created = convertToCatory(response.getBody().getObject());
        assertTrue(categoryTitle.equals(created.getTitle()));

    }

    @And("requests to get all the instances of categories")
    public void requestsToGetAllTheInstancesOfCategories() {

        this.nowCategoryList = getAllCategory();
    }

    @Then("the system should return instances of categories in the updated category list contains a new one with the given title {string} and the size of the category list will be incremented.")
    public void theSystemShouldReturnInstancesOfCategoriesInTheUpdatedCategoryListContainsANewOneWithTheGivenTitleAndTheSizeOfTheCategoryListWillBeIncremented(String categoryTitle) {
        assertTrue(this.nowCategoryList.size() == this.oldCategoryList.size() + 1);
        Optional<Category> categoryOptional = this.nowCategoryList.stream().filter(category -> categoryTitle.equals(category.getTitle())).findAny();
        assertTrue(categoryOptional.isPresent());
    }

    @Given("the category list is not empty")
    public void theCategoryListIsNotEmpty() {

        assertFalse(getAllCategory().isEmpty());
    }

    @Then("Then the system should output an error code of no category instance founded: {string}")
    public void thenTheSystemShouldOutputAnErrorCodeOfNoCategoryInstanceFounded(String code) {
        assertEquals(""+this.getCategoryByIdReponse.getStatus(), code);
    }


    static List<Category> getAllCategory() {
        HttpResponse<JsonNode> response = Unirest.get("/categories").asJson();
        return convertToCategoryList(response.getBody().getObject());
    }

    public static List<Category> convertToCategoryList(JSONObject response) {
        List<Category> categoryList = new ArrayList();
        if (response.has("categories")) {
            for (Object category : response.getJSONArray("categories")) {
                JSONObject categoryObject = (JSONObject) category;
                categoryList.add(convertToCatory(categoryObject));
            }
        }
        return categoryList;
    }


    private static Category convertToCatory(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String description = jsonObject.has("description") ? jsonObject.getString("description") : null;
        String id = jsonObject.has("id") ? jsonObject.getString("id") : null;
        return new Category(id, title, description);
    }


    private HttpResponse<JsonNode> getResponseById(String categoryId) {
        return Unirest.get("/categories/" + categoryId).asJson();
    }


    public static HttpResponse<JsonNode> createCategory(String title, String description) {
        return Unirest.post("/categories").body("{\"title\":\"" + title + "\", \"description\":\"" + description + "\"}").asJson();
    }




}