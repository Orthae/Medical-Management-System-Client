package orthae.com.github.medicalmanagementsystem.client.management.sessions.service;

import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.SessionDto;

import java.util.List;

public interface SessionService {
    List<SessionDto> find();
    List<SessionDto> find(int employeeId);
}
