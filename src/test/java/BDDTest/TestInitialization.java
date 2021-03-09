package BDDTest;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.junit.After;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestInitialization {
    private static Process serverProcess;
    private static ArrayList<Process> serverProcesses = new ArrayList<Process>();
    private static final String URL = "http://localhost:4567";
    static String errorMessage;
    static int statusCode;
    static JSONObject originalValue;
    static JSONObject response;
    static JSONObject originalTodoList;
    static JSONArray taskList;
    public static boolean isShutdown = false;
    public static boolean isOnline = false;
    public static int isFirst=1;


    public static void environmentSetUp() {
        Unirest.config().defaultBaseUrl(URL);
        startServerAPI();
    }

    public static void startServerAPI() {
        try {
            ProcessBuilder inputS = new ProcessBuilder("java", "-jar", "runTodoManagerRestAPI-1.5.5.jar");
            serverProcess = inputS.start();
            serverProcesses.add(serverProcess);
            final InputStream is = serverProcess.getInputStream();
            final InputStreamReader inputSR = new InputStreamReader(is);
            final BufferedReader output = new BufferedReader(inputSR);
            while (true) {
                String in = output.readLine();
                if (Unirest.get("/todos").asJson().getStatus()==200) {
                    isOnline = true;
                    System.out.println("====start"+"==="+serverProcess.pid());
                    break;
                }
            }
        } catch (Exception e) {
        }
    }

    public static void stopServer2() {
        for (Process p : serverProcesses) {
            System.out.println("====stop" + "==" + p.pid());
            p.destroy();
        }
        serverProcesses=new ArrayList<Process>();
        isFirst=0;
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isOnline = false;
    }

    public static void checkSideEffect() {
        if (isShutdown) {
            isShutdown = !isShutdown;
            return;
        }
        String title = Unirest.get("/todos").asJson().getBody().getObject().getJSONArray("todos").getJSONObject(0).getString("title");
        assertTrue(title.equals("scan paperwork") || title.equals("file paperwork"));
        assertEquals("Office Work", Unirest.get("/projects").asJson().getBody().getObject().getJSONArray("projects").getJSONObject(0).getString("title"));
        HttpResponse<JsonNode> response = Unirest.get("/categories").asJson();
        String title1 = response.getBody().getObject().getJSONArray("categories").getJSONObject(0).getString("title");
        assertTrue(title1.equals("Home") || title1.equals("Office"));
    }

    public static JSONObject findProjectByTitle(String projectName) {
        JSONObject response = Unirest.get("/projects?title=" + projectName).asJson().getBody().getObject();
        for (Object proj : response.getJSONArray("projects")) {
            JSONObject project = (JSONObject) proj;
            if (project.getString("title").equals(projectName)) {
                return project;
            }
        }
        return null;
    }

    public static JSONObject findTodoByTitle(String todoTitle) {
        JSONObject response = Unirest.get("/todos?title=" + todoTitle).asJson().getBody().getObject();
        for (Object todo : response.getJSONArray("todos")) {
            JSONObject todo_task = (JSONObject) todo;
            if (todo_task.getString("title").equals(todoTitle)) {
                return todo_task;
            }
        }
        return null;
    }

    public static JSONObject findCategoryByTitle(String categoryTitle) {
        JSONObject response = Unirest.get("/categories?title=" + categoryTitle).asJson().getBody().getObject();
        for (Object cate : response.getJSONArray("categories")) {
            JSONObject category = (JSONObject) cate;
            if (category.getString("title").equals(categoryTitle)) {
                return category;
            }
        }
        return null;
    }


}
