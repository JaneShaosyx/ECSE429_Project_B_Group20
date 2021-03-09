package BDDTest;

import domain.Project;
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

public class ProjectTestStepdefs {

    private HttpResponse<JsonNode> projectByIdReponse;
    private List<Project> oldProjectList;
    private List<Project> nowProjectList;

    @Given("the project with id {string} and title {string} is existed in the system:")
    public void theProjectWithIdAndTitleIsExistedInTheSystem(String projectId, String projectTitle) {
        boolean isExists = getAllProject().stream().filter(cate -> projectId.equals(cate.getId()) && projectTitle.equals(cate.getTitle()))
                .findAny().isPresent();
        assertTrue(isExists);
    }

    @When("user requests to get a project with id {string}")
    public void userRequestsToGetAProjectWithId(String projectId) {
        this.projectByIdReponse = getResponseById(projectId);

    }

    @Then("only the project with id {string} and title {string} should be returned")
    public void onlyTheProjectWithIdAndTitleShouldBeReturned(String projectId, String projectTitle) {
        List<Project> projectList = convertToProjectList(this.projectByIdReponse.getBody().getObject());
        assertEquals(projectList.size(), 1);
        Project project = projectList.get(0);
        assertEquals(project.getId(), projectId);
        assertEquals(project.getTitle(), projectTitle);
    }

    @Given("the project list size is known and does not include the project with the given title {string}")
    public void theProjectListSizeIsKnownAndDoesNotIncludeTheProjectWithTheGivenTitle(String projectTitle) {
        List<Project> projectList = getAllProject();
        assertFalse(projectList.stream().filter(cate -> projectTitle.equals(cate.getTitle())).findAny().isPresent());
        this.oldProjectList = projectList;
    }

    @When("user add a new project with title {string}")
    public void userAddANewProjectWithTitle(String projectTitle) {
        HttpResponse<JsonNode> response = createProject(projectTitle, null);
        Project created = convertToCatory(response.getBody().getObject());
        assertTrue(projectTitle.equals(created.getTitle()));

    }

    @And("requests to get all the instances of projects")
    public void requestsToGetAllTheInstancesOfProjects() {
        this.nowProjectList = getAllProject();
    }

    @Then("the system should return instances of projects in the updated project list contains a new one with the given title {string} and the size of the project list will be incremented.")
    public void theSystemShouldReturnInstancesOfProjectsInTheUpdatedProjectListContainsANewOneWithTheGivenTitleAndTheSizeOfTheProjectListWillBeIncremented(String projectTitle) {
        assertTrue(this.nowProjectList.size() == this.oldProjectList.size() + 1);
        Optional<Project> projectOptional = this.nowProjectList.stream().filter(project -> projectTitle.equals(project.getTitle())).findAny();
        assertTrue(projectOptional.isPresent());
    }

    @Given("the project list is not empty")
    public void theProjectListIsNotEmpty() {
        assertFalse(getAllProject().isEmpty());
    }


    @Then("Then the system should output an error code of no project instance founded: {string}")
    public void thenTheSystemShouldOutputAnErrorCodeOfNoProjectInstanceFounded(String arg0) {
        assertEquals(this.projectByIdReponse.getStatus(), 404);
    }

    static List<Project> getAllProject() {
        HttpResponse<JsonNode> response = Unirest.get("/projects").asJson();
        return convertToProjectList(response.getBody().getObject());
    }

    public static List<Project> convertToProjectList(JSONObject response) {
        List<Project> projectList = new ArrayList();
        if (response.has("projects")) {
            for (Object project : response.getJSONArray("projects")) {
                JSONObject projectObject = (JSONObject) project;
                projectList.add(convertToCatory(projectObject));
            }
        }
        return projectList;
    }


    private static Project convertToCatory(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String description = jsonObject.has("description") ? jsonObject.getString("description") : null;
        String id = jsonObject.has("id") ? jsonObject.getString("id") : null;
        return new Project(id, title, description);
    }


    private HttpResponse<JsonNode> getResponseById(String projectId) {
        return Unirest.get("/projects/" + projectId).asJson();
    }


    public static HttpResponse<JsonNode> createProject(String title, String description) {
        return Unirest.post("/projects").body("{\"title\":\"" + title + "\", \"description\":\"" + description + "\"}").asJson();
    }


}
