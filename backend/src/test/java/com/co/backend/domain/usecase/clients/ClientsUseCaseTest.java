package com.co.backend.domain.usecase.clients;

import com.co.backend.application.exceptions.ApplicationException;
import com.co.backend.domain.model.clients.Client;
import com.co.backend.infraestructure.adapters.r2dbc.clients.ClientsDao;
import com.co.backend.infraestructure.adapters.r2dbc.clients.ClientsDataRepository;
import com.co.backend.infraestructure.web.clients.ClientDto;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class ClientsUseCaseTest extends TestCase {

    @InjectMocks
    ClientsUseCase clientsUseCase;

    @Mock
    ClientsDataRepository clientsDataRepository;

    private ClientsDao clientsDao = ClientsDao.builder()
            .id(1L)
            .email("test@email.com")
            .shared_key("sharedKey")
            .business_id("businessId")
            .phone("phone")
            .end_date(LocalDate.now())
            .start_date(LocalDate.now())
            .build();

    private ClientDto clientDto = ClientDto.builder()
            .email("email@email.com")
            .name("Test Unit")
            .phone("12345")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now())
            .build();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        clientsUseCase = new ClientsUseCase(clientsDataRepository);
    }

    @Test
    void getCLients() {
        Mockito.when(clientsDataRepository.findAll()).thenReturn(Flux.just(clientsDao));
        Flux<Client> cLients = clientsUseCase.getCLients();
        StepVerifier.create(cLients).consumeNextWith(client -> {
            Assert.assertEquals(client.getDataAdded(), clientsDao.getStart_date());
            Assert.assertEquals(client.getBusinessId(), clientsDao.getBusiness_id());
            Assert.assertEquals(client.getSharedKey(), clientsDao.getShared_key());
            Assert.assertEquals(client.getPhone(), clientsDao.getPhone());
            Assert.assertEquals(client.getEmail(), clientsDao.getEmail());
        }).verifyComplete();
    }

    @Test
    void getCLientsBySharedKey() {
        Mockito.when(clientsDataRepository.findBySharedKey(any())).thenReturn(Mono.just(clientsDao));
        Mono<Client> result = clientsUseCase.getCLientsBySharedKey("test");
        StepVerifier.create(result).consumeNextWith(client -> {
            Assert.assertEquals(client.getEmail(), clientsDao.getEmail());
            Assert.assertEquals(client.getSharedKey(), clientsDao.getShared_key());
            Assert.assertEquals(client.getBusinessId(), clientsDao.getBusiness_id());
            Assert.assertEquals(client.getPhone(), clientsDao.getPhone());
            Assert.assertEquals(client.getDataAdded(), clientsDao.getStart_date());
        }).verifyComplete();
    }

    @Test
    void saveClient() {
        Mockito.when(clientsDataRepository.findByEmail(any())).thenReturn(Flux.empty());
        Mockito.when(clientsDataRepository.save(any())).thenReturn(Mono.just(clientsDao));
        Mono<Client> result = clientsUseCase.saveClient(clientDto);
        StepVerifier.create(result).consumeNextWith(client -> {
            Assert.assertEquals(clientsDao.getShared_key(), client.getSharedKey());
            Assert.assertEquals(clientsDao.getEmail(), client.getEmail());
            Assert.assertNull(client.getBusinessId());
        }).verifyComplete();
    }

    @Test
    void saveClientError() {
        Mockito.when(clientsDataRepository.findByEmail(any())).thenReturn(Flux.just(clientsDao));
        Mockito.when(clientsDataRepository.save(any())).thenReturn(Mono.just(clientsDao));
        Mono<Client> result = clientsUseCase.saveClient(clientDto);
        StepVerifier.create(result).verifyError(ApplicationException.class);
    }
}