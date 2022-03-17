package com.bootcamp.msregisterproductclient.webclient;

import com.bootcamp.msregisterproductclient.webclient.dto.BusinessAccountDto;
import reactor.core.publisher.Mono;

public interface IBusinessAccountService {

    Mono<BusinessAccountDto> findByName(String name);
}
