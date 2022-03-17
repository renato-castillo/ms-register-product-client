package com.bootcamp.msregisterproductclient.webclient;

import com.bootcamp.msregisterproductclient.exception.GenericException;
import com.bootcamp.msregisterproductclient.webclient.dto.PersonalAccountDto;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class PersonalAccountServiceImpl implements IPersonalAccountService {

    private static final String BASE_URL = "lb://ms-personal-accounts";

    @Autowired
    private WebClient.Builder client;

    @Override
    public Mono<PersonalAccountDto> findByName(String name) {
        return client.baseUrl(BASE_URL).build().get().uri("/api/personalaccounts/name/".concat(name))
                .retrieve().bodyToMono(PersonalAccountDto.class)
                .onErrorResume(error -> {
                    log.error("Error ocurred: {}", error);

                    WebClientResponseException response = (WebClientResponseException) error;

                    if(response.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new GenericException("Personal Account Not Found"));
                    }

                    return Mono.error(error);
                });
    }
}
