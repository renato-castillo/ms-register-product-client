package com.bootcamp.msregisterproductclient.resource;

import com.bootcamp.msregisterproductclient.dto.CreditClientDto;
import com.bootcamp.msregisterproductclient.entity.CreditClient;
import com.bootcamp.msregisterproductclient.service.ICreditClientService;
import com.bootcamp.msregisterproductclient.util.MapperUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class CreditClientResource extends MapperUtil {

    @Autowired
    public ICreditClientService iCreditClientService;

    public Mono<CreditClientDto> create(CreditClientDto creditClientDto) {
        CreditClient creditClient = map(creditClientDto,CreditClient.class);

       if (creditClient.getClient().getClientType().equals(TypeClient.Company.name())){
            creditClient.setId(new ObjectId().toString());
            creditClient.setCreatedAt(LocalDateTime.now());
            Mono<CreditClient> entity = iCreditClientService.save(creditClient);
            return  entity.map(x->map(x,CreditClientDto.class));
        }else{
            creditClient.setId(new ObjectId().toString());
            creditClient.setCreatedAt(LocalDateTime.now());
            Flux<CreditClientDto> temp = iCreditClientService.findAll()
                    .filter(p->p.isState())
                    .filter(t->t.getClient().getNumberDocument().equals(creditClientDto.getClient().getNumberDocument()))
                    .switchIfEmpty(iCreditClientService.save(creditClient))
                    .map(x->creditClientDto);
            return Mono.from(temp);
        }

    }

    public Flux<CreditClientDto> findAll(){
        return iCreditClientService.findAll().map(x->map(x,CreditClientDto.class));
    }

    public Mono<CreditClientDto> update(CreditClientDto creditClientDto){
        return iCreditClientService.findById(creditClientDto.getId())
                .switchIfEmpty(Mono.error(new Exception()))
                .flatMap(p->{
                    CreditClient creditClient =  map(creditClientDto,CreditClient.class);
                    creditClient.setUpdatedAt(LocalDateTime.now());
                    return iCreditClientService.save(creditClient).map(y->map(y,CreditClientDto.class));
                });
    }
    public Mono<CreditClientDto> findById(String id){
        return iCreditClientService.findById(id)
                .switchIfEmpty(Mono.error(new Exception()))
                .map(x-> map(x,CreditClientDto.class));
    }

    public Mono<Void> delete(CreditClientDto creditClientDto)
    {
        return iCreditClientService.findById(creditClientDto.getId())
                .switchIfEmpty(Mono.error(new Exception()))
                .flatMap(x-> iCreditClientService.deleteById(creditClientDto.getId()));
    }
}
