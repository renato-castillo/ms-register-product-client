package com.bootcamp.msregisterproductclient.webclient;

import com.bootcamp.msregisterproductclient.webclient.dto.PersonalAccountDto;
import reactor.core.publisher.Mono;

public interface IPersonalAccountService {

    Mono<PersonalAccountDto> findByName(String name);
}
