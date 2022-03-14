package com.bootcamp.msregisterproductclient.util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICrud <T,V> {
    Mono<T> save(T t);
    Mono<Void>deleteById(V v);
    Mono<T> findById(V v);
    Flux<T> findAll();
   

}
