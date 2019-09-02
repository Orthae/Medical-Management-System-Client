package orthae.com.github.medicalmanagementsystem.client.employees.service;

import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDetailsDto;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDto;

import java.util.List;

public interface EmployeesService {

    EmployeeDetailsDto find(int id);
    List<EmployeeDto> find(String name, String surname, String username, String email);
    void add(EmployeeDetailsDto dto);
    void update(EmployeeDetailsDto dto);
    void delete(int id);
}
