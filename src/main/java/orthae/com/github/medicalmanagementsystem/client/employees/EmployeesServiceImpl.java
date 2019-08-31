package orthae.com.github.medicalmanagementsystem.client.employees;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import orthae.com.github.medicalmanagementsystem.client.aspects.settings.SettingsService;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class EmployeesServiceImpl implements EmployeesService {

    private WebClient client;
    private SettingsService settingsService;
    private ObjectMapper mapper;

    public EmployeesServiceImpl(@Value("${application.server-address}") String serverAddress, SettingsService settingsService, ObjectMapper objectMapper){
        this.client = WebClient.create(serverAddress);
        this.settingsService = settingsService;
        this.mapper = objectMapper;
    }

    public List<EmployeeDto> find(String name, String surname, String username, String email){
        StringBuilder queryBuilder = new StringBuilder("/employees?");
        if(name != null){
            queryBuilder.append("name=");
            queryBuilder.append(name);
            queryBuilder.append("&");
        }
        if(surname != null){
            queryBuilder.append("surname=");
            queryBuilder.append(surname);
            queryBuilder.append("&");
        }
        if(username != null){
            queryBuilder.append("username=");
            queryBuilder.append(username);
            queryBuilder.append("&");
        }
        if(email != null){
            queryBuilder.append("email=");
            queryBuilder.append(email);
            queryBuilder.append("&");
        }
        String payload =  Objects.requireNonNull(client.get().uri(queryBuilder.toString()).header("Authorization", "Bearer " + settingsService.getSessionToken())
                .exchange().block()).bodyToMono((String.class)).block();
        try {
            EmployeeDto[] list = mapper.readValue(payload, EmployeeDto[].class);
            return Arrays.asList(list);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void delete(int id) {
        Objects.requireNonNull(client.delete().uri("/employees/" + id).header("Authorization", "Bearer " + settingsService.getSessionToken())
                .exchange().block());


    }

}
