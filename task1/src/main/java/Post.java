import java.util.UUID;

public class Post {

    protected UUID id;

    protected String name;

    public Post() {
    }
    //не понял по диаграмме классов какой должен быть конструктор у класса, поэтому взял оба варианта
    public Post(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}