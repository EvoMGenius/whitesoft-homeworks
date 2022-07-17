package com.evo.apatios.service.employee;

import com.evo.apatios.exception.NotFoundException;
import com.evo.apatios.model.Employee;
import com.evo.apatios.model.Post;
import com.evo.apatios.model.QEmployee;
import com.evo.apatios.repository.EmployeeRepository;
import com.evo.apatios.service.argument.employee.CreateEmployeeArgument;
import com.evo.apatios.service.argument.employee.UpdateEmployeeArgument;
import com.evo.apatios.service.params.QPredicates;
import com.evo.apatios.service.params.SearchParams;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    private final EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);

    private final EmployeeService service = new EmployeeService(repository);

    private final UUID postId = UUID.randomUUID();

    private final UUID firstEmployeeId = UUID.randomUUID();
    private final UUID secondEmployeeId = UUID.randomUUID();
    private final UUID thirdEmployeeId = UUID.randomUUID();

    @Test
    void getEmployeeListWithAllCompletedSearchParams() {
        //arrange
        SearchParams searchParams = SearchParams.builder()
                                                .firstName("Ivan")
                                                .lastName("Ivanov")
                                                .postId(postId).build();

        Post post = new Post(postId, "some post name");

        QEmployee employee = QEmployee.employee;

        Predicate expectedPredicate = QPredicates.builder()
                                                 .add(searchParams.getFirstName(), employee.firstName::containsIgnoreCase)
                                                 .add(searchParams.getLastName(), employee.lastName::containsIgnoreCase)
                                                 .add(searchParams.getPostId(), employee.post.id::eq)
                                                 .buildAnd();

        ArgumentCaptor<Predicate> predicateArgumentCaptor = ArgumentCaptor.forClass(Predicate.class);

        when(repository.findAll(predicateArgumentCaptor.capture()))
                .thenReturn(List.of(Employee.builder()
                                            .id(firstEmployeeId)
                                            .firstName("Ivan")
                                            .lastName("Ivanov")
                                            .post(post)
                                            .build()));
        //act
        List<Employee> employeeList = service.getEmployeeList(searchParams);
        Predicate capturedPredicate = predicateArgumentCaptor.getValue();
        //assert
        assertThat(capturedPredicate).isEqualTo(expectedPredicate);
        Assertions.assertEquals(employeeList,
                                List.of(Employee.builder()
                                                .id(firstEmployeeId)
                                                .firstName("Ivan")
                                                .lastName("Ivanov")
                                                .post(post)
                                                .build()));
    }

    @Test
    void create() {
        //arrange
        Employee expectedEmployee = mock(Employee.class);

        CreateEmployeeArgument argument = CreateEmployeeArgument.builder()
                                                                .firstName("Victor")
                                                                .lastName("Ivanchenko")
                                                                .post(mock(Post.class))
                                                                .build();

        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);

        when(repository.save(employeeCaptor.capture())).thenReturn(expectedEmployee);
        //act
        Employee employee = service.create(argument);
        Employee capturedEmployee = employeeCaptor.getValue();
        //assert
        Assertions.assertEquals(employee, expectedEmployee);
        assertThat(capturedEmployee).usingRecursiveComparison()
                                    .ignoringFields("id")
                                    .isEqualTo(argument);
    }

    @Test
    void update() {
        //arrange
        UUID empId = UUID.randomUUID();
        Employee expectedEmployee = mock(Employee.class);
        UpdateEmployeeArgument argument = UpdateEmployeeArgument.builder()
                                                                .id(empId)
                                                                .firstName("Victor")
                                                                .lastName("Ivanchenko")
                                                                .post(mock(Post.class))
                                                                .build();

        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);

        when(repository.findById(empId)).thenReturn(Optional.of(Employee.builder()
                                                                        .id(empId)
                                                                        .build()));
        when(repository.save(employeeCaptor.capture())).thenReturn(expectedEmployee);
        //act
        Employee employee = service.update(argument);
        Employee capturedEmployee = employeeCaptor.getValue();
        //assert
        Assertions.assertEquals(employee, expectedEmployee);

        assertThat(capturedEmployee).usingRecursiveComparison()
                                    .isEqualTo(argument);
    }

    @Test
    void findByIdExistedEmployee() {
        //arrange
        Employee mock = mock(Employee.class);

        when(repository.findById(any())).thenReturn(Optional.of(mock));
        //act
        Employee employee = service.getExisting(firstEmployeeId);
        //assert
        Assertions.assertEquals(employee, mock);

        verify(repository).findById(any());
    }

    @Test
    void findByIdNotExistedEmployee() {
        //arrange
        when(repository.findById(any())).thenReturn(Optional.empty());
        //act+assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            service.getExisting(firstEmployeeId);
        });

        verify(repository).findById(any());
    }

    private List<Employee> mockEmployees() {
        List<Employee> list = new ArrayList<>();
        list.add(Employee.builder()
                         .id(firstEmployeeId)
                         .firstName("Ivan")
                         .lastName("Ivanov")
                         .post(new Post(postId, "some post name"))
                         .build());
        list.add(Employee.builder()
                         .id(secondEmployeeId)
                         .firstName("Jeka")
                         .lastName("Trakilov")
                         .post(new Post(UUID.randomUUID(), ""))
                         .build());
        list.add(Employee.builder()
                         .id(thirdEmployeeId)
                         .firstName("Andrew")
                         .lastName("Mikanchik")
                         .post(new Post(UUID.randomUUID(), ""))
                         .build());
        return new ArrayList<>(list);
    }
}