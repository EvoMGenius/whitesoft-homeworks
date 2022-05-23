package services;

import actions.SearchingUtils;
import lombok.Getter;
import models.Employee;
import models.Post;
import models.SearchParams;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class EmployeeService {

    private final PostsService postsService;

    private final SearchingUtils searchingUtils;

    private List<Employee> employees = new ArrayList<>();

    public EmployeeService(PostsService postsService, SearchingUtils searchingUtils) {
        this.postsService = postsService;
        this.searchingUtils = searchingUtils;
    }
    public EmployeeService(PostsService postsService) {
        this.postsService = postsService;
        this.searchingUtils = new SearchingUtils(); //По скольку класс единичный, нет смысла делать его как аргумент конструктора
    }


    public void addEmployee(Employee employee){
        employees.add(employee);
    }

    public void autofillPosts(){ //создано для тестов
        postsService.autofill();
    }

    public Employee parseFromJson(JSONObject employee){
        Object firstName = employee.get("firstName");
        Object lastName = employee.get("lastName");
        Object description = employee.get("description");
        JSONArray characteristics= (JSONArray)employee.get("characteristics");
        Object postId = employee.get("postID");
        Post post = postsService.findPostByUUID(UUID.fromString(String.valueOf(postId)));
        String stringDescription = "";
        if(description != null){
            stringDescription = description.toString();
        }
        Employee parsedEmployee = Employee.builder()
                .firstName(firstName.toString())
                .lastName(lastName.toString())
                .characteristics(new ArrayList<>(characteristics))
                .description(stringDescription)
                .post(post)
                .build();
        addEmployee(parsedEmployee);
        return parsedEmployee;
    }

    public Employee parseFromString(String employee){
        String[] components = employee.split("\n");
        String firstName = "";
        String lastName = "";
        String description = "";
        Post post = null;
        List<String> characteristics = new ArrayList<>();
        for (String component: components) {
            if(component.startsWith("firstName:")){
                firstName = component.replaceFirst("firstName:","").trim();
            }
            if(component.startsWith("lastName:")){
                lastName = component.replaceFirst("lastName:","").trim();
            }
            if(component.startsWith("description:")){
                description = component.replaceFirst("description:","").trim();
            }
            if(component.startsWith("characteristics:")){
                characteristics.addAll(List.of(component
                        .replaceFirst("characteristics:","")
                        .trim().split(",")));
            }
            if(component.startsWith("postId:")){
                post = postsService.findPostByUUID(UUID.fromString(component.replaceFirst("postId:","").trim()));
            }
        }
        if(firstName.isBlank()|| lastName.isBlank()|| characteristics.isEmpty() || post==null){
            throw new RuntimeException("Не все поля работника заполнены");
        }
        Collections.sort(characteristics);
        Employee parsedEmployee = new Employee(firstName, lastName, description, characteristics, post);
        addEmployee(parsedEmployee);
        return parsedEmployee;
    }


    public List<Employee> getEmployees(SearchParams params){
        return searchingUtils.search(employees, params);
    }
}
