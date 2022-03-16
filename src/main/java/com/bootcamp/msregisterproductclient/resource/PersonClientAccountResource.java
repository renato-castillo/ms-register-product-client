package com.bootcamp.msregisterproductclient.resource;

import com.bootcamp.msregisterproductclient.dto.CompanyClientAccountDto;
import com.bootcamp.msregisterproductclient.dto.PersonClientAccountDto;
import com.bootcamp.msregisterproductclient.entity.CompanyClientAccount;
import com.bootcamp.msregisterproductclient.entity.PersonClientAccount;
import com.bootcamp.msregisterproductclient.exception.ModelNotFoundException;
import com.bootcamp.msregisterproductclient.service.IPersonClientAccountService;
import com.bootcamp.msregisterproductclient.util.MapperUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class PersonClientAccountResource extends MapperUtil {

    @Autowired
    private IPersonClientAccountService iPersonClientAccountService;

    public Mono<PersonClientAccountDto> create(PersonClientAccountDto personClientAccountDto){
        PersonClientAccount personClientAccount = map(personClientAccountDto,PersonClientAccount.class);
        personClientAccount.setId(new ObjectId().toString());
        personClientAccount.setCreatedAt(LocalDateTime.now());

        String typeAccount = personClientAccount.getTypeAccount().getName();
        String typeClient = personClientAccount.getClient().getClientType();
        if(typeAccount.equals(TypeAccount.SAVINGACCOUNT.name()) && typeClient.equals(TypeClient.Person.name())){
            Flux<PersonClientAccountDto> temp = iPersonClientAccountService.findAll()
                    .filter(p->p.isState())
                    .filter(t->t.getClient().getNumberDocument().equals(personClientAccount.getClient().getNumberDocument()))
                    .filter(y->y.getTypeAccount().getName().equals(TypeAccount.SAVINGACCOUNT.name()))
                    .switchIfEmpty(iPersonClientAccountService.save(personClientAccount))
                    .map(x->personClientAccountDto);
            return Mono.from(temp);
        }
        if(typeAccount.equals(TypeAccount.CURRENTACCOUNT.name()) && typeClient.equals(TypeClient.Person.name())){
            Flux<PersonClientAccountDto> personTemp = iPersonClientAccountService.findAll()
                    .filter(p->p.isState())
                    .filter(t->t.getClient().getNumberDocument().equals(personClientAccount.getClient().getNumberDocument()))
                    .filter(o->o.getTypeAccount().getName().equals(TypeAccount.CURRENTACCOUNT.name()))
                    .switchIfEmpty(iPersonClientAccountService.save(personClientAccount))
                    .map(x->personClientAccountDto);
            return Mono.from(personTemp);

        }
        if(typeAccount.equals(TypeAccount.DEPOSITACCOUNT.name()) && typeClient.equals(TypeClient.Person.name())){
            Mono<PersonClientAccount> entity = iPersonClientAccountService.save(personClientAccount);
            return entity.map(x->map(x,PersonClientAccountDto.class));
        }else{
            return Mono.error(new Exception());
        }

    }

    public Flux<PersonClientAccountDto> findAll(){
        return iPersonClientAccountService.findAll().map(x->map(x,PersonClientAccountDto.class));
    }

    public Mono<PersonClientAccountDto> update(PersonClientAccountDto personClientAccountDto){
        return iPersonClientAccountService.findById(personClientAccountDto.getId())
                .switchIfEmpty(Mono.error(new ModelNotFoundException()))
                .flatMap(p->{
                    PersonClientAccount personClientAccount =  map(personClientAccountDto,PersonClientAccount.class);
                    personClientAccount.setUpdatedAt(LocalDateTime.now());
                    return iPersonClientAccountService.save(personClientAccount).map(y->map(y,PersonClientAccountDto.class));
                });
    }
    public Mono<PersonClientAccountDto> findById(String id){
        return iPersonClientAccountService.findById(id)
                .switchIfEmpty(Mono.error(new ModelNotFoundException()))
                .map(x-> map(x,PersonClientAccountDto.class));
    }

    public Mono<PersonClientAccountDto> findByDocumentNumberDocumentTypeAndAccountNumber(String documentNumber,
                                                                                         String documentType,
                                                                                         String accountNumber) {
        return iPersonClientAccountService.findByDocumentNumberAndDocumentTypeAndAccountNumber(documentNumber,
                documentType,accountNumber)
                .switchIfEmpty(Mono.error(new ModelNotFoundException()))
                .map(x -> map(x, PersonClientAccountDto.class));
    }

    public Mono<Void> delete(PersonClientAccountDto personClientAccountDto)
    {
        return iPersonClientAccountService.findById(personClientAccountDto.getId())
                .switchIfEmpty(Mono.error(new ModelNotFoundException()))
                .flatMap(x-> iPersonClientAccountService.deleteById(personClientAccountDto.getId()));
    }
}
