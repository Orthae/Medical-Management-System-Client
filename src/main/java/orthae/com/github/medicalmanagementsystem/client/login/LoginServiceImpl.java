package orthae.com.github.medicalmanagementsystem.client.login;

import io.netty.channel.ChannelException;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.annotation.PostConstruct;
import java.net.ConnectException;
import java.net.URI;

@Service
public class LoginServiceImpl extends Task implements LoginService {

    @Value("${application.server-address}")
    private String serverAddress;
    private WebClient client;

    @PostConstruct
    public void init(){
        this.client = WebClient.create(serverAddress);
    }

    @Override
    public void login(String username, String password) throws WebClientResponseException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebClient.UriSpec<WebClient.RequestBodySpec> request = client.post();
        WebClient.RequestHeadersSpec requestSpec = request.uri("/login").body(BodyInserters.fromObject(new LoginDto(username, password)));
        String token = requestSpec.retrieve().bodyToMono(String.class).block();
        System.out.println("TOKEN");
        System.out.println(token);
    }

    @Override
    protected Object call() throws Exception {
        return null;
    }
}
