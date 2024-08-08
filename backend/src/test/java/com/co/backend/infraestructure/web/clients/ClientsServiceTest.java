package com.co.backend.infraestructure.web.clients;

import com.co.backend.domain.model.clients.Client;
import com.co.backend.domain.usecase.clients.ClientsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest( controllers = ClientsService.class)
@RunWith(SpringRunner.class)
class ClientsServiceTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ClientsUseCase clientsUseCase;

    @Test
    void getClients_shouldReturnClients() {
        Client client1 = new Client(1L, "business1", "email1@example.com", "1234567890", "12345", LocalDate.now());
        Client client2 = new Client(2L, "business2", "email2@example.com", "0987654321", "123345", LocalDate.now());
        when(clientsUseCase.getCLients()).thenReturn(Flux.just(client1, client2));

        webTestClient.get()
                .uri("/clients")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Client.class).hasSize(2).contains(client1, client2);
    }

    @Test
    void getClientsBySharedKey_shouldReturnClient() {
        String sharedKey = "key1";
        Client client = new Client(1L, "business1", "email1@example.com", "1234567890", "12345", LocalDate.now());
        when(clientsUseCase.getCLientsBySharedKey(sharedKey)).thenReturn(Mono.just(client));

        webTestClient.get()
                .uri("/clients/{sharedKey}", sharedKey)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Client.class).isEqualTo(client);
    }

    @Test
    void saveClient_shouldSaveAndReturnClient() {
        ClientDto clientDto = new ClientDto("nombre", "business1", "email1@example.com", LocalDate.now(), LocalDate.now().plusDays(30));
        Client client = new Client(2L, "business1", "email1@example.com", "1234567890", "1234567890", LocalDate.now());
        when(clientsUseCase.saveClient(any(ClientDto.class))).thenReturn(Mono.just(client));

        webTestClient.post()
                .uri("/clients")
                .bodyValue(clientDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Client.class).isEqualTo(client);
    }

}