package services;

import models.Employee;
import models.Post;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class EmployeeServiceTest {

    EmployeeService employeeService = new EmployeeService(new PostsService());
    List<Employee> employees = new ArrayList<>(List.of(new Employee("Иван", "Иванов", "", List.of("some characteristics"), new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Backend Senior Developer")),
                                                        new Employee("Генадий","Кузьмин",
                                                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sitamet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas erat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. Suspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis sed congue erat",
                                                                List.of("honest","introvert","like criticism","love of Learning","pragmatism"),
                                                                new Post(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"),"Backend Middle Developer"))));

    @Test
    public void parseFromJsonFromFile() {
        //arrange
        FileService fileService = new FileService();
        File file = new File("input.json");
        JSONArray array = fileService.readToJsonArray(file);
        employeeService.autofillPosts();
        //act
        array.forEach(o -> employeeService.parseFromJson((JSONObject) o));

        //assert
        Assert.assertArrayEquals(employeeService.getEmployees().toArray(),employees.toArray());

    }
}