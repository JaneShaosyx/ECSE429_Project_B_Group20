package BDDTest;

import com.google.gson.JsonObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class StepDefinition extends AnotherTestInitialization{

    HttpResponse<JsonNode> response;
    private Boolean toBool(String s){if(s.equals("true"))return true;else return false;}


    @Given("the rest-api todo list manager is running")
    public void the_restAPI_is_running() throws Throwable{

//        URL url=  new URL("http://localhost:4567");
//        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
//        connection.connect();
//        assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
        while (!isOnline) {
            try {
                environmentSetUp();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    //----------------------UserStory 1 updateTodo-------------------------------------
      // Normal flow
    @When("updating todo id 1 with following params:")
    public void upd_todo_ID_1(List<Map<String,String>> table) throws IOException,InterruptedException {

        JsonObject jo=new JsonObject();
        jo.addProperty("title",table.get(0).get("title"));
        jo.addProperty("doneStatus",toBool(table.get(0).get("doneStatus")));
        jo.addProperty("description",table.get(0).get("description"));

        response=Unirest.post("http://localhost:4567/todos/1").body(jo).asJson();

    }
    @Then("system will update todo id 1 with new contents.")
    public void successUpdTodo_1(){
        assertEquals(response.getStatus(),200);

        JsonObject jo=new JsonObject();
        jo.addProperty("title","scan paperwork");
        jo.addProperty("doneStatus",false);
        jo.addProperty("description","");

        response=Unirest.post("http://localhost:4567/todos/1").body(jo).asJson();
    }
      // Alternate flow
    @When("updating todo id 1 with only new title,status and description not change")
    public void upd_todo_ID_1_noChange() throws IOException,InterruptedException {
        JsonObject jo=new JsonObject();
        jo.addProperty("title","new title1");

        response=Unirest.post("http://localhost:4567/todos/1").body(jo).asJson();
    }
    @Then("system will update todo id 1 and set the optional fields to default values.")
    public void upd_ID1_unchanged(){
        assertEquals(response.getBody().getObject().getString("title"),"new title1");

        JsonObject jo=new JsonObject();
        jo.addProperty("title","scan paperwork");
        jo.addProperty("doneStatus",false);
        jo.addProperty("description","");

        response=Unirest.post("http://localhost:4567/todos/1").body(jo).asJson();

    }

    @When("updating todo id 1 to id \"1\"")
    public void upd_wrongFormat() throws IOException,InterruptedException {
        JsonObject jo=new JsonObject();
        jo.addProperty("id","1");

        response=Unirest.post("http://localhost:4567/todos/1").body(jo).asJson();
    }

    //----------------------UserStory 2 updateProjects-------------------------------------
    // Normal flow
    @When("updating category id 1 with following params:")
    public void upd_category_ID_1(List<Map<String,String>> table) throws IOException,InterruptedException {

        JsonObject jo=new JsonObject();
        jo.addProperty("title",table.get(0).get("title"));
        jo.addProperty("description",table.get(0).get("description"));

        response=Unirest.post("http://localhost:4567/categories/1").body(jo).asJson();

    }
    @Then("system will update category id 1 with new contents.")
    public void successUpdcategory_1(){
        assertEquals(response.getStatus(),200);

        JsonObject jo=new JsonObject();
        jo.addProperty("title","Office");
        jo.addProperty("description","");

        response=Unirest.post("http://localhost:4567/categories/1").body(jo).asJson();
    }
    // Alternate flow
    @When("updating category id 1 with only new title,status and description not change")
    public void upd_category_ID_1_noChange() throws IOException,InterruptedException {
        JsonObject jo=new JsonObject();
        jo.addProperty("title","new cat title1");

        response=Unirest.post("http://localhost:4567/categories/1").body(jo).asJson();
    }
    @Then("system will update category id 1 and set the optional fields to default values.")
    public void upd_categoryID1_unchanged(){
        assertEquals(response.getBody().getObject().getString("title"),"new cat title1");

        JsonObject jo=new JsonObject();
        jo.addProperty("title","Office");
        jo.addProperty("description","");

        response=Unirest.post("http://localhost:4567/categories/1").body(jo).asJson();

    }

    @When("updating category id 1 to id \"1\"")
    public void upd_category_wrongFormat() throws IOException,InterruptedException {
        JsonObject jo=new JsonObject();
        jo.addProperty("id","1");

        response=Unirest.post("http://localhost:4567/categories/1").body(jo).asJson();
    }
    //----------------------UserStory 3 updateProjects-------------------------------------

    @When("updating project id 1 with following params:")
    public void update_projID1(List<Map<String,String>> table){
        JsonObject jo=new JsonObject();
        jo.addProperty("title",table.get(0).get("title"));
        jo.addProperty("completed",toBool(table.get(0).get("completed")));
        jo.addProperty("active",toBool(table.get(0).get("active")));
        jo.addProperty("description",table.get(0).get("description"));

        response=Unirest.post("http://localhost:4567/projects/1").body(jo).asJson();
    }
    @Then("system will update project id 1 with new contents.")
    public void successUpdate_ProjID1(){
        assertEquals(response.getStatus(),200);

        //restore
        JsonObject jo=new JsonObject();
        jo.addProperty("title","Office Work");
        jo.addProperty("completed",false);
        jo.addProperty("active",false);
        jo.addProperty("description","");
        response=Unirest.post("http://localhost:4567/projects/1").body(jo).asJson();
   }

    @When("updating project id 1 with only new title,status and description not change")
    public void update_projID1_onlyTitle(){
        JsonObject jo=new JsonObject();
        jo.addProperty("title","new Title");

        response=Unirest.post("http://localhost:4567/projects/1").body(jo).asJson();
    }
    @Then("system will update project id 1 and set the optional fields to default values.")
    public void successUpdate_ProjID1_onlyTitle(){

        assertEquals(response.getBody().getObject().getString("title"),"new Title");
        //restore
        JsonObject jo=new JsonObject();
        jo.addProperty("title","Office Work");
        jo.addProperty("completed",false);
        jo.addProperty("active",false);
        jo.addProperty("description","");
        response=Unirest.post("http://localhost:4567/projects/1").body(jo).asJson();

    }

    @When("updating project id 1 to id \"1\"")
    public void update_projID1to100(){
        JsonObject jo=new JsonObject();
        jo.addProperty("id","1");

        response=Unirest.post("http://localhost:4567/projects/1").body(jo).asJson();
    }
    @Then("will show the error message give status Bad request")
    public void fail_updateProj1(){
        assertEquals(response.getStatus(),400);
    }
    //----------------------UserStory 4 View projects-------------------------------------
    @And("there exists three projects:")
    public void there_exists_three_projs(List<Map<String,String>> table){
        JsonObject jo=new JsonObject();

        for(int i=0;i<2;i++) {
            jo.addProperty("title", table.get(i).get("title"));
            jo.addProperty("completed", toBool(table.get(i).get("completed")));
            jo.addProperty("active", toBool(table.get(i).get("active")));
            jo.addProperty("description", table.get(i).get("description"));

            response = Unirest.post("http://localhost:4567/projects").body(jo).asJson();
        }
        assertEquals(Unirest.get("http://localhost:4567/projects").asJson().getBody().getObject().getJSONArray("projects").length(),3);
    }

    @When("request to see a project with a given ID 3")
    public void view_proj3(){
        response=Unirest.get("http://localhost:4567/projects/3").asJson();
    }
    @Then("system will list only that project")
    public void success_view_proj3(){
        assertEquals( response.getBody().getObject().getJSONArray("projects").getJSONObject(0).getInt("id"),3);

        //restore
        Unirest.delete("http://localhost:4567/projects/2").asJson();
        Unirest.delete("http://localhost:4567/projects/3").asJson();
    }

    @When("request to list all the projects")
    public void view_all_projs(){
        response=Unirest.get("http://localhost:4567/projects").asJson();
    }
    @Then("system will successfully list all projects")
    public void success_view_all_projs(){

        assertEquals(response.getStatus(),200);

        //restore
        Unirest.delete("http://localhost:4567/projects/4").asJson();
        Unirest.delete("http://localhost:4567/projects/5").asJson();
    }



    @When("request to see a project with ID 1000")
    public void view_proj_invalidID(){
        response=Unirest.get("http://localhost:4567/projects/1000").asJson();
    }
    @Then("system show error can't find such project")
    public void fail_viewProj(){

        assertEquals( response.getStatus(),404);
        Unirest.delete("http://localhost:4567/projects/6").asJson();
        Unirest.delete("http://localhost:4567/projects/7").asJson();
    }
    //----------------------UserStory 5  Assign a category to a project-------------------------------------
    @Given("a following category:")
    public void add_category(List<Map<String,String>> table){
        JsonObject jo=new JsonObject();
        jo.addProperty("title",table.get(0).get("title"));

        jo.addProperty("description",table.get(0).get("description"));

        response=Unirest.post("http://localhost:4567/categories").body(jo).asJson();

        assertEquals(Unirest.get("http://localhost:4567/categories").asJson().getBody().getObject().getJSONArray("categories").length(),3);
    }
    @When("assigning the category id 3 with project id 1")
    public void assign_cat3_to_proj1(){
        JsonObject jo=new JsonObject();
        jo.addProperty("id","3");
        response=Unirest.post("http://localhost:4567/projects/1/categories").body(jo).asJson();
    }
    @Then("system will add category 3 under project 1")
    public void successAttach_cat3_to_proj1(){

        assertEquals( response.getStatus(),201);
        assertEquals( Unirest.get("http://localhost:4567/projects/1/categories").asJson().getBody().getObject().getJSONArray("categories").length(),1);
        //restore
        Unirest.delete("http://localhost:4567/projects/1/categories/3").asJson();
        Unirest.delete("http://localhost:4567/categories/3").asJson();
    }

    @And("this category is already assigned to project id 1")
    public void assign(){
        JsonObject jo=new JsonObject();
        jo.addProperty("id","4");
        response=Unirest.post("http://localhost:4567/projects/1/categories").body(jo).asJson();
    }
    @When("assigning this category with project id 1 again")
    public void assign_again(){
        JsonObject jo=new JsonObject();
        jo.addProperty("id","4");
        response=Unirest.post("http://localhost:4567/projects/1/categories").body(jo).asJson();
    }
    @Then("system will not add the repeated category again")
    public void notAddSame(){
        assertEquals( response.getStatus(),201);

        Unirest.delete("http://localhost:4567/projects/1/categories/4").asJson();
        Unirest.delete("http://localhost:4567/categories/4").asJson();
    }

    @When("request to assign invalid category id 100 to project id 1")
    public void assign_invalidID(){
        JsonObject jo=new JsonObject();
        jo.addProperty("id","4");
        response=Unirest.post("http://localhost:4567/projects/1/categories").body(jo).asJson();
    }
    @Then("the system will show the error message \"Could not find thing matching value for id\"")
    public void fail_assign(){
        assertEquals( response.getStatus(),404);
        Unirest.delete("http://localhost:4567/projects/1/categories/5").asJson();
        Unirest.delete("http://localhost:4567/categories/5").asJson();
    }
}
