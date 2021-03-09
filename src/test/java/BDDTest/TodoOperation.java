package BDDTest;

import domain.Todo;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TodoOperation {



    public static HttpResponse<JsonNode> createTodo(String title, String description) {
        return Unirest.post("/todos").body("{\"title\":\"" + title + "\", \"description\":\"" + description + "\"}").asJson();
    }

    public static HttpResponse<JsonNode> deleteTodo(String todoId) {
        return Unirest.delete("/todos/"+todoId).asJson();
    }

    public static HttpResponse<JsonNode> getTodoResponseById(String id) {
        return Unirest.get("/todos/" + id).asJson();
    }



    public static List<Todo> getAllTodos() {
        HttpResponse<JsonNode> response = Unirest.get("/todos").asJson();
        return convertTodoList(response.getBody().getObject());
    }

    public static List<Todo> convertTodoList(JSONObject response) {
        List<Todo> todoList = new ArrayList();
        if (response.has("todos")) {
            for (Object todo : response.getJSONArray("todos")) {
                JSONObject todo_task = (JSONObject) todo;
                todoList.add(convertToTodo(todo_task));
            }
        }
        return todoList;
    }

    public static Todo convertToTodo(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String description = jsonObject.has("description") ? jsonObject.getString("description") : null;
        Boolean doneStatus = jsonObject.has("doneStatus") ? Boolean.valueOf(jsonObject.getString("doneStatus")) : null;
        String id = jsonObject.has("id") ? jsonObject.getString("id") : null;
        return new Todo(id, title, doneStatus, description);
    }


}
