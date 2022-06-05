package action;

import argument.CreationEmployeeArgument;
import argument.CreationEmployeeArgumentForService;
import exception.IllegalPostNameException;
import model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.EmployeeService;
import service.PostService;

@Component
public class AddEmployeeAction {

    public final EmployeeService employeeService;

    public final PostService postService;

    @Autowired
    public AddEmployeeAction(EmployeeService employeeService, PostService postService) {
        this.employeeService = employeeService;
        this.postService = postService;
    }

    public Employee execute(CreationEmployeeArgument employeeArgument){
        CreationEmployeeArgumentForService argumentForService = CreationEmployeeArgumentForService.builder()
                .firstName(employeeArgument.getFirstName())
                .lastName(employeeArgument.getLastName())
                .characteristics(employeeArgument.getCharacteristics())
                .description(employeeArgument.getDescription())
                .post(postService.findByName(employeeArgument.getPostName()).orElseThrow(()->
                        new IllegalPostNameException(String.format("This '%s' post name is not correct",employeeArgument.getPostName()))))
                .build();

        return employeeService.create(argumentForService);
    }
}
