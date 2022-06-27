package com.evo.apatios.service.employee;

import com.evo.apatios.model.Employee;
import com.evo.apatios.model.Post;
import com.evo.apatios.repository.EmployeeRepository;
import com.evo.apatios.service.argument.CreationEmployeeAgrument;
import com.evo.apatios.service.argument.UpdatingEmployeeArgument;
import com.evo.apatios.service.params.SearchParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
         when(repository.findAll()).thenReturn(this.mockEmployees());

        //act

        List<Employee> employeeList = service.getEmployeeList(searchParams);

        //assert

        Assertions.assertArrayEquals(employeeList.toArray(), List.of(Employee.builder()
                .id(firstEmployeeId)
                .firstName("Ivan")
                .lastName("Ivanov")
                        .post(new Post(postId, "some post name"))
                .build()).toArray());

        verify(repository).findAll();
    }

    @Test
    void create() {
        //arrange
        UUID empId = UUID.randomUUID();
        Employee expectedEmployee = Employee.builder()
                .id(empId)
                .firstName("Victor")
                .lastName("Ivanchenko")
                .post(new Post(null,"post name")).build();
        CreationEmployeeAgrument argument = CreationEmployeeAgrument.builder()
                .id(empId)
                .firstName("Victor")
                .lastName("Ivanchenko")
                .post(new Post(null, "post name"))
                .build();
        when(repository.save(any())).thenReturn(expectedEmployee);
        //act
        Employee employee = service.create(argument);
        //assert
        Assertions.assertEquals(employee, expectedEmployee);

        verify(repository).save(any());
    }

    @Test
    void update() {
        //arrange
        UUID empId = UUID.randomUUID();
        Employee expectedEmployee = Employee.builder()
                .id(empId)
                .firstName("Victor")
                .lastName("Ivanchenko")
                .post(new Post(null,"post name")).build();
        UpdatingEmployeeArgument argument = UpdatingEmployeeArgument.builder()
                .id(empId)
                .firstName("Victor")
                .lastName("Ivanchenko")
                .post(new Post(null, "post name"))
                .build();
        when(repository.save(any())).thenReturn(expectedEmployee);
        //act
        Employee employee = service.update(argument);
        //assert
        Assertions.assertEquals(employee, expectedEmployee);

        verify(repository).save(any());
    }

    @Test
    void findByIdExistedEmployee() {
        //arrange

        when(repository.findById(any())).thenReturn(Optional.of(Employee.builder()
                .id(firstEmployeeId)
                .firstName("Ivan")
                .lastName("Ivanov")
                .post(new Post(postId, "some post name"))
                .build()));
        //act

        Optional<Employee> employee = service.findById(firstEmployeeId);

        //assert

        Assertions.assertEquals(employee.get(), Employee.builder()
                .id(firstEmployeeId)
                .firstName("Ivan")
                .lastName("Ivanov")
                .post(new Post(postId, "some post name"))
                .build());

        verify(repository).findById(any());
    }

    @Test
    void findByIdNotExistedEmployee() {
        //arrange

        when(repository.findById(any())).thenReturn(Optional.empty());
        //act

        Optional<Employee> employee = service.findById(firstEmployeeId);

        //assert

        Assertions.assertThrows(NoSuchElementException.class,
                employee::get);

        verify(repository).findById(any());
    }
    private List<Employee> mockEmployees(){
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
                .post(new Post(UUID.randomUUID(),""))
                .build());
        return new ArrayList<>(list);
    }
}