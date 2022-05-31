package actions;

import models.Employee;
import models.Post;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import services.PostsService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ToEmployeeParser {
    private final PostsService postsService;

    public ToEmployeeParser(PostsService postsService) {
        this.postsService = postsService;
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
        return Employee.builder()
                .firstName(firstName.toString())
                .lastName(lastName.toString())
                .characteristics(new ArrayList<>(characteristics))
                .description(stringDescription)
                .post(post)
                .build();
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
        return new Employee(firstName, lastName, description, characteristics, post);
    }
}
