package orthae.com.github.medicalmanagementsystem.client.aspects.rest;

import org.springframework.web.reactive.function.client.ClientResponse;

public interface RestClient {
    <T> T get(String uri, int successCode, Class<T> aClass);
    void get(String uri, int successCode);
    <T, B> T post(String uri, int successCode, B requestBody, Class<T> aClass);
    <B> void post(String uri, int successCode, B requestBody);
    void post(String uri, int successCode);
    <T, B> T put(String uri, int successCode, B requestBody, Class<T> aClass);
    <B> void put(String uri, int successCode, B requestBody);
    <T> T delete(String uri, int successCode, Class<T> aClass);
    void delete(String uri, int successCode);

    <T> T handleResponse(ClientResponse response, int successCode, Class<T> aClass);
    void handleResponse(ClientResponse response, int successCode);
}
