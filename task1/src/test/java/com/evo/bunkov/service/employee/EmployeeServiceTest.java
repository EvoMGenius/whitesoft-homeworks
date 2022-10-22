package com.evo.bunkov.service.employee;

import com.evo.bunkov.exception.NotFoundException;
import com.evo.bunkov.model.Employee;
import com.evo.bunkov.model.Post;
import com.evo.bunkov.repository.EmployeeRepository;
import com.evo.bunkov.service.argument.employee.CreateEmployeeArgument;
import com.evo.bunkov.service.argument.employee.UpdateEmployeeArgument;
import com.evo.bunkov.service.params.SearchParams;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    private final EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);

    private final EmployeeService service = new EmployeeService(repository);

    private final UUID firstEmployeeId = UUID.randomUUID();

    @ParameterizedTest
    @MethodSource("getParams")
    void getEmployeeListWithAllCompletedSearchParams(SearchParams searchParams) {
        //arrange
        UUID postId = searchParams.getPostId();

        Post post = new Post(postId, "some post name");

        when(repository.findAll(any(Predicate.class)))
                .thenReturn(List.of(Employee.builder()
                                            .id(firstEmployeeId)
                                            .firstName("Ivan")
                                            .lastName("Ivanov")
                                            .post(post)
                                            .build()));
        //act
        List<Employee> employeeList = service.getEmployeeList(searchParams);
        //assert
        ArgumentCaptor<Predicate> predicateArgumentCaptor = ArgumentCaptor.forClass(Predicate.class);

        verify(repository).findAll(predicateArgumentCaptor.capture());
        Predicate capturedPredicate = predicateArgumentCaptor.getValue();

        assertThat(capturedPredicate)
                .hasToString(String.format("containsIc(employee.firstName,Ivan) &&" +
                                           " containsIc(employee.lastName,Ivanov) &&" +
                                           " employee.post.id = %s", postId));
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

        when(repository.save(any())).thenReturn(expectedEmployee);
        //act
        Employee employee = service.create(argument);
        //assert
        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);

        verify(repository).save(employeeCaptor.capture());
        Employee capturedEmployee = employeeCaptor.getValue();

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

        when(repository.findById(empId)).thenReturn(Optional.of(Employee.builder()
                                                                        .id(empId)
                                                                        .build()));
        when(repository.save(any())).thenReturn(expectedEmployee);
        //act
        Employee employee = service.update(argument);
        //assert
        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);

        verify(repository).save(employeeCaptor.capture());
        Employee capturedEmployee = employeeCaptor.getValue();

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

    static Stream<SearchParams> getParams(){
        return Stream.of(SearchParams.builder()
                                     .firstName("Ivan")
                                     .lastName("Ivanov")
                                     .postId(UUID.randomUUID())
                                     .build());
    }
}