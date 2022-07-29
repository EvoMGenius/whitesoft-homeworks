package com.evo.apatios.controller.employee;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.evo.apatios.aspect.logging.ApiRequestLoggingAspect;
import com.evo.apatios.controller.employee.logger.util.LoggingAppender;
import com.evo.apatios.dto.input.employee.CreateEmployeeDto;
import com.evo.apatios.dto.input.employee.UpdateEmployeeDto;
import com.evo.apatios.dto.output.employee.EmployeeDto;
import com.evo.apatios.dto.output.post.PostDto;
import com.evo.apatios.exception.MessageError;
import com.evo.apatios.model.Contacts;
import com.evo.apatios.service.params.SearchParams;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.UUID;

import static com.evo.apatios.model.JobType.CONTRACT;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnablePostgresIntegrationTest
class EmployeeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final String ip = "127.0.0.1";

    private LoggingAppender logAppender;

    EmployeeDto expectedDto = EmployeeDto.builder()
                                         .id(UUID.fromString("ad4faaaf-1c1c-4442-87db-1df09c662f89"))
                                         .firstName("mikhail")
                                         .lastName("bunkov")
                                         .characteristics(List.of("Brave", "Smart"))
                                         .description("wwq")
                                         .jobType(CONTRACT)
                                         .contacts(new Contacts("9929", "email", "workEmail"))
                                         .post(PostDto.builder()
                                                      .id(UUID.fromString("4085e25e-6e6c-4cf1-8949-63c4175bf168"))
                                                      .name("Senior Dev")
                                                      .build())
                                         .build();

    @BeforeEach
    void setUp() {
        logAppender = new LoggingAppender();
        logAppender.setContext(new LoggerContext());
        logAppender.start();

        Logger logger = (Logger) LoggerFactory.getLogger(ApiRequestLoggingAspect.class);
        logger.addAppender(logAppender);
    }

    @AfterEach
    void cleanUp() {
        logAppender.stop();
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "/dataset/EmployeeControllerTest/findAllWithSearchParams/DB.json")
    void findAllEmployeesWithSearchParams() {
        //arrange
        SearchParams params = SearchParams.builder().firstName("mikhail").build();

        String endpoint = "/employee/list";
        //act
        List<EmployeeDto> response = webTestClient.get()
                                                  .uri(uriBuilder -> uriBuilder.path(endpoint)
                                                                               .queryParam("firstName", params.getFirstName())
                                                                               .build())
                                                  .exchange()
                                                  //assert
                                                  .expectStatus()
                                                  .isOk()
                                                  .expectBodyList(EmployeeDto.class)
                                                  .returnResult()
                                                  .getResponseBody();

        assertApiRequestLog("List", "findAllEmployees", "SearchParams", params.toString(), ip, endpoint, "GET");

        assertThat(response).hasSize(1);
        assertThat(response.get(0)).usingRecursiveComparison()
                                   .isEqualTo(expectedDto);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "/dataset/EmployeeControllerTest/findById/DB.json")
    void findExistedById() {
        //arrange
        UUID id = expectedDto.getId();

        String endpoint = "/employee/" + id;
        //act
        EmployeeDto response = webTestClient.get()
                                            .uri(endpoint)
                                            .exchange()
                                            //assert
                                            .expectStatus()
                                            .isOk()
                                            .expectBody(EmployeeDto.class)
                                            .returnResult()
                                            .getResponseBody();

        assertApiRequestLog("EmployeeDto", "findById", "UUID", id.toString(), ip, endpoint, "GET");

        assertThat(response).usingRecursiveComparison()
                            .isEqualTo(expectedDto);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "/dataset/EmployeeControllerTest/create/DB.json")
    @ExpectedDataSet(value = "/dataset/EmployeeControllerTest/create/dbByCreate.json", ignoreCols = "employee.id, employee_characteristics.employee_id")
    void create() {
        //arrange
        String endpoint = "/employee";

        CreateEmployeeDto createEmployeeDto = CreateEmployeeDto.builder()
                                                               .firstName(expectedDto.getFirstName())
                                                               .lastName(expectedDto.getLastName())
                                                               .characteristics(expectedDto.getCharacteristics())
                                                               .description(expectedDto.getDescription())
                                                               .jobType(expectedDto.getJobType())
                                                               .contacts(expectedDto.getContacts())
                                                               .postId(expectedDto.getPost().getId())
                                                               .build();
        //act
        EmployeeDto response = webTestClient.post()
                                            .uri(endpoint)
                                            .bodyValue(createEmployeeDto)
                                            .exchange()
                                            //Assert
                                            .expectStatus()
                                            .isOk()
                                            .expectBody(EmployeeDto.class)
                                            .returnResult()
                                            .getResponseBody();

        assertApiRequestLog("EmployeeDto", "create", "CreateEmployeeDto", createEmployeeDto.toString(), ip, endpoint, "POST");

        assertThat(response).usingRecursiveComparison().ignoringFields("id")
                            .isEqualTo(expectedDto);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "/dataset/EmployeeControllerTest/updateExisted/DB.json")
    @ExpectedDataSet(value = "/dataset/EmployeeControllerTest/updateExisted/dbByUpdateExpected.json")
    void updateExisted() {
        //arrange
        String endpoint = "/employee/" + expectedDto.getId();

        UpdateEmployeeDto updateEmployeeDto = UpdateEmployeeDto.builder()
                                                               .firstName(expectedDto.getFirstName())
                                                               .lastName(expectedDto.getLastName())
                                                               .characteristics(expectedDto.getCharacteristics())
                                                               .description(expectedDto.getDescription())
                                                               .jobType(expectedDto.getJobType())
                                                               .contacts(expectedDto.getContacts())
                                                               .postId(expectedDto.getPost().getId())
                                                               .build();
        //act
        EmployeeDto response = webTestClient.put()
                                            .uri(endpoint)
                                            .bodyValue(updateEmployeeDto)
                                            .exchange()
                                            //Assert
                                            .expectStatus()
                                            .isOk()
                                            .expectBody(EmployeeDto.class)
                                            .returnResult()
                                            .getResponseBody();
        assertApiRequestLog("EmployeeDto", "update", "UUID,UpdateEmployeeDto", expectedDto.getId().toString() + ", " + updateEmployeeDto, ip, endpoint, "PUT");

        assertThat(response).usingRecursiveComparison()
                            .isEqualTo(expectedDto);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "/dataset/EmployeeControllerTest/delete/DB.json")
    @ExpectedDataSet(value = "/dataset/EmployeeControllerTest/delete/dbByDelete.json")
    void deleteById() {
        //arrange
        UUID id = UUID.fromString("ad4faaaf-1c1c-4442-87db-1df09c662f89");

        String endpoint = "/employee/" + id;
        //act
        webTestClient.delete()
                     .uri(endpoint)
                     .exchange()
                     //assert
                     .expectStatus()
                     .isOk();

        assertApiRequestLog("void", "deleteById", "UUID", id.toString(), ip, endpoint, "DELETE");
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "/dataset/EmployeeControllerTest/findByIdNotExisted/DB.json")
    void findNotExistedById() {
        //arrange
        UUID id = UUID.randomUUID();

        String endpoint = "/employee/" + id;
        //act
        MessageError er = webTestClient.get()
                                       .uri(endpoint)
                                       .exchange()
                                       //assert
                                       .expectStatus()
                                       .isNotFound()
                                       .expectBody(MessageError.class)
                                       .returnResult()
                                       .getResponseBody();

        assertApiRequestLog("EmployeeDto", "findById", "UUID", id.toString(), ip, endpoint, "GET");

        assertThat(er).usingRecursiveComparison()
                      .ignoringFields("timestamp")
                      .isEqualTo(MessageError.builder()
                                             .message("Employee with this id is not found")
                                             .build());
    }
