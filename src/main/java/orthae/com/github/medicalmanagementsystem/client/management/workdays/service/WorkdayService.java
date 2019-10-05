package orthae.com.github.medicalmanagementsystem.client.management.workdays.service;

import orthae.com.github.medicalmanagementsystem.client.management.workdays.dto.WorkdayDto;

import java.util.List;

public interface WorkdayService {

    List<WorkdayDto> getByEmployeeId(int employeeId);
    void createWorkday(int employeeId, WorkdayDto dto);

}
