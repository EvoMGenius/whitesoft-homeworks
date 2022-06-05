package controller;

import action.AddEmployeeAction;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.EmployeeService;
import service.utils.SearchParams;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AddEmployeeAction addEmployeeAction;

    private final ObjectMapper objectMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, AddEmployeeAction addEmployeeAction, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
        this.addEmployeeAction = addEmployeeAction;
        this.objectMapper = objectMapper;
    }
    @GetMapping()
    public List<EmployeeDto> findAllEmployees(SearchParams searchParams){
        return employeeService.getEmployeesWithSearchParams(searchParams).stream()
                .map(employee -> objectMapper.convertValue(employee, EmployeeDto.class))
                .collect(Collectors.toList());
    }
}
