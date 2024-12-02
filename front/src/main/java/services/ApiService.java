package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.ambiente.bancario.dto.AccountDto;
import com.mycompany.ambiente.bancario.dto.CashDTO;
import com.mycompany.ambiente.bancario.dto.UserDto;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * ApiService responsável por realizar chamadas HTTP e retornar objetos
 * mapeados.
 */
public class ApiService {

    private static final String BASE_URL = "http://localhost:8080/account/";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ApiService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public AccountDto CriarConta(Object requestBody) throws JsonProcessingException {
        return sendPostRequest(BASE_URL + "create-account", requestBody, AccountDto.class);
    }
    
    public UserDto CriarUsuario(Object requestBody) throws JsonProcessingException {
        return sendPostRequest(BASE_URL + "create-user", requestBody, UserDto.class);
    }

    public AccountDto Logar(Object requestBody) throws JsonProcessingException {
        return sendPostRequest(BASE_URL + "login", requestBody, AccountDto.class);
    }

    public CashDTO Sacar(Object requestBody) throws JsonProcessingException {
        return sendPostRequest(BASE_URL + "cash-withdrawal", requestBody, CashDTO.class);
    }
    
    public CashDTO Depositar(Object requestBody) throws JsonProcessingException {
        return sendPostRequest(BASE_URL + "cash-deposit", requestBody, CashDTO.class);
    }

    private <T> T sendPostRequest(String url, Object requestBody, Class<T> responseType) {
        try {
            String requestBodyJson = objectMapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), responseType);
        } catch (IOException | InterruptedException e) {
            System.out.println("Error during HTTP request: " + e.getMessage());
            return null;
        }
    }
}
