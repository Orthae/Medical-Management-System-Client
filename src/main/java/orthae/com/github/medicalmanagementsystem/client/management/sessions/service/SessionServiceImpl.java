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
    public List<SessionDto> find(String username, String ipAddress, Boolean active, String created, String expiring) {
        StringBuilder query = new StringBuilder("/employees/sessions?");
        if(username != null)
            query.append("username=").append(username).append("&");
        if(ipAddress != null)
            query.append("ipAddress=").append(ipAddress).append("&");
        if(active != null)
            query.append("active=").append(active).append("&");
        if(created != null)
            query.append("created=").append(created).append("&");
        if(expiring != null)
            query.append("expiring=").append(expiring).append("&");
        SessionDto[] list = restClient.get(query.toString(), 200, SessionDto[].class);
        return Arrays.asList(list);
    }

    @Override
    public List<SessionDto> find(int employeeId) {
        SessionDto[] list = restClient.get("/employees/" + employeeId + "/sessions", 200, SessionDto[].class);
        return Arrays.asList(list);
    }

    @Override
    public void invalidateSession(int sessionId) {
        restClient.delete("/employees/sessions/" + sessionId, 200);
    }

    @Override
    public void invalidateEmployeeSessions(int employeeId) {
        restClient.delete("/employees/" + employeeId + "/sessions", 200);
    }
}
