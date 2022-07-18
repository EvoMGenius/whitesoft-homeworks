package com.evo.apatios.controller.employee.mapper;

import com.evo.apatios.action.argument.CreateEmployeeActionArgument;
import com.evo.apatios.action.argument.UpdateEmployeeActionArgument;
import com.evo.apatios.dto.input.employee.CreateEmployeeDto;
import com.evo.apatios.dto.input.employee.UpdateEmployeeDto;
import com.evo.apatios.dto.output.employee.EmployeeDto;
import com.evo.apatios.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper EMPLOYEE_MAPPER = Mappers.getMapper(EmployeeMapper.class);

    CreateEmployeeActionArgument createDtoToArgument(CreateEmployeeDto dto);

    UpdateEmployeeActionArgument updateDtoToArgument(UpdateEmployeeDto dto);

    EmployeeDto entityToDto(Employee employee);
}
