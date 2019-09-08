package orthae.com.github.medicalmanagementsystem.client.employees.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import orthae.com.github.medicalmanagementsystem.client.aspects.rest.RestClient;
import orthae.com.github.medicalmanagementsystem.client.aspects.settings.SettingsService;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDetailsDto;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDto;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeesServiceImpl implements EmployeesService {

    private WebClient client;
    private RestClient restClient;
    private SettingsService settingsService;

    public EmployeesServiceImpl(@Value("${application.server-address}") String serverAddress, SettingsService settingsService, RestClient restClient) {
        this.client = WebClient.create(serverAddress);
        this.settingsService = settingsService;
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

    public void add(EmployeeDetailsDto dto) {
        restClient.post("/employees/", 201, dto);
    }


    public void update(EmployeeDetailsDto dto) {
        restClient.put("/employees", 200, dto);
    }

}
