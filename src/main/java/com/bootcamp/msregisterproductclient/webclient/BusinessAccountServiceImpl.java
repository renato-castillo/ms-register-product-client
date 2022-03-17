package com.bootcamp.msregisterproductclient.webclient;

import com.bootcamp.msregisterproductclient.exception.GenericException;
import com.bootcamp.msregisterproductclient.webclient.dto.BusinessAccountDto;
import com.bootcamp.msregisterproductclient.webclient.dto.PersonalAccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BusinessAccountServiceImpl implements IBusinessAccountService {

    private static final String BASE_URL = "lb://ms-business-accounts";

    @Autowired
    private WebClient.Builder client;

    @Override
    public Mono<BusinessAccountDto> findByName(String name) {
        return client.baseUrl(BASE_URL).build().get().uri("/api/business-account/name/".concat(name))
                .retrieve().bodyToMono(BusinessAccountDto.class)
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
