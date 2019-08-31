package orthae.com.github.medicalmanagementsystem.client.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import orthae.com.github.medicalmanagementsystem.client.aspects.settings.SettingsService;

@Service
public class LoginServiceImpl implements LoginService {

    private WebClient client;
    private SettingsService settingsService;

    public LoginServiceImpl(SettingsService settingsService, @Value("${application.server-address}") String serverAddress){
        this.settingsService = settingsService;
        this.client = WebClient.create(serverAddress);
    }

    @Override
    public void login(String username, String password) throws WebClientResponseException {
        WebClient.UriSpec<WebClient.RequestBodySpec> request = client.post();
        WebClient.RequestHeadersSpec requestSpec = request.uri("/login").body(BodyInserters.fromObject(new LoginDto(username, password)));
        String token = requestSpec.retrieve().bodyToMono(String.class).block();
        settingsService.setSessionToken(token);
        System.out.println(settingsService.getSessionToken());
    }

}
