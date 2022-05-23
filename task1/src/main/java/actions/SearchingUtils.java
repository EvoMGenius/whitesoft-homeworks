package actions;

import models.Employee;
import models.SearchParams;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SearchingUtils {
    public List<Employee> search(List<Employee> employees, SearchParams params) {
        Predicate<Employee> predicateFirstName = Objects::nonNull;
        Predicate<Employee> predicateLastName= Objects::nonNull;
        Predicate<Employee> predicateUUID = Objects::nonNull;
        if(params.getFirstName()!=null){
            predicateFirstName = employee -> employee.getFirstName().equals(params.getFirstName());
        }
        if(params.getLastName()!=null){
            predicateLastName = employee ->  employee.getLastName().equals(params.getLastName());
        }
        if(params.getPostId()!=null){
            predicateUUID = employee -> employee.getPost().getId().equals(params.getPostId());
        }

        //очень долго думал как сделать, чтобы при отсутствии params вывод не страдал. Ибо выводил пустой лист

        return employees.stream()
                .filter(predicateFirstName)
                .filter(predicateLastName)
                .filter(predicateUUID)
                .sorted(Comparator.comparing(Employee::getFirstName)
                .thenComparing(Employee::getLastName))
                .collect(Collectors.toList());
    }
}
