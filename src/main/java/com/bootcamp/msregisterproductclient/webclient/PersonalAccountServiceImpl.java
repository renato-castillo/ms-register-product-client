package com.bootcamp.msregisterproductclient.webclient;

import com.bootcamp.msregisterproductclient.exception.GenericException;
import com.bootcamp.msregisterproductclient.webclient.dto.PersonalAccountDto;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class PersonalAccountServiceImpl implements IPersonalAccountService {

    @Autowired
    private WebClient.Builder client;

    @Override
    public Mono<PersonalAccountDto> findByName(String name) {
        return client.build().get().uri("personal-account/api/personalaccounts/name".concat(name))
                .retrieve().bodyToMono(PersonalAccountDto.class)
                .onErrorResume(error -> {
                    WebClientResponseException response = (WebClientResponseException) error;

                    if(response.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new GenericException("Personal Account Not Found"));
                    }

                    return Mono.error(error);
                });
    }
}
