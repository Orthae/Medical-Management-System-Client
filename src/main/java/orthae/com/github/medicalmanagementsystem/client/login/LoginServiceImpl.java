package orthae.com.github.medicalmanagementsystem.client.login;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import orthae.com.github.medicalmanagementsystem.client.aspects.rest.RestClient;
import orthae.com.github.medicalmanagementsystem.client.aspects.settings.SettingsService;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.EmployeeDetailsDto;

@Service
public class LoginServiceImpl implements LoginService {

    private RestClient restClient;
    private SettingsService settingsService;

    public LoginServiceImpl(SettingsService settingsService, RestClient restClient) {
        this.settingsService = settingsService;
        this.restClient = restClient;
    }

    @Override
    public void login(String username, String password) throws WebClientResponseException {
        //  Obtaining session token
        String sessionToken = restClient.post("/login/", 202, new LoginDto(username, password), String.class );
        settingsService.setSessionToken(sessionToken);
        //  Obtaining logged in employee
        EmployeeDetailsDto loggedEmployee = restClient.get("/login/", 200, EmployeeDetailsDto.class);
        settingsService.setLoggedEmployee(loggedEmployee);
    }

}
