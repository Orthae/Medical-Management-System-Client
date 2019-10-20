package orthae.com.github.medicalmanagementsystem.client.management.workdays.service;

import orthae.com.github.medicalmanagementsystem.client.management.workdays.dto.WorkdayDto;

import java.util.List;

public interface WorkdayService {

    List<WorkdayDto> getByEmployeeIdAndMonth(int employeeId, int month, int year);
    void createWorkday(int employeeId, WorkdayDto dto);
    void deleteWorkday(int workdayId);

}
