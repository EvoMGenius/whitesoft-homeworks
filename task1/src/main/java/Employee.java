import java.util.List;

public class Employee {

    protected String firstName;
    protected String lastName;
    protected String description;
    protected List<String> characteristics;
    protected Post post;

    public Employee() {
    }
    //не понял по диаграмме классов какой должен быть конструктор у класса, поэтому взял оба варианта
    public Employee(String firstName, String lastName, String description, List<String> characteristics, Post post) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.characteristics = characteristics;
        this.post = post;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<String> characteristics) {
        this.characteristics = characteristics;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                ", characteristics=" + characteristics +
                ", post=" + post.getName() +
                '}';
    }
}