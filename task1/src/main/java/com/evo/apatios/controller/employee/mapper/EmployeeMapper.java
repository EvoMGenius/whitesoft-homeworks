package com.evo.apatios.controller.employee.mapper;

import com.evo.apatios.action.argument.CreationEmployeeActionArgument;
import com.evo.apatios.dto.EmployeeDto;
import com.evo.apatios.model.Employee;
import com.evo.apatios.action.argument.UpdatingEmployeeActionArgument;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeDto entityToDto(Employee employee){
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .description(employee.getDescription())
                .postId(employee.getPost().getId())
                .contacts(employee.getContacts())
                .characteristics(employee.getCharacteristics())
                .jobType(employee.getJobType())
                .build();
    }

    public CreationEmployeeActionArgument dtoToCreationActionArgument(EmployeeDto dto){
        return CreationEmployeeActionArgument.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .description(dto.getDescription())
                .postId(dto.getPostId())
                .contacts(dto.getContacts())
                .characteristics(dto.getCharacteristics())
                .jobType(dto.getJobType())
                .build();
    }

    public UpdatingEmployeeActionArgument dtoToUpdatingActionArgument(EmployeeDto employeeDto) {
        return UpdatingEmployeeActionArgument.builder()
                .id(employeeDto.getId())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .description(employeeDto.getDescription())
                .postId(employeeDto.getPostId())
                .contacts(employeeDto.getContacts())
                .characteristics(employeeDto.getCharacteristics())
                .jobType(employeeDto.getJobType())
                .build();
    }
}
