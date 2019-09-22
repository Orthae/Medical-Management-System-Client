package orthae.com.github.medicalmanagementsystem.client.management.sessions.service;

import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.SessionDto;

import java.util.List;

public interface SessionService {
    List<SessionDto> find(String username, String ipAddress, Boolean active, String created, String expiring);
    List<SessionDto> find(int employeeId);
    void invalidateSession(int sessionId);
    void invalidateEmployeeSessions(int employeeId);
}