//
//    private void loggingApiCheck(String api, List<String> params){
//        String loggingExpected = String.format("api: %s, ip: 127.0.0.1, parametrs: %s", api, params.stream().map(String::valueOf).collect(Collectors.joining("\t")));
//        assertLogging(loggingExpected);
//    }
//
//    private void loggingExceptions(String method, String id){
////        String loggingExpected = "Method com.dasha.controller.employee.EmployeeController."+method+" threw exception: Данного работника не существует " + id;
//        assertLogging(loggingExpected);
//    }
    // create - method= EmployeeDto com.evo.apatios.controller.employee.EmployeeController.create(CreateEmployeeDto), params= [CreateEmployeeDto(firstName=mikhail, lastName=bunkov, description=wwq, postId=4085e25e-6e6c-4cf1-8949-63c4175bf168, contacts=Contacts(phone=9929, email=email, workEmail=workEmail), characteristics=[Brave, Smart], jobType=CONTRACT)], request: ipAddress= 127.0.0.1, endpoint= /employee, requestTime= 2022-07-29T17:30:55.151918248, operation= POST
    // findExisted - method= EmployeeDto com.evo.apatios.controller.employee.EmployeeController.findById(UUID), params= [ad4faaaf-1c1c-4442-87db-1df09c662f89], request: ipAddress= 127.0.0.1, endpoint= /employee/ad4faaaf-1c1c-4442-87db-1df09c662f89, requestTime= 2022-07-29T17:30:55.704521828, operation= GET
