package orthae.com.github.medicalmanagementsystem.client.management.employees.service;

import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.EmployeeChangePasswordDto;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.EmployeeDetailsDto;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.EmployeeDto;

import java.util.List;

public interface EmployeesService {

    EmployeeDetailsDto find(int id);
    List<EmployeeDto> find(String name, String surname, String username, String email);
    void add(EmployeeDetailsDto dto);
    void update(EmployeeDetailsDto dto);
    void activate(int id);
    void deactivate(int id);
    void changePassword(int id, EmployeeChangePasswordDto dto);
    void delete(int id);
}
