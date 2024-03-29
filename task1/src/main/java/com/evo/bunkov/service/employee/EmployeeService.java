package com.evo.bunkov.service.employee;

import com.evo.bunkov.exception.NotFoundException;
import com.evo.bunkov.model.QEmployee;
import com.evo.bunkov.repository.EmployeeRepository;
import com.evo.bunkov.service.argument.employee.CreateEmployeeArgument;
import com.evo.bunkov.service.argument.employee.UpdateEmployeeArgument;
import com.evo.bunkov.service.params.QPredicates;
import com.evo.bunkov.aspect.logging.annotation.LogUpdateMethod;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.evo.bunkov.model.Employee;
import com.evo.bunkov.service.params.SearchParams;

import javax.transaction.Transactional;
import java.util.*;

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
    @LogUpdateMethod
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
        return Lists.newArrayList(repository.findAll(predicate));
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Employee getExisting(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Employee with this id is not found", id));
    }
}
