package com.evo.apatios.mapper;

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
}