//    find not exist - method= EmployeeDto com.evo.apatios.controller.employee.EmployeeController.findById(UUID), params= [e70b84fd-2016-4163-a492-9883060f7a29], request: ipAddress= 127.0.0.1, endpoint= /employee/e70b84fd-2016-4163-a492-9883060f7a29, requestTime= 2022-07-29T17:30:55.865798811, operation= GET
//    findall - method= List com.evo.apatios.controller.employee.EmployeeController.findAllEmployees(SearchParams), params= [SearchParams(firstName=mikhail, lastName=null, postId=null)], request: ipAddress= 127.0.0.1, endpoint= /employee/list, requestTime= 2022-07-29T17:30:56.297072600, operation= GET
//    update  apilogg- method= EmployeeDto com.evo.apatios.controller.employee.EmployeeController.update(UUID,UpdateEmployeeDto), params= [ad4faaaf-1c1c-4442-87db-1df09c662f89, UpdateEmployeeDto(firstName=mikhail, lastName=bunkov, description=wwq, postId=4085e25e-6e6c-4cf1-8949-63c4175bf168, contacts=Contacts(phone=9929, email=email, workEmail=workEmail), characteristics=[Brave, Smart], jobType=CONTRACT)], request: ipAddress= 127.0.0.1, endpoint= /employee/ad4faaaf-1c1c-4442-87db-1df09c662f89, requestTime= 2022-07-29T17:30:56.801016106, operation= PUT
//    updlog- Updating employee with id : ad4faaaf-1c1c-4442-87db-1df09c662f89, fields update : firstName: before [Anton] after [mikhail]. lastName: before [Ivanov] after [bunkov]. characteristics: before [[Something, Something 2]] after [[Brave, Smart]].
//    delete - method= void com.evo.apatios.controller.employee.EmployeeController.deleteById(UUID), params= [ad4faaaf-1c1c-4442-87db-1df09c662f89], request: ipAddress= 127.0.0.1, endpoint= /employee/ad4faaaf-1c1c-4442-87db-1df09c662f89, requestTime= 2022-07-29T17:30:57.199903003, operation= DELETE

    private void assertApiRequestLog(String returningType, String methodName, String methodArgsTypes, String params, String requestIdAddress, String endpoint, String operation) {
        String loggingExpected = String.format("method= %s com.evo.apatios.controller.employee.EmployeeController.%s(%s), params= [%s], request: ipAddress= %s, endpoint= %s, operation= %s", returningType, methodName, methodArgsTypes, params, requestIdAddress, endpoint, operation);
        assertLog(loggingExpected);
    }

    private void assertLog(String expectedLog) {
        assertThat(logAppender.getEvents()).isNotEmpty().anySatisfy(event -> assertThat(event.getMessage()).isEqualTo(expectedLog));
    }
}