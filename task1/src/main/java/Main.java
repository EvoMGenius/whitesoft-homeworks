import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    protected static Map<UUID, Post> posts = new HashMap<>();

    public static void main(String[] args) {
        if (args.length==0) {
            throw new RuntimeException("Введите путь к файлу аргументом консоли при запуске");
        }
        posts.put(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Backend Senior Developer"));
        posts.put(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"), new Post(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"), "Backend Middle Developer"));

        String filePath = args[0];
        File inputFile = new File(filePath);
        List<String> readInfo = read(inputFile);
        List<Employee> employees = new ArrayList<>();
        readInfo.forEach(employeeText -> {
            Employee employee = parse(employeeText);
            employee.getCharacteristics().sort(String::compareTo);
            employees.add(employee);
        });
        employees.sort(Comparator.comparing(Employee::getLastName).thenComparing(Employee::getFirstName));
        print(employees);
    }

    private static List<String> read(File file){
       List<String> readedInfo = new ArrayList<>();
       try(Scanner sc = new Scanner(file)){
           sc.useDelimiter("((\\n\\r)|(\\r\\n)){2}|(\\r){2}|(\\n){2}");
           while(sc.hasNext()){
               readedInfo.add(sc.next());
           }
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }
       return readedInfo;
    }

    private static Employee parse(String employee){
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
                post = posts.get(UUID.fromString(component.replaceFirst("postId:","").trim()));
            }
        }
        if(firstName.isBlank()|| lastName.isBlank()|| characteristics.isEmpty() || post==null){
            throw new RuntimeException("Не все поля работника заполнены");
        }
        Collections.sort(characteristics);
        return new Employee(firstName,lastName,description,characteristics,post);

    }
    private static void print(List<Employee> employees){
        employees.forEach(System.out::println); //в файле "Выводные данные.txt" находится то, как именно выводится список работников.
    }
}
