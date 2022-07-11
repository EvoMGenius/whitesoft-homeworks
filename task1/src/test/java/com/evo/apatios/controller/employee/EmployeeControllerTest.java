package com.evo.apatios.controller.employee;

import com.evo.apatios.dto.input.employee.CreateEmployeeDto;
import com.evo.apatios.dto.input.employee.UpdateEmployeeDto;
import com.evo.apatios.dto.output.employee.EmployeeDto;
import com.evo.apatios.exception.MessageError;
import com.evo.apatios.model.Contacts;
import com.evo.apatios.service.params.SearchParams;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.evo.apatios.model.JobType.CONTRACT;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnablePostgresIntegrationTest
class EmployeeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "/dataset/EmployeeControllerTest/findAllWithSearchParams/DB.json")
    void findAllEmployeesWithSearchParams() {
        //arrange
        SearchParams params = SearchParams.builder().firstName("mikhail").build();
        //act
        List<EmployeeDto> response = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/employee/list")
                        .queryParam("firstName",params.getFirstName())
                        .build())
                .exchange()
                //assert
                .expectStatus()
                .isOk()
                .expectBodyList(EmployeeDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).hasSize(1);
        Assertions.assertThat(response.get(0)).usingRecursiveComparison()
                .isEqualTo(EmployeeDto.builder()
                        .id(UUID.fromString("ad4faaaf-1c1c-4442-87db-1df09c662f89"))
                        .firstName("mikhail")
                        .lastName("bunkov")
                        .characteristics(new ArrayList<>())
                        .description("wwq")
                        .jobType(CONTRACT)
                        .contacts(new Contacts("9929","email","workEmail"))
                        .postId(UUID.fromString("4085e25e-6e6c-4cf1-8949-63c4175bf168"))
                        .build());
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "/dataset/EmployeeControllerTest/findById/DB.json")
    void findExistedById() {
        //arrange
        UUID id = UUID.fromString("3ed906ca-2943-496b-9f31-ee35692c2bfa");
        //act
        EmployeeDto response = webTestClient.get()
                .uri("employee/{id}", id)
                .exchange()
                //assert
                .expectStatus()
                .isOk()
                .expectBody(EmployeeDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).usingRecursiveComparison()
                .isEqualTo(EmployeeDto.builder()
                        .id(id)
                        .firstName("Anton")
                        .lastName("Ivanov")
                        .characteristics(new ArrayList<>())
                        .description("wwq")
                        .jobType(CONTRACT)
                        .contacts(new Contacts("9929","email","workEmail"))
                        .postId(UUID.fromString("4085e25e-6e6c-4cf1-8949-63c4175bf168"))
                        .build());
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "/dataset/EmployeeControllerTest/create/DB.json")
    @ExpectedDataSet(value = "/dataset/EmployeeControllerTest/create/dbByCreate.json", ignoreCols = "employee.id")
    void create() {
        //arrange
        UUID postId = UUID.fromString("4085e25e-6e6c-4cf1-8949-63c4175bf168");
        Contacts contacts = new Contacts("9929", "email", "workEmail");

        CreateEmployeeDto createEmployeeDto = CreateEmployeeDto.builder()
                .firstName("Kirill")
                .lastName("Kolov")
                .characteristics(new ArrayList<>())
                .description("wwq")
                .jobType(CONTRACT)
                .contacts(contacts)
                .postId(postId)
                .build();
        //act
        EmployeeDto response = webTestClient.post()
                .uri("employee")
                .bodyValue(createEmployeeDto)
                .exchange()
                //Assert
                .expectStatus()
                .isOk()
                .expectBody(EmployeeDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(EmployeeDto.builder()
                        .id(UUID.randomUUID())
                        .firstName("Kirill")
                        .lastName("Kolov")
                        .characteristics(new ArrayList<>())
                        .description("wwq")
                        .jobType(CONTRACT)
                        .contacts(contacts)
                        .postId(postId)
                        .build());
    }

    @Test
    @DataSet(cleanBefore = true, value = "/dataset/EmployeeControllerTest/updateExisted/DB.json")
    @ExpectedDataSet(value = "/dataset/EmployeeControllerTest/updateExisted/dbByUpdateExpected.json")
    void updateExisted() {
        //arrange
        UUID postId = UUID.fromString("4085e25e-6e6c-4cf1-8949-63c4175bf168");
        Contacts contacts = new Contacts("9929", "email", "workEmail");
        UUID id = UUID.fromString("3ed906ca-2943-496b-9f31-ee35692c2bfa");

        UpdateEmployeeDto updateEmployeeDto = UpdateEmployeeDto.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .characteristics(new ArrayList<>())
                .description("wwq")
                .jobType(CONTRACT)
                .contacts(contacts)
                .postId(postId)
                .build();
        //act
        EmployeeDto response = webTestClient.put()
                .uri("employee/{id}", id)
                .bodyValue(updateEmployeeDto)
                .exchange()
                //Assert
                .expectStatus()
                .isOk()
                .expectBody(EmployeeDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).usingRecursiveComparison()
                .isEqualTo(EmployeeDto.builder()
                        .id(id)
                        .firstName("Ivan")
                        .lastName("Ivanov")
                        .characteristics(new ArrayList<>())
                        .description("wwq")
                        .jobType(CONTRACT)
                        .contacts(contacts)
                        .postId(postId)
                        .build());
    }

    @Test
    @DataSet(cleanBefore = true, value = "/dataset/EmployeeControllerTest/delete/DB.json")
    @ExpectedDataSet(value = "/dataset/EmployeeControllerTest/delete/dbByDelete.json")
    void deleteById() {
        //arrange
        UUID id = UUID.fromString("ad4faaaf-1c1c-4442-87db-1df09c662f89");

        //act
        webTestClient.delete()
                .uri("employee/{id}", id)
                .exchange()
                //assert
                .expectStatus()
                .isOk();
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "/dataset/EmployeeControllerTest/findByIdNotExisted/DB.json")
    void findNotExistedById() {
        //arrange
        UUID id = UUID.randomUUID();
        //act
        MessageError er = webTestClient.get()
                .uri("employee/{id}", id)
                .exchange()
                //assert
                .expectStatus()
                .isNotFound()
                .expectBody(MessageError.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(er).usingRecursiveComparison()
                .ignoringFields("timestamp")
                .isEqualTo(MessageError.builder()
                        .message("Employee with this id is not found")
                        .build());
    }
}