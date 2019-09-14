package orthae.com.github.medicalmanagementsystem.client.employees.service;

import org.springframework.stereotype.Service;
import orthae.com.github.medicalmanagementsystem.client.aspects.rest.RestClient;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeChangePasswordDto;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDetailsDto;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDto;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeesServiceImpl implements EmployeesService {

    private RestClient restClient;

    public EmployeesServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    public EmployeeDetailsDto find(int id) {
        return restClient.get("/employees/" + id, 200, EmployeeDetailsDto.class);
    }

    public List<EmployeeDto> find(String name, String surname, String username, String email) {
        StringBuilder queryBuilder = new StringBuilder("/employees?");
        if (name != null) {
            queryBuilder.append("name=");
            queryBuilder.append(name);
            queryBuilder.append("&");
        }
        if (surname != null) {
            queryBuilder.append("surname=");
            queryBuilder.append(surname);
            queryBuilder.append("&");
        }
        if (username != null) {
            queryBuilder.append("username=");
            queryBuilder.append(username);
            queryBuilder.append("&");
        }
        if (email != null) {
            queryBuilder.append("email=");
            queryBuilder.append(email);
            queryBuilder.append("&");
        }
        EmployeeDto[] employeeList = restClient.get(queryBuilder.toString(), 200, EmployeeDto[].class);
        return Arrays.asList(employeeList);
    }

    @Override
    public void delete(int id) {
        restClient.delete("/employees/" + id, 200);
    }

    @Override
    public void add(EmployeeDetailsDto dto) {
        restClient.post("/employees/", 201, dto);
    }

    @Override
    public void update(EmployeeDetailsDto dto) {
        restClient.put("/employees", 200, dto);
    }

    @Override
    public void activate(int id){
       restClient.post("/employees/" + id + "/active", 200);
    }

    @Override
    public void deactivate(int id){
        restClient.delete("/employees/" + id + "/active", 200);
    }

    @Override
    public void changePassword(int id, EmployeeChangePasswordDto dto) {
        restClient.put("/employees/" + id + "/password", 200, dto );
    }
}
