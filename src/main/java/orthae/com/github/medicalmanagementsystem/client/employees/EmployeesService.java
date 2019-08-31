package orthae.com.github.medicalmanagementsystem.client.employees;

import java.util.List;

public interface EmployeesService {

    List<EmployeeDto> find(String name, String surname, String username, String email);
    void delete(int id);
}
