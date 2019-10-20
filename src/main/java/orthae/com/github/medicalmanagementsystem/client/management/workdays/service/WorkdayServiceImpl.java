package orthae.com.github.medicalmanagementsystem.client.management.workdays.service;

import org.springframework.stereotype.Service;
import orthae.com.github.medicalmanagementsystem.client.aspects.rest.RestClient;
import orthae.com.github.medicalmanagementsystem.client.management.workdays.dto.WorkdayDto;

import java.util.Arrays;
import java.util.List;

@Service
public class WorkdayServiceImpl implements WorkdayService {

    private RestClient restClient;

    public WorkdayServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<WorkdayDto> getByEmployeeIdAndMonth(int employeeId, int month, int year) {
        WorkdayDto[] list = restClient.get("/employees/" + employeeId + "/workdays/" + month + "/" + year , 200, WorkdayDto[].class);
        return Arrays.asList(list);
    }

    @Override
    public void createWorkday(int employeeId, WorkdayDto dto) {
        restClient.post("/employees/" + employeeId + "/workdays", 200, dto);
    }

    @Override
    public void deleteWorkday(int workdayId) {
        restClient.delete("/employees/workdays/" + workdayId, 200);
    }
}
