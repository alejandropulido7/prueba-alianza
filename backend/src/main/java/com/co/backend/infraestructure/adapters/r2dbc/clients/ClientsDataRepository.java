package com.co.backend.infraestructure.adapters.r2dbc.clients;

import com.co.backend.domain.model.clients.Client;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ClientsDataRepository extends R2dbcRepository<ClientsDao, Long> {

    Flux<ClientsDao> findByEmail(String email);

    @Query("SELECT c.* FROM clients c WHERE c.shared_key = :#{[0]}")
    Mono<ClientsDao> findBySharedKey(String sharedKey);
}
