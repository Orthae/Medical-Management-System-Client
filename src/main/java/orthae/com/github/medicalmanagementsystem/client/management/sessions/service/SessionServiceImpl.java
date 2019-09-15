package orthae.com.github.medicalmanagementsystem.client.management.sessions.service;

import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.aspects.rest.RestClient;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.SessionDto;

import java.util.Arrays;
import java.util.List;

@Component
public class SessionServiceImpl implements SessionService {

    private RestClient restClient;

    public SessionServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<SessionDto> find() {
        SessionDto[] list = restClient.get("/employees/sessions/", 200, SessionDto[].class);
        return Arrays.asList(list);
    }

    @Override
    public List<SessionDto> find(int employeeId) {
        SessionDto[] list = restClient.get("/employees/" + employeeId + "/sessions", 200, SessionDto[].class);
        return Arrays.asList(list);
    }
}
