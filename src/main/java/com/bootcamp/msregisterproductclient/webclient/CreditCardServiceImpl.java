package com.bootcamp.msregisterproductclient.webclient;

import com.bootcamp.msregisterproductclient.entity.CreditCard;
import com.bootcamp.msregisterproductclient.webclient.dto.CreditCardDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CreditCardServiceImpl implements ICreditCardService {

    @Autowired
    private WebClient client;

    @Override
    public Flux<CreditCardDto> findByClientType(String clientType) {

        return client.get()
                .uri("/creditcard")
                .retrieve()
                .bodyToFlux(CreditCardDto.class);

    }

    @Override
    public Mono<CreditCardDto> findByName(String name) {

        return client.get().uri("/creditcard/name/" + name)
                .retrieve()
                .bodyToMono(CreditCardDto.class);
    }

    @Override
    public Mono<CreditCardDto> findByNameAndClientType(String name, String clientType) {
        return client.get()
                .uri("/creditcard/name/".concat(name).concat("/client-type/").concat(clientType))
                .retrieve()
                .bodyToMono(CreditCardDto.class);
    }
}
