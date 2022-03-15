package com.bootcamp.msregisterproductclient.webclient;

import com.bootcamp.msregisterproductclient.entity.CreditCard;
import com.bootcamp.msregisterproductclient.webclient.dto.CreditCardDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditCardService {

    Flux<CreditCardDto> findByClientType(String clientType);

    Mono<CreditCardDto> findByName(String name);

     Mono<CreditCardDto> findByNameAndClientType(String name, String clientType);
}
