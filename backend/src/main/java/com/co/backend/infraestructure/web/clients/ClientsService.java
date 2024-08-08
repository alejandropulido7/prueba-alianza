package com.co.backend.infraestructure.web.clients;


import com.co.backend.domain.model.clients.Client;
import com.co.backend.domain.usecase.clients.ClientsUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientsService {

    private final ClientsUseCase clientsUseCase;

    @GetMapping
    public Flux<Client> getClients(){
        return clientsUseCase.getCLients();
    }

    @GetMapping("{sharedKey}")
    public Mono<Client> getClientsBySharedKey(@PathVariable String sharedKey){
        return clientsUseCase.getCLientsBySharedKey(sharedKey);
    }

    @PostMapping
    public Mono<Client> saveClient(@Valid @RequestBody ClientDto clientDto){
        return clientsUseCase.saveClient(clientDto);
    }
}
