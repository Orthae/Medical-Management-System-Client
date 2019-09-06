package orthae.com.github.medicalmanagementsystem.client.aspects.settings;

import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDetailsDto;

public interface SettingsService {

    String getSessionToken();
    void setSessionToken(String sessionToken);

    EmployeeDetailsDto getLoggedEmployee();
    void setLoggedEmployee(EmployeeDetailsDto loggedEmployee);

}
