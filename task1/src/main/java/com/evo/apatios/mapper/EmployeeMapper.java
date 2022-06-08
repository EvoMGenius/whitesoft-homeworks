package com.evo.apatios.mapper;

import com.evo.apatios.argument.CreationEmployeeActionArgument;
import com.evo.apatios.argument.UpdateEmployeeActionArgument;
import com.evo.apatios.dto.EmployeeDto;
import com.evo.apatios.model.Employee;
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
    public UpdateEmployeeActionArgument dtoToUpdateActionArgument(EmployeeDto dto){
        return UpdateEmployeeActionArgument.builder()
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
}
