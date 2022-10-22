package com.evo.bunkov.controller.employee.mapper;

import com.evo.bunkov.action.argument.CreateEmployeeActionArgument;
import com.evo.bunkov.action.argument.UpdateEmployeeActionArgument;
import com.evo.bunkov.dto.input.employee.CreateEmployeeDto;
import com.evo.bunkov.dto.input.employee.UpdateEmployeeDto;
import com.evo.bunkov.dto.output.employee.EmployeeDto;
import com.evo.bunkov.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper EMPLOYEE_MAPPER = Mappers.getMapper(EmployeeMapper.class);

    CreateEmployeeActionArgument createDtoToArgument(CreateEmployeeDto dto);

    UpdateEmployeeActionArgument updateDtoToArgument(UpdateEmployeeDto dto);

    EmployeeDto entityToDto(Employee employee);
}
