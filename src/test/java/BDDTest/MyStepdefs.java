package BDDTest;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.junit.AfterClass;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class MyStepdefs extends TestInitialization {

    @Before
    public void setUpEnvironment() {
        environmentSetUp();
        statusCode = 0;
        errorMessage = "";
        response = null;
        originalValue = null;
        originalTodoList = null;
        taskList = null;
    }

//    @After
//    public void restoreInitialState() {
//        checkSideEffect();
//    }

    @AfterClass
    public void shutdown() {
        stopServer();
    }

    @Given("the service is running")
    public void theServiceIsRunning() {
        while (!isOnline) {
            try {
                environmentSetUp();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Given("the project name with {string} is not in the system:")
    public void theProjectNameWithIsNotInTheSystem(String arg0) {
        assertNull(findProjectByTitle(arg0));
    }

    @When("user requests to create a project with title {string} and description {string}")
    public void userRequestsToCreateAProjectWithTitleAndDescription(String arg0, String arg1) {
        HttpResponse<JsonNode> res = Unirest.post("/projects").body("{\"title\":\"" + arg0 + "\", \"description\":\"" + arg1 + "\"}").asJson();
        response = res.getBody().getObject();
        statusCode = res.getStatus();
    }

    @Then("the project with title {string} and description {string} should be created:")
    public void theProjectWithTitleAndDescriptionShouldBeCreated(String arg0, String arg1) {
        assertEquals(statusCode, 201);
        assertEquals(response.getString("title"), arg0);
        Unirest.delete("/projects/" + response.getInt("id")).asEmpty();
    }

    @Given("the project name with {string} , completed status {string} is registered in the system:")
    public void theProjectNameWithCompletedStatusIsRegisteredInTheSystem(String arg0, String arg1) {
        while (findProjectByTitle(arg0) == null) {
            try {
                Unirest.post("/projects").body("{\"title\":\"" + arg0 + "\", \"completed\":" + Boolean.parseBoolean(arg1) + "}").asJson();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertNotNull(findProjectByTitle(arg0));
    }

    @When("user requests to change the complete status of the project with title {string}  to {string}")
    public void userRequestsToChangeTheCompleteStatusOfTheProjectWithTitleTo(String arg0, String arg1) {
        int id = findProjectByTitle(arg0).getInt("id");
        HttpResponse<JsonNode> res = Unirest.post("/projects/" + id).body("{\"completed\":" + Boolean.parseBoolean(arg1) + "}").asJson();
        response = res.getBody().getObject();
        statusCode = res.getStatus();
    }

    @Then("the complete status of the project with title {string} will be set to {string}")
    public void theCompleteStatusOfTheProjectWithTitleWillBeSetTo(String arg0, String arg1) {
        assertEquals(statusCode, 200);
        assertEquals(response.getString("completed"), arg1);
        Unirest.delete("/projects/" + response.getInt("id")).asEmpty();
    }

    @When("user requests to create a project with title {string} and active status {string}")
    public void userRequestsToCreateAProjectWithTitleAndActiveStatus(String arg0, String arg1) {
        HttpResponse<JsonNode> res = Unirest.post("/projects").body("{\"title\":\"" + arg0 + "\", \"active\":" + arg1 + "}").asJson();
        response = res.getBody().getObject();
        statusCode = res.getStatus();
    }

    @Then("the system should output an error code of wrong data type of active status: {string}")
    public void theSystemShouldOutputAnErrorCodeOfWrongDataTypeOfActiveStatus(String arg0) {
        assertEquals(statusCode, Integer.parseInt(arg0));
    }

    @Given("the todo name with {string} and description {string} is not in the system:")
    public void theTodoNameWithAndDescriptionIsNotInTheSystem(String arg0, String arg1) {
        while (findTodoByTitle(arg0) != null) {
            try {
                int id = findTodoByTitle(arg0).getInt("id");
                Unirest.delete("/todos/" + id).asEmpty();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertNull(findTodoByTitle(arg0));
    }

    @When("user requests to create a todo with title {string} and description {string}")
    public void userRequestsToCreateATodoWithTitleAndDescription(String arg0, String arg1) {
        HttpResponse<JsonNode> res = Unirest.post("/todos").body("{\"title\":\"" + arg0 + "\", \"description\":\"" + arg1 + "\"}").asJson();
        response = res.getBody().getObject();
        statusCode = res.getStatus();
    }

    @Then("the todo task with title {string} and description {string} should be created:")
    public void theTodoTaskWithTitleAndDescriptionShouldBeCreated(String arg0, String arg1) {
        assertEquals(statusCode, 201);
        assertEquals(response.getString("title"), arg0);
        assertEquals(response.getString("description"), arg1);
        Unirest.delete("/todos/" + response.getInt("id")).asEmpty();
    }

    @Given("the todo task name with {string} ,  done status {string} is registered in the system:")
    public void theTodoTaskNameWithDoneStatusIsRegisteredInTheSystem(String arg0, String arg1) {
        while (findTodoByTitle(arg0) == null) {
            try {
                Unirest.post("/todos").body("{\"title\":\"" + arg0 + "\", \"doneStatus\":" + Boolean.parseBoolean(arg1) + "}").asJson();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertNotNull(findTodoByTitle(arg0));
    }

    @When("user requests to change the  done status of the todo task with title {string}  to {string}")
    public void userRequestsToChangeTheDoneStatusOfTheTodoTaskWithTitleTo(String arg0, String arg1) {
        int id = findTodoByTitle(arg0).getInt("id");
        HttpResponse<JsonNode> res = Unirest.post("/todos/" + id).body("{\"doneStatus\":" + Boolean.parseBoolean(arg1) + "}").asJson();
        response = res.getBody().getObject();
        statusCode = res.getStatus();
    }

    @Then("the  done status of the todo task with title {string} will be set to {string}")
    public void theDoneStatusOfTheTodoTaskWithTitleWillBeSetTo(String arg0, String arg1) {
        assertEquals(statusCode, 200);
        assertEquals(response.getString("doneStatus"), arg1);
        Unirest.delete("/todos/" + response.getInt("id")).asEmpty();
    }

    @Given("the todo name with {string} and done status {string} is not in the system:")
    public void theTodoNameWithAndDoneStatusIsNotInTheSystem(String arg0, String arg1) {
        assertNull(findTodoByTitle(arg0));
    }

    @When("user requests to create a todo with title {string} and done status {string}")
    public void userRequestsToCreateATodoWithTitleAndDoneStatus(String arg0, String arg1) {
        HttpResponse<JsonNode> res = Unirest.post("/todos").body("{\"title\":\"" + arg0 + "\", \"doneStatus\":" + arg1 + "}").asJson();
        response = res.getBody().getObject();
        statusCode = res.getStatus();
    }

    @Then("the system should output an error code of wrong data type of doneStatus: {string}")
    public void theSystemShouldOutputAnErrorCodeOfWrongDataTypeOfDoneStatus(String arg0) {
        assertEquals(statusCode, Integer.parseInt(arg0));
    }

    @Given("the project name with {string} todo name with {string} is in the system:")
    public void theProjectNameWithTodoNameWithIsInTheSystem(String arg0, String arg1) {
        while (findTodoByTitle(arg1) == null) {
            try {
                Unirest.post("/todos").body("{\"title\":\"" + arg1 + "\"}").asJson();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (findProjectByTitle(arg0) == null) {
            try {
                Unirest.post("/projects").body("{\"title\":\"" + arg0 + "\"}").asJson();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertNotNull(findProjectByTitle(arg0));
        assertNotNull(findTodoByTitle(arg1));

    }

    @When("user requests to add a todo task with title {string} to the project with title {string}")
    public void userRequestsToAddATodoTaskWithTitleToTheProjectWithTitle(String arg0, String arg1) {
        int id_1 = findTodoByTitle(arg0).getInt("id");
        int id_2 = findProjectByTitle(arg1).getInt("id");
        HttpResponse<JsonNode> res = Unirest.post("/todos/" + id_1 + "/tasksof").body("{\"id\":\"" + id_2 + "\"}").asJson();
        statusCode = res.getStatus();
    }

    @Then("the a todo task with title {string} will be add to the tasks list of project with title {string}")
    public void theATodoTaskWithTitleWillBeAddToTheTasksListOfProjectWithTitle(String arg0, String arg1) {
        assertEquals(statusCode, 201);
        int id_1 = findTodoByTitle(arg0).getInt("id");
        int id_2 = findProjectByTitle(arg1).getInt("id");
        JSONArray jsonArray = findTodoByTitle(arg0).getJSONArray("tasksof");
        int find_id_2 = 0;
        for (Object proj : jsonArray) {
            JSONObject project = (JSONObject) proj;
            if (project.getInt("id") == id_2) {
                find_id_2 = id_2;
            }
        }
        assertEquals(find_id_2, id_2);

        JSONArray jsonArray_2 = findProjectByTitle(arg1).getJSONArray("tasks");
        int find_id_1 = 0;
        for (Object todo : jsonArray_2) {
            JSONObject todo_task = (JSONObject) todo;
            if (todo_task.getInt("id") == id_1) {
                find_id_1 = id_1;
            }
        }
        assertEquals(find_id_1, id_1);
        Unirest.delete("/todos/" + id_1 + "/tasksof/" + id_2).asEmpty();
        Unirest.delete("/todos/" + id_1).asEmpty();
        Unirest.delete("/projects/" + id_2).asEmpty();
    }

    @Given("the project name with {string} is in the system")
    public void theProjectNameWithIsInTheSystem(String arg0) {
        while (findProjectByTitle(arg0) == null) {
            try {
                Unirest.post("/projects").body("{\"title\":\"" + arg0 + "\"}").asJson();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertNotNull(findProjectByTitle(arg0));
    }

    @And("the todo task name with {string} is not in the system:")
    public void theTodoTaskNameWithIsNotInTheSystem(String arg0) {
        assertNull(findTodoByTitle(arg0));
    }


    @And("the todo name with {string} and description {string} is in the system")
    public void theTodoNameWithAndDescriptionIsInTheSystem(String arg0, String arg1) {
        while (findTodoByTitle(arg0) == null) {
            try {
                Unirest.post("/todos").body("{\"title\":\"" + arg0 + "\"}").asJson();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertNotNull(findTodoByTitle(arg0));
    }

    @When("user requests to add a todo task with title {string} and description {string} to the project with title {string}")
    public void userRequestsToAddATodoTaskWithTitleAndDescriptionToTheProjectWithTitle(String arg0, String arg1, String arg2) {
        int id_1 = findTodoByTitle(arg0).getInt("id");
        int id_2 = findProjectByTitle(arg2) == null ? 999 : findProjectByTitle(arg2).getInt("id");
        HttpResponse<JsonNode> res = Unirest.post("/todos/" + id_1 + "/tasksof").body("{\"id\":\"" + id_2 + "\"}").asJson();
        statusCode = res.getStatus();
    }

    @Then("the system should output an error code of add todo task with non-existent project id: {string}")
    public void theSystemShouldOutputAnErrorCodeOfAddTodoTaskWithNonExistentProjectId(String arg0) {
        assertEquals(statusCode, Integer.parseInt(arg0));
    }

    @Given("the category name with {string} is not in the system")
    public void theCategoryNameWithIsNotInTheSystem(String arg0) {
        while (findCategoryByTitle(arg0) != null) {
            try {
                int id = findCategoryByTitle(arg0).getInt("id");
                Unirest.delete("/categories/" + id).asEmpty();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertNull(findCategoryByTitle(arg0));
    }

    @When("user requests to create a category with title {string} and description of {string}")
    public void userRequestsToCreateACategoryWithTitleAndDescriptionOf(String arg0, String arg1) {
        HttpResponse<JsonNode> res = Unirest.post("/categories").body("{\"title\":\"" + arg0 + "\", \"description\":\"" + arg1 + "\"}").asJson();
        response = res.getBody().getObject();
        statusCode = res.getStatus();
    }

    @Then("the category with title {string} and description {string} should be created")
    public void theCategoryWithTitleAndDescriptionShouldBeCreated(String arg0, String arg1) {
        assertEquals(statusCode, 201);
        assertEquals(response.getString("title"), arg0);
        assertEquals(response.getString("description"), arg1);
        Unirest.delete("/categories/" + response.getInt("id")).asEmpty();
    }

    @When("user requests to create a category with title {string}")
    public void userRequestsToCreateACategoryWithTitle(String arg0) {
        HttpResponse<JsonNode> res = Unirest.post("/categories").body("{\"title\":\"" + arg0 + "\"}").asJson();
        response = res.getBody().getObject();
        statusCode = res.getStatus();
    }

    @Then("the category with title {string} should be created")
    public void theCategoryWithTitleShouldBeCreated(String arg0) {
        assertEquals(statusCode, 201);
        assertEquals(response.getString("title"), arg0);
        Unirest.delete("/categories/" + response.getInt("id")).asEmpty();
    }

    @Given("the category name description of {string} is not in the system")
    public void theCategoryNameDescriptionOfIsNotInTheSystem(String arg0) {
        Boolean response = Unirest.get("/categories?description=" + arg0).asJson().getBody().getObject().getJSONArray("categories").isEmpty();
        assertEquals(response, true);
    }

    @When("user requests to create a category with description {string}")
    public void userRequestsToCreateACategoryWithDescription(String arg0) {
        HttpResponse<JsonNode> res = Unirest.post("/categories").body("{\"description\":\"" + arg0 + "\"}").asJson();
        response = res.getBody().getObject();
        statusCode = res.getStatus();
    }

    @Then("the system should output an error code of create category without title: {string}")
    public void theSystemShouldOutputAnErrorCodeOfCreateCategoryWithoutTitle(String arg0) {
        assertEquals(statusCode, Integer.parseInt(arg0));
    }

    @And("there are these categories in the system:")
    public void thereAreTheseCategoriesInTheSystem(DataTable table) {
        List<List<String>> rows = table.asLists(String.class);

        boolean firstLine = true;
        for (List<String> columns : rows) {
            if (!firstLine) {
                Unirest.post("/categories")
                        .body("{\n\"description\":\"" + columns.get(1) + "\",\n  \"title\":\""
                                + columns.get(0) + "\"\n}")
                        .asJson();
                assertNotNull(findCategoryByTitle(columns.get(0)));
            }
            firstLine = false;
        }
    }

    @Given("the todo task with name {string} registered in the system")
    public void theTodoTaskWithNameRegisteredInTheSystem(String arg0) {
        while (findTodoByTitle(arg0) == null) {
            try {
                Unirest.post("/todos").body("{\"title\":\"" + arg0 + "\"}").asJson();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertNotNull(findTodoByTitle(arg0));
    }

    @When("user requests to categorize a task with {string} as {string}")
    public void userRequestsToCategorizeATaskWithAs(String arg0, String arg1) {
        int id_1 = findTodoByTitle(arg0).getInt("id");
        int id_2 = findCategoryByTitle(arg1).getInt("id");
        HttpResponse<JsonNode> res = Unirest.post("/categories/" + id_2 + "/todos").body("{\"id\":\"" + id_1 + "\"}").asJson();
        statusCode = res.getStatus();
    }

    @Then("the todo task with name {string} should be in category {string}")
    public void theTodoTaskWithNameShouldBeInCategory(String arg0, String arg1) {
        assertEquals(statusCode, 201);
        int id_1 = findTodoByTitle(arg0).getInt("id");
        int id_2 = findCategoryByTitle(arg1).getInt("id");
        Boolean hasCategories = findTodoByTitle(arg0).has("categories");
        assertTrue(hasCategories);
        if (hasCategories) {
            JSONArray jsonArray = findTodoByTitle(arg0).getJSONArray("categories");
            int find_id_2 = 0;
            for (Object proj : jsonArray) {
                JSONObject project = (JSONObject) proj;
                if (project.getInt("id") == id_2) {
                    find_id_2 = id_2;
                }
            }
            assertEquals(find_id_2, id_2);
        }
        Boolean hasTodos = findTodoByTitle(arg0).has("todos");
        assertTrue(hasTodos);
        if (hasTodos) {
            JSONArray jsonArray_2 = findCategoryByTitle(arg1).getJSONArray("todos");
            int find_id_1 = 0;
            for (Object todo : jsonArray_2) {
                JSONObject todo_task = (JSONObject) todo;
                if (todo_task.getInt("id") == id_1) {
                    find_id_1 = id_1;
                }
            }
            assertEquals(find_id_1, id_1);
        }

        Unirest.delete("/categories/" + id_2 + "/todos/" + id_1).asEmpty();
        Unirest.delete("/todos/" + id_1).asEmpty();
        Unirest.delete("/categories/" + id_2).asEmpty();

    }

    @And("the todo task with name {string} has a {string} category")
    public void theTodoTaskWithNameHasACategory(String arg0, String arg1) {
        while (findTodoByTitle(arg0) == null) {
            try {
                Unirest.post("/todos").body("{\"title\":\"" + arg0 + "\"}").asJson();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int id_1 = findTodoByTitle(arg0).getInt("id");
        int id_2 = findCategoryByTitle(arg1).getInt("id");
        while (findTodoByTitle(arg0).has("categories") == false) {
            Unirest.post("/todos/" + id_1 + "/categories").body("{\"id\":\"" + id_2 + "\"}").asJson();
        }
        assertNotNull(findTodoByTitle(arg0));
        JSONArray jsonArray = findTodoByTitle(arg0).getJSONArray("categories");
        int find_id_2 = 0;
        for (Object proj : jsonArray) {
            JSONObject project = (JSONObject) proj;
            if (project.getInt("id") == id_2) {
                find_id_2 = id_2;
            }
        }
        assertEquals(find_id_2, id_2);
    }

    @And("the category with title {string} registered in the system")
    public void theCategoryWithTitleRegisteredInTheSystem(String arg0) {
        while (findCategoryByTitle(arg0) == null) {
            try {
                Unirest.post("/categories").body("{\"title\":\"" + arg0 + "\"}").asJson();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertNotNull(findCategoryByTitle(arg0));
    }

    @When("user tries to remove {string} category categorization from {string}")
    public void userTriesToRemoveCategoryCategorizationFrom(String arg0, String arg1) {
        int id_1 = findTodoByTitle(arg1).getInt("id");
        int id_2 = findCategoryByTitle(arg0).getInt("id");
        Unirest.delete("/todos/" + id_1 + "/categories/" + id_2).asEmpty();
    }

    @And("user requests to categorize a todo task with {string} with a new category {string}")
    public void userRequestsToCategorizeATodoTaskWithWithANewCategory(String arg0, String arg1) {
        int id_1 = findTodoByTitle(arg0).getInt("id");
        int id_2 = findCategoryByTitle(arg1).getInt("id");
        HttpResponse<JsonNode> res = Unirest.post("/todos/" + id_1 + "/categories").body("{\"id\":\"" + id_2 + "\"}").asJson();
        response = res.getBody().getObject();
        statusCode = res.getStatus();
    }

    @Then("the todo task {string} should be a task with {string} category")
    public void theTodoTaskShouldBeATaskWithCategory(String arg0, String arg1) {
        assertEquals(statusCode, 201);
        int id_1 = findTodoByTitle(arg0).getInt("id");
        int id_2 = findCategoryByTitle(arg1).getInt("id");

        Boolean hasCategories = findTodoByTitle(arg0).has("categories");
        assertTrue(hasCategories);
        if (hasCategories) {
            JSONArray jsonArray = findTodoByTitle(arg0).getJSONArray("categories");
            int find_id_2 = 0;
            for (Object proj : jsonArray) {
                JSONObject project = (JSONObject) proj;
                if (project.getInt("id") == id_2) {
                    find_id_2 = id_2;
                }
            }
            assertEquals(find_id_2, id_2);
        }
        Boolean hasTodos = findCategoryByTitle(arg1).has("todos");
        assertTrue(hasTodos);
        if (hasTodos) {
            JSONArray jsonArray_2 = findCategoryByTitle(arg1).getJSONArray("todos");
            int find_id_1 = 0;
            for (Object todo : jsonArray_2) {
                JSONObject todo_task = (JSONObject) todo;
                if (todo_task.getInt("id") == id_1) {
                    find_id_1 = id_1;
                }
            }
            assertEquals(find_id_1, id_1);
        }
        Unirest.delete("/todos/" + id_1 + "/categories/" + id_2).asEmpty();
        Unirest.delete("/todos/" + id_1).asEmpty();
        Unirest.delete("/categories/" + id_2).asEmpty();
    }

    @Given("the todo task with name {string} is not in the system")
    public void theTodoTaskWithNameIsNotInTheSystem(String arg0) {
        assertNull(findTodoByTitle(arg0));
    }

    @When("user requests to categorize the todo task with name {string} as {string}")
    public void userRequestsToCategorizeTheTodoTaskWithNameAs(String arg0, String arg1) {
        int id_1 = findTodoByTitle(arg0) == null ? 999 : findTodoByTitle(arg0).getInt("id");
        int id_2 = findCategoryByTitle(arg1).getInt("id");
        HttpResponse<JsonNode> res = Unirest.post("/todos/" + id_1 + "/categories").body("{\"id\":\"" + id_2 + "\"}").asJson();
        response = res.getBody().getObject();
        statusCode = res.getStatus();
    }

    @Then("the system should output an error message of categotrizing a non-existent todo task: {string}")
    public void theSystemShouldOutputAnErrorMessageOfCategotrizingANonExistentTodoTask(String arg0) {
        assertEquals(statusCode, Integer.parseInt(arg0));
    }

    @Given("the project name with {string} is not in the system")
    public void theProjectNameWithIsNotInTheSystem2(String arg0) {
        assertNull(findProjectByTitle(arg0));
    }

    @And("the category name with {string} is in the system")
    public void theCategoryNameWithIsInTheSystem(String arg0) {
        while (findCategoryByTitle(arg0) == null) {
            try {
                Unirest.post("/categories").body("{\"title\":\"" + arg0 + "\"}").asJson();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertNotNull(findCategoryByTitle(arg0));
    }
}
