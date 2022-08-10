//package com.evo.bunkov.controller.employee;
//
//import ch.qos.logback.classic.Logger;
//import ch.qos.logback.classic.LoggerContext;
//import com.evo.bunkov.aspect.logging.ApiRequestLoggingAspect;
//import com.evo.bunkov.aspect.logging.UpdateEmployeeMethodLoggingAspect;
//import com.evo.bunkov.controller.employee.logger.util.LoggingAppender;
//import com.evo.bunkov.dto.input.employee.CreateEmployeeDto;
//import com.evo.bunkov.dto.input.employee.UpdateEmployeeDto;
//import com.evo.bunkov.dto.output.employee.EmployeeDto;
//import com.evo.bunkov.dto.output.post.PostDto;
//import com.evo.bunkov.exception.MessageError;
//import com.evo.bunkov.model.Contacts;
//import com.evo.bunkov.service.params.SearchParams;
//import com.github.database.rider.core.api.dataset.DataSet;
//import com.github.database.rider.core.api.dataset.ExpectedDataSet;
//import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.reactive.server.WebTestClient;
//
//import java.util.List;
//import java.util.UUID;
//
//import static com.evo.bunkov.model.JobType.CONTRACT;
//import static org.assertj.core.api.Assertions.assertThat;
////
////@AutoConfigureWebTestClient
////@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
////@EnablePostgresIntegrationTest
//class EmployeeControllerTest {
//
//    @Autowired
//    private WebTestClient webTestClient;
//
//    private static final String ip = "127.0.0.1";
//
//    private LoggingAppender controllerLogAppender;
//
//    private LoggingAppender updateLogAppender;
//
//    EmployeeDto expectedDto = EmployeeDto.builder()
//                                         .id(UUID.fromString("ad4faaaf-1c1c-4442-87db-1df09c662f89"))
//                                         .firstName("mikhail")
//                                         .lastName("bunkov")
//                                         .characteristics(List.of("Brave", "Smart"))
//                                         .description("wwq")
//                                         .jobType(CONTRACT)
//                                         .contacts(new Contacts("9929", "email", "workEmail"))
//                                         .post(PostDto.builder()
//                                                      .id(UUID.fromString("4085e25e-6e6c-4cf1-8949-63c4175bf168"))
//                                                      .name("Senior Dev")
//                                                      .build())
//                                         .build();
//
//    @BeforeEach
//    void setUp() {
//        controllerLogAppender = new LoggingAppender();
//        controllerLogAppender.setContext(new LoggerContext());
//        controllerLogAppender.start();
//
//        updateLogAppender = new LoggingAppender();
//        updateLogAppender.setContext(new LoggerContext());
//        updateLogAppender.start();
//
//        Logger controllerLogger = (Logger) LoggerFactory.getLogger(ApiRequestLoggingAspect.class);
//        controllerLogger.addAppender(controllerLogAppender);
//
//        Logger updateLogger = (Logger) LoggerFactory.getLogger(UpdateEmployeeMethodLoggingAspect.class);
//        updateLogger.addAppender(updateLogAppender);
//    }
//
//    @AfterEach
//    void cleanUp() {
//        controllerLogAppender.stop();
//    }
//
//    @Test
//    @DataSet(cleanBefore = true, cleanAfter = true, value = "/dataset/EmployeeControllerTest/findAllWithSearchParams/DB.json")
//    void findAllEmployeesWithSearchParams() {
//        //arrange
//        SearchParams params = SearchParams.builder().firstName("mikhail").build();
//
//        String endpoint = "/employee/list";
//        //act
//        List<EmployeeDto> response = webTestClient.get()
//                                                  .uri(uriBuilder -> uriBuilder.path(endpoint)
//                                                                               .queryParam("firstName", params.getFirstName())
//                                                                               .build())
//                                                  .exchange()
//                                                  //assert
//                                                  .expectStatus()
//                                                  .isOk()
//                                                  .expectBodyList(EmployeeDto.class)
//                                                  .returnResult()
//                                                  .getResponseBody();
//
//        assertApiRequestLog("List", "findAllEmployees", "SearchParams", params.toString(), ip, endpoint, "GET");
//
//        assertThat(response).hasSize(1);
//        assertThat(response.get(0)).usingRecursiveComparison()
//                                   .isEqualTo(expectedDto);
//    }
//
//    @Test
//    @DataSet(cleanBefore = true, cleanAfter = true, value = "/dataset/EmployeeControllerTest/findById/DB.json")
//    void findExistedById() {
//        //arrange
//        UUID id = expectedDto.getId();
//
//        String endpoint = "/employee/" + id;
//        //act
//        EmployeeDto response = webTestClient.get()
//                                            .uri(endpoint)
//                                            .exchange()
//                                            //assert
//                                            .expectStatus()
//                                            .isOk()
//                                            .expectBody(EmployeeDto.class)
//                                            .returnResult()
//                                            .getResponseBody();
//
//        assertApiRequestLog("EmployeeDto", "findById", "UUID", id.toString(), ip, endpoint, "GET");
//
//        assertThat(response).usingRecursiveComparison()
//                            .isEqualTo(expectedDto);
//    }
//
//    @Test
//    @DataSet(cleanBefore = true, cleanAfter = true, value = "/dataset/EmployeeControllerTest/create/DB.json")
//    @ExpectedDataSet(value = "/dataset/EmployeeControllerTest/create/dbByCreate.json", ignoreCols = "employee.id, employee_characteristics.employee_id")
//    void create() {
//        //arrange
//        String endpoint = "/employee";
//
//        CreateEmployeeDto createEmployeeDto = CreateEmployeeDto.builder()
//                                                               .firstName(expectedDto.getFirstName())
//                                                               .lastName(expectedDto.getLastName())
//                                                               .characteristics(expectedDto.getCharacteristics())
//                                                               .description(expectedDto.getDescription())
//                                                               .jobType(expectedDto.getJobType())
//                                                               .contacts(expectedDto.getContacts())
//                                                               .postId(expectedDto.getPost().getId())
//                                                               .build();
//        //act
//        EmployeeDto response = webTestClient.post()
//                                            .uri(endpoint)
//                                            .bodyValue(createEmployeeDto)
//                                            .exchange()
//                                            //Assert
//                                            .expectStatus()
//                                            .isOk()
//                                            .expectBody(EmployeeDto.class)
//                                            .returnResult()
//                                            .getResponseBody();
//
//        assertApiRequestLog("EmployeeDto", "create", "CreateEmployeeDto", createEmployeeDto.toString(), ip, endpoint, "POST");
//
//        assertThat(response).usingRecursiveComparison().ignoringFields("id")
//                            .isEqualTo(expectedDto);
//    }
//
//    @Test
//    @DataSet(cleanBefore = true, cleanAfter = true, value = "/dataset/EmployeeControllerTest/updateExisted/DB.json")
//    @ExpectedDataSet(value = "/dataset/EmployeeControllerTest/updateExisted/dbByUpdateExpected.json")
//    void updateExisted() {
//        //arrange
//        String endpoint = "/employee/" + expectedDto.getId();
//
//        UpdateEmployeeDto updateEmployeeDto = UpdateEmployeeDto.builder()
//                                                               .firstName(expectedDto.getFirstName())
//                                                               .lastName(expectedDto.getLastName())
//                                                               .characteristics(expectedDto.getCharacteristics())
//                                                               .description(expectedDto.getDescription())
//                                                               .jobType(expectedDto.getJobType())
//                                                               .contacts(expectedDto.getContacts())
//                                                               .postId(expectedDto.getPost().getId())
//                                                               .build();
//        //act
//        EmployeeDto response = webTestClient.put()
//                                            .uri(endpoint)
//                                            .bodyValue(updateEmployeeDto)
//                                            .exchange()
//                                            //Assert
//                                            .expectStatus()
//                                            .isOk()
//                                            .expectBody(EmployeeDto.class)
//                                            .returnResult()
//                                            .getResponseBody();
//        assertApiRequestLog("EmployeeDto", "update", "UUID,UpdateEmployeeDto", expectedDto.getId().toString() + ", " + updateEmployeeDto, ip, endpoint, "PUT");
//
//        assertThat(updateLogAppender.getEvents())
//                .isNotEmpty()
//                .anySatisfy(event -> assertThat(event.getMessage())
//                        .isEqualTo("Updating employee with id : %s, fields update : firstName: before [Anton] after [%s]. lastName: before [Ivanov] after [%s]. characteristics: before [[Something, Something 2]] after [%s]. ",
//                                   expectedDto.getId(), expectedDto.getFirstName(), expectedDto.getLastName(), expectedDto.getCharacteristics()));
//
//        assertThat(response).usingRecursiveComparison()
//                            .isEqualTo(expectedDto);
//    }
//
//    @Test
//    @DataSet(cleanBefore = true, cleanAfter = true, value = "/dataset/EmployeeControllerTest/delete/DB.json")
//    @ExpectedDataSet(value = "/dataset/EmployeeControllerTest/delete/dbByDelete.json")
//    void deleteById() {
//        //arrange
//        UUID id = UUID.fromString("ad4faaaf-1c1c-4442-87db-1df09c662f89");
//
//        String endpoint = "/employee/" + id;
//        //act
//        webTestClient.delete()
//                     .uri(endpoint)
//                     .exchange()
//                     //assert
//                     .expectStatus()
//                     .isOk();
//
//        assertApiRequestLog("void", "deleteById", "UUID", id.toString(), ip, endpoint, "DELETE");
//    }
//
//    @Test
//    @DataSet(cleanBefore = true, cleanAfter = true, value = "/dataset/EmployeeControllerTest/findByIdNotExisted/DB.json")
//    void findNotExistedById() {
//        //arrange
//        UUID id = UUID.randomUUID();
//
//        String endpoint = "/employee/" + id;
//        //act
//        MessageError er = webTestClient.get()
//                                       .uri(endpoint)
//                                       .exchange()
//                                       //assert
//                                       .expectStatus()
//                                       .isNotFound()
//                                       .expectBody(MessageError.class)
//                                       .returnResult()
//                                       .getResponseBody();
//
//        assertApiRequestLog("EmployeeDto", "findById", "UUID", id.toString(), ip, endpoint, "GET");
//
//        assertThat(er).usingRecursiveComparison()
//                      .ignoringFields("timestamp")
//                      .isEqualTo(MessageError.builder()
//                                             .message("Employee with this id is not found")
//                                             .build());
//    }
//
//    private void assertApiRequestLog(String returningType, String methodName, String methodArgsTypes, String params, String requestIdAddress, String endpoint, String operation) {
//        String loggingExpected = String.format("method= %s com.evo.bunkov.controller.employee.EmployeeController.%s(%s), params= [%s], request: ipAddress= %s, endpoint= %s, operation= %s", returningType, methodName, methodArgsTypes, params, requestIdAddress, endpoint, operation);
//        assertLog(loggingExpected);
//    }
//
//    private void assertLog(String expectedLog) {
//        assertThat(controllerLogAppender.getEvents()).isNotEmpty().anySatisfy(event -> assertThat(event.getMessage()).isEqualTo(expectedLog));
//    }
//}