package com.evo.apatios.mapper;

import com.evo.apatios.argument.CreationEmployeeActionArgument;
import com.evo.apatios.dto.EmployeeDto;
import com.evo.apatios.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public EmployeeDto entityToDto(Employee employee){
        return EmployeeDto.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .characteristics(employee.getCharacteristics())
                .description(employee.getDescription())
                .postId(employee.getPost().getId())
                .build();
    }

    public CreationEmployeeActionArgument dtoToCreationActionArgument(EmployeeDto dto){
        return CreationEmployeeActionArgument.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .characteristics(dto.getCharacteristics())
                .description(dto.getDescription())
                .postId(dto.getPostId())
                .build();
    }
}
