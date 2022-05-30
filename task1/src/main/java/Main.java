import actions.ToEmployeeParser;
import dao.EmployeeDAO;
import models.SearchParams;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import services.EmployeeService;
import services.FileService;
import services.PostsService;

import java.io.*;
import java.util.*;

public class Main {

    private static String filePath = "";
    private static SearchParams searchParam =new SearchParams();

    public static void main(String[] args) {
//        parseArgs(args);
        String[] strings = new String[]{"-file=","task1/src/main/resources/input.json","-firstname=","Иван"};
        parseArgs(strings);
        PostsService postsService = new PostsService();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        EmployeeService employeeService = new EmployeeService(employeeDAO);
        FileService fileService = new FileService();
        ToEmployeeParser parser = new ToEmployeeParser(postsService);

        postsService.autofill();

        File file = new File(filePath);
        JSONArray array = fileService.readToJsonArray(file);
        array.forEach( o -> {
            employeeService.addEmployee(parser.parseFromJson((JSONObject) o));
        });
        employeeService.getEmployeesWithSearchParams(searchParam).forEach(System.out::println);
    }

    private static void parseArgs(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Введите путь к файлу аргументом консоли при запуске");
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-file=")) {
                filePath = args[i + 1];
                System.out.println(filePath);
            }
            if (args[i].equals("-firstname=")) {
                 searchParam.setFirstName(args[i + 1]);
            }
            if (args[i].equals("-lastname=")) {
                searchParam.setLastName(args[i + 1]);
            }
            if (args[i].equals("-uuid=")) {
                searchParam.setPostId(UUID.fromString(args[i + 1]));
            }
        }
    }
}