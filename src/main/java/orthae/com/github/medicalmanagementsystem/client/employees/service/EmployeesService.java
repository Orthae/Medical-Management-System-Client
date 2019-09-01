package orthae.com.github.medicalmanagementsystem.client.employees.service;

import orthae.com.github.medicalmanagementsystem.client.employees.dto.AuthorityDto;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDto;

import java.util.List;

public interface EmployeesService {

    List<EmployeeDto> find(String name, String surname, String username, String email);
    void add(String name, String surname, String username, String email, String password, List<AuthorityDto> authorities);
    void delete(int id);
}
