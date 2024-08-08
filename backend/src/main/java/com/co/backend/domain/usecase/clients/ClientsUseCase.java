package com.co.backend.domain.usecase.clients;


import com.co.backend.application.exceptions.ApplicationException;
import com.co.backend.domain.model.clients.Client;
import com.co.backend.infraestructure.adapters.r2dbc.clients.ClientsDao;
import com.co.backend.infraestructure.adapters.r2dbc.clients.ClientsDataRepository;
import com.co.backend.infraestructure.web.clients.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientsUseCase {

    private final ClientsDataRepository clientsDataRepository;

    public Flux<Client> getCLients(){
        return clientsDataRepository.findAll()
                .flatMap(clientsDao -> {
                    return Flux.just(TransformerClientDaoToClient.convert(clientsDao));
                });
    }

    public Mono<Client> getCLientsBySharedKey(String sharedKey){
        return clientsDataRepository.findBySharedKey(sharedKey)
                .flatMap(clientsDao -> Mono.just(TransformerClientDaoToClient.convert(clientsDao)));
    }

    public Mono<Client> saveClient(ClientDto clientDto){
        return clientsDataRepository.findByEmail(clientDto.getEmail())
                .collectList()
                .flatMap(clientsDaos -> {
                    if(clientsDaos.size() > 0){
                        return Mono.error(new ApplicationException("Email already exists"));
                    }
                    return this.proccessClient(clientDto);
                });
    }

    private Mono<Client> proccessClient(ClientDto clientDto){
        String[] split = clientDto.getEmail().split("@");
        String sharedKey = split[0];

        ClientsDao clientsDao = ClientsDao.builder()
                .business_id(clientDto.getName())
                .shared_key(sharedKey)
                .email(clientDto.getEmail())
                .phone(clientDto.getPhone())
                .start_date(clientDto.getStartDate())
                .end_date(clientDto.getEndDate())
                .build();

        return clientsDataRepository.save(clientsDao)
                .flatMap(clientsDao1 -> Mono.just(Client.builder()
                                .sharedKey(clientsDao1.getShared_key())
                                .email(clientsDao1.getEmail())
                                .build()));
    }


}
