package com.evo.apatios.service.employee;

import com.evo.apatios.exception.NotFoundException;
import com.evo.apatios.model.QEmployee;
import com.evo.apatios.repository.EmployeeRepository;
import com.evo.apatios.service.argument.employee.CreateEmployeeArgument;
import com.evo.apatios.service.argument.employee.UpdateEmployeeArgument;
import com.evo.apatios.service.params.QPredicates;
import com.google.common.collect.Lists;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.evo.apatios.model.Employee;
import com.evo.apatios.service.params.SearchParams;

import javax.transaction.Transactional;
import java.util.*;
//import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public Employee create(CreateEmployeeArgument employee) {
        return repository.save(Employee.builder()
                                       .firstName(employee.getFirstName())
                                       .lastName(employee.getLastName())
                                       .description(employee.getDescription())
                                       .post(employee.getPost())
                                       .contacts(employee.getContacts())
                                       .characteristics(employee.getCharacteristics())
                                       .jobType(employee.getJobType())
                                       .build());
    }

    @Transactional
    public Employee update(UpdateEmployeeArgument employee) {
        Employee existedEmployee = getExisting(employee.getId());

        existedEmployee.setFirstName(employee.getFirstName());
        existedEmployee.setLastName(employee.getLastName());
        existedEmployee.setDescription(existedEmployee.getDescription());
        existedEmployee.setContacts(employee.getContacts());
        existedEmployee.setCharacteristics(employee.getCharacteristics());
        existedEmployee.setPost(employee.getPost());
        existedEmployee.setJobType(employee.getJobType());

        return repository.save(existedEmployee);
    }

    @Transactional
    public List<Employee> getEmployeeList(SearchParams params) {
        QEmployee employee = QEmployee.employee;

        Predicate predicate = QPredicates.builder()
                                         .add(params.getFirstName(), employee.firstName::containsIgnoreCase)
                                         .add(params.getLastName(), employee.lastName::containsIgnoreCase)
                                         .add(params.getPostId(), employee.post.id::eq)
                                         .buildAnd();
        return Lists.newArrayList(repository.findAll(predicate != null ? predicate
                                                                       : employee.isNotNull()));
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Employee getExisting(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Employee with this id is not found", id));
    }
}
