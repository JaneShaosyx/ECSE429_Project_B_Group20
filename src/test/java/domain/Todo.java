package domain;

public class Todo {

    String id;
    String title;
    String description;
    Boolean doneStatus;


    public Todo(String id, String title, Boolean doneStatus,String description ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.doneStatus = doneStatus;
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getDoneStatus() {
        return doneStatus;
    }



}
