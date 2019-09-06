package orthae.com.github.medicalmanagementsystem.client.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import orthae.com.github.medicalmanagementsystem.client.aspects.exceptions.Error;
import orthae.com.github.medicalmanagementsystem.client.aspects.settings.SettingsService;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDetailsDto;

@Service
public class LoginServiceImpl implements LoginService {

    private WebClient client;
    private SettingsService settingsService;

    public LoginServiceImpl(SettingsService settingsService, @Value("${application.server-address}") String serverAddress) {
        this.settingsService = settingsService;
        this.client = WebClient.create(serverAddress);
    }

    @Override
    public void login(String username, String password) throws WebClientResponseException {
        ClientResponse response = client.post().uri("/login").body(BodyInserters.fromObject(new LoginDto(username, password))).exchange().block();
        if (response != null) {
            if (response.rawStatusCode() == 202) {
                String token = response.bodyToMono(String.class).block();
                settingsService.setSessionToken(token);
                setEmployee();
            } else {
                Error error = response.bodyToMono(Error.class).block();
                if (error != null) {
//  TODO Fix error message
                    throw new RuntimeException(error.toString());
                } else {
                    throw new RuntimeException("Error response is null");
                }
            }
        } else {
            throw new RuntimeException("Server response in null");
        }
    }

//  TODO change to public ?
    private void setEmployee() {
        ClientResponse response = client.get().uri("/login").header("Authorization", "Bearer " + settingsService.getSessionToken()).exchange().block();
        if (response != null) {
            if (response.rawStatusCode() == 200) {
                settingsService.setLoggedEmployee(response.bodyToMono(EmployeeDetailsDto.class).block());
            }
        }
    }

}
