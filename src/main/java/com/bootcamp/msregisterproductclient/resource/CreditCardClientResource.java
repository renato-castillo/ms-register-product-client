package com.bootcamp.msregisterproductclient.resource;

import com.bootcamp.msregisterproductclient.dto.CreditCardClientDto;
import com.bootcamp.msregisterproductclient.dto.CreditClientDto;
import com.bootcamp.msregisterproductclient.entity.CreditCardClient;
import com.bootcamp.msregisterproductclient.service.ICreditCardClientService;
import com.bootcamp.msregisterproductclient.webclient.ICreditCardService;
import com.bootcamp.msregisterproductclient.webclient.dto.CreditCardDto;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
public class CreditCardClientResource extends ModelMapper {
    @Autowired
    private ICreditCardClientService iCreditCardClientService;

    @Autowired
    private ICreditCardService creditCardService;

    public Mono<CreditCardClientDto> create(CreditCardClientDto creditCardClientDto) {
        CreditCardClient creditCardClient = map(creditCardClientDto, CreditCardClient.class);
        creditCardClient.setId(new ObjectId().toString());
        creditCardClient.setCreatedAt(LocalDateTime.now());

        return creditCardService.findByNameAndClientType(creditCardClientDto.getCreditCard().getName(),
                creditCardClientDto.getClient().getClientType()).switchIfEmpty(Mono.error(new Exception()))
                .flatMap(x -> Mono.from(iCreditCardClientService.findAll().filter(c -> c.getClient().getNumberDocument().equals(creditCardClientDto.getClient().getNumberDocument()))
                        .switchIfEmpty(iCreditCardClientService.save(creditCardClient))
                        .map(y -> creditCardClientDto)));
        /*
        Flux<CreditCardClientDto> temp = iCreditCardClientService.findAll()
                .filter(t->t.getClient().getNumberDocument().equals(creditCardClientDto.getClient().getNumberDocument()))
                .switchIfEmpty(iCreditCardClientService.save(creditCardClient))
                .map(et-> creditCardClientDto);



        return Mono.from(temp);
         */
    }

    public Flux<CreditCardClientDto> findAll(){
        return iCreditCardClientService.findAll().map(x->map(x,CreditCardClientDto.class));
    }

    public Mono<CreditCardClientDto> update(CreditCardClientDto creditCardClientDto){
        return iCreditCardClientService.findById(creditCardClientDto.getId())
                .switchIfEmpty(Mono.error(new Exception()))
                .flatMap(p->{
                    CreditCardClient creditCardClient =  map(creditCardClientDto,CreditCardClient.class);
                    creditCardClient.setUpdatedAt(LocalDateTime.now());
                    return iCreditCardClientService.save(creditCardClient).map(y->map(y,CreditCardClientDto.class));
                });
    }
    public Mono<CreditCardClientDto> findById(String id){
        return iCreditCardClientService.findById(id)
                .switchIfEmpty(Mono.error(new Exception()))
                .map(x-> map(x,CreditCardClientDto.class));
    }

    public Mono<Void> delete(CreditCardClientDto creditCardClientDto)
    {
        return iCreditCardClientService.findById(creditCardClientDto.getId())
                .switchIfEmpty(Mono.error(new Exception()))
                .flatMap(x-> iCreditCardClientService.deleteById(creditCardClientDto.getId()));
    }
}
