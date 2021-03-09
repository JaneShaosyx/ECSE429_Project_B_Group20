package domain;

public class Project {

    private String id;
    private String title;
    private String description;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


    public Project(String id, String title, String description) {
        this.id=id;
        this.title=title;
        this.description=description;
    }

}
