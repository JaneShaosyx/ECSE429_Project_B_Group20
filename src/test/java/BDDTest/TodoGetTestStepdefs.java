package BDDTest;

import domain.Todo;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;


import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TodoGetTestStepdefs extends AnotherTestInitialization {



    private HttpResponse<JsonNode> todoByIdReponse;
    private List<Todo> oldTodoList;
    private List<Todo> nowTodoList;



    @After("@tagToIdentifyThatBeginAfterShouldRunForThisFeatureOnly")
    public void shutdown() {
        stopServer();
        if(TestInitialization.isFirst==1){
            TestInitialization.stopServer2();
        }

    }


    @Given("the todo with id {string} and title {string} is existed in the system:")
    public void theTodoWithIdAndTitleIsExistedInTheSystem(String todoId, String title) {
        boolean isExists=TodoOperation.getAllTodos().stream().filter(todo -> todoId.equals(todo.getId()) && title.equals(todo.getTitle()) )
                .findAny().isPresent();
        assertTrue(isExists);
    }

    @When("user requests to get a todo with id {string}")
    public void userRequestsToGetATodoWithId(String todoId) {
        this.todoByIdReponse = TodoOperation.getTodoResponseById(todoId);
    }

    @Then("only the todo with id {string} and related title {string} should be returned")
    public void onlyTheTodoWithIdAndRelatedTitleShouldBeReturned(String todoId, String title) {
        List<Todo> todolist = TodoOperation.convertTodoList(this.todoByIdReponse.getBody().getObject());
        assertEquals(todolist.size(), 1);
        Todo todo=todolist.get(0);
        assertEquals(todo.getId(), todoId);
        assertEquals(todo.getTitle(), title);
    }





    @Given("the todo list is not empty")
    public void theTodoListIsNotEmpty() {
        assertFalse(TodoOperation.getAllTodos().isEmpty());
    }

    @Then("Then the system should output an error code of no instance founded: {string}")
    public void thenTheSystemShouldOutputAnErrorCodeOfNoInstanceFounded(String id) {
        assertEquals(this.todoByIdReponse.getStatus(), 404);
    }



    @Given("the todo list size is known and does not include the todo with the given title {string}")
    public void theTodoListSizeIsKnownAndDoesNotIncludeTheTodoWithTheGivenTitle(String todoTitle) {
        List<Todo> todoList=TodoOperation.getAllTodos();
        assertFalse(todoList.stream().filter(todo->todoTitle.equals(todo.getTitle())).findAny().isPresent());
        this.oldTodoList=todoList;
    }

    @When("user add a new todo with title {string}")
    public void userAddANewTodoWithTitle(String todoTitle) {
        HttpResponse<JsonNode> response=TodoOperation.createTodo(todoTitle,null);
         Todo created=TodoOperation.convertToTodo(response.getBody().getObject());
         assertTrue(todoTitle.equals(created.getTitle()));

    }

    @And("requests to get all the instances of todos")
    public void requestsToGetAllTheInstancesOfTodos() {
        this.nowTodoList=TodoOperation.getAllTodos();
    }

    @Then("the system should return instances of todos in the updated todo list contains a new one with the given title {string} and the size of the todo list will be incremented.")
    public void theSystemShouldReturnInstancesOfTodosInTheUpdatedTodoListContainsANewOneWithTheGivenTitleAndTheSizeOfTheTodoListWillBeIncremented(String todoTitle) {
        assertTrue(this.nowTodoList.size()==this.oldTodoList.size()+1);
        Optional<Todo> todoOptional=this.nowTodoList.stream().filter(todo->todoTitle.equals(todo.getTitle())).findAny();
        assertTrue(todoOptional.isPresent());
    }

    @Given("my service is running")
    public void myServiceIsRunning() {
            while (!isOnline) {
                try {
                    environmentSetUp();
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

