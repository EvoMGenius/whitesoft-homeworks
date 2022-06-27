package com.evo.apatios.service.employee;

import com.evo.apatios.model.Employee;
import com.evo.apatios.model.Post;
import com.evo.apatios.repository.EmployeeRepository;
import com.evo.apatios.service.params.SearchParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Test
    void create() {
        //arrange

        //act

        //assert
    }

    @Test
    void update() {
        //arrange

        //act

        //assert
    }

    @Test
    void deleteById() {
        //arrange

        //act

        //assert
    }

    @Test
    void findById() {
        //arrange

        //act

        //assert
    }
}