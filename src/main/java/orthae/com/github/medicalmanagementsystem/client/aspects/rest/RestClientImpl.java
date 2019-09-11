package orthae.com.github.medicalmanagementsystem.client.aspects.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import orthae.com.github.medicalmanagementsystem.client.aspects.exceptions.ExceptionResponse;
import orthae.com.github.medicalmanagementsystem.client.aspects.settings.SettingsService;

@Component
public class RestClientImpl implements RestClient {

    private WebClient client;
    private SettingsService settingsService;

    public RestClientImpl(@Value("${application.server-address}") String serverAddress, SettingsService settingsService) {
        this.client = WebClient.create(serverAddress);
        this.settingsService = settingsService;
    }

    @Override
    public <T> T get(String uri, int successCode, Class<T> aClass) {
        ClientResponse response = client.get().uri(uri).header("Authorization", "Bearer " + settingsService.getSessionToken()).exchange().block();
        return handleResponse(response, successCode, aClass);
    }

    @Override
    public void get(String uri, int successCode) {
        throw new RuntimeException("METHOD NOT IMPLEMENTED");
    }

    @Override
    public <T, D> T post(String uri, int successCode, D requestBody, Class<T> aClass) {
        ClientResponse response = client.post().uri(uri).header("Authorization", "Bearer " + settingsService.getSessionToken())
                .body(BodyInserters.fromObject(requestBody)).exchange().block();
        return handleResponse(response, successCode, aClass);
    }

    @Override
    public <D> void post(String uri, int successCode, D requestBody) {
        ClientResponse response = client.post().uri(uri).header("Authorization", "Bearer " + settingsService.getSessionToken())
                .body(BodyInserters.fromObject(requestBody)).exchange().block();
        handleResponse(response, successCode);
    }

    @Override
    public void post(String uri, int successCode){
        ClientResponse response = client.post().uri(uri).header("Authorization", "Bearer " + settingsService.getSessionToken()).exchange().block();
        handleResponse(response, successCode);
    }

    @Override
    public <T, B> T put(String uri, int successCode, B requestBody, Class<T> aClass) {
        throw new RuntimeException("METHOD NOT IMPLEMENTED");
    }

    @Override
    public <B> void put(String uri, int successCode, B requestBody) {
        ClientResponse response = client.put().uri(uri).header("Authorization", "Bearer " + settingsService.getSessionToken())
                .body(BodyInserters.fromObject(requestBody)).exchange().block();
        handleResponse(response, successCode);
    }

    @Override
    public <T> T delete(String uri, int successCode, Class<T> aClass) {
        throw new RuntimeException("METHOD NOT IMPLEMENTED");
    }

    @Override
    public void delete(String uri, int successCode) {
        ClientResponse response = client.delete().uri(uri).header("Authorization", "Bearer " + settingsService.getSessionToken()).exchange().block();
        handleResponse(response, successCode);
    }


    @Override
    public <T> T handleResponse(ClientResponse response, int successCode, Class<T> aClass) {
        if (response != null) {
            if (response.rawStatusCode() == successCode) {
                T body = response.bodyToMono(aClass).block();
                if (body != null)
                    return body;
                else
                    throw new RuntimeException("Failed to bind server response to a " + aClass.getName() + " object");
            } else {
                ExceptionResponse exceptionResponse = response.bodyToMono(ExceptionResponse.class).block();
                if (exceptionResponse != null)
                    throw new RuntimeException(exceptionResponse.toString());
                else
                    throw new RuntimeException("Exception response is null");
            }
        } else {
            throw new RuntimeException("Server response is null");
        }
    }

    @Override
    public void handleResponse(ClientResponse response, int successCode) {
        if (response != null) {
            if (response.rawStatusCode() != successCode) {
                ExceptionResponse exceptionResponse = response.bodyToMono(ExceptionResponse.class).block();
                if (exceptionResponse != null)
                    throw new RuntimeException(exceptionResponse.toString());
                else
                    throw new RuntimeException("Exception response is null");
            }
        } else {
            throw new RuntimeException("Server response is null");
        }
    }


}
