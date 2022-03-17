package com.bootcamp.msregisterproductclient.resource;

import com.bootcamp.msregisterproductclient.dto.CompanyClientAccountDto;
import com.bootcamp.msregisterproductclient.dto.PersonClientAccountDto;
import com.bootcamp.msregisterproductclient.entity.CompanyClientAccount;
import com.bootcamp.msregisterproductclient.entity.PersonClientAccount;
import com.bootcamp.msregisterproductclient.entity.TypeAccount;
import com.bootcamp.msregisterproductclient.exception.GenericException;
import com.bootcamp.msregisterproductclient.exception.ModelNotFoundException;
import com.bootcamp.msregisterproductclient.service.IPersonClientAccountService;
import com.bootcamp.msregisterproductclient.util.MapperUtil;
import com.bootcamp.msregisterproductclient.webclient.IPersonalAccountService;
import com.bootcamp.msregisterproductclient.webclient.dto.PersonalAccountDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PersonClientAccountResource extends MapperUtil {

    @Autowired
    private IPersonClientAccountService iPersonClientAccountService;

    @Autowired
    private IPersonalAccountService personalAccountService;

    private Mono<Boolean> validateCreation(PersonalAccountDto personalAccountDto,
                                           PersonClientAccountDto personClientAccountDto) {

        return iPersonClientAccountService.findByDocumentNumberAndDocumentTypeAndTypeAccountName(personClientAccountDto.getClient().getNumberDocument(),
                personClientAccountDto.getClient().getDocumentType(), personClientAccountDto.getTypeAccount().getName())
                .collectList()
                .flatMap(x -> {
                    if(x.size() == personalAccountDto.getMaxPerClient()) {
                        return Mono.error(new GenericException("Limit account per client exceed"));
                    }

                    return Mono.just(true);
                });
    }

    public Mono<PersonClientAccountDto> create(PersonClientAccountDto personClientAccountDto){
        PersonClientAccount personClientAccount = map(personClientAccountDto,PersonClientAccount.class);
        personClientAccount.setId(new ObjectId().toString());
        personClientAccount.setCreatedAt(LocalDateTime.now());

        if(personClientAccountDto.getClient().getClientType().equalsIgnoreCase("PERSONAL")) {
            return personalAccountService.findByName(personClientAccountDto.getTypeAccount().getName()).onErrorResume(Mono::error)
                    .flatMap(x -> validateCreation(x, personClientAccountDto).flatMap(bool -> {
                        String account = UUID.randomUUID().toString();
                        personClientAccount.setTypeAccount(new TypeAccount(x.getName(), x.getMaxMonthlyMovements()));
                        personClientAccount.setAccountNumber(account);

                        return iPersonClientAccountService.save(personClientAccount).map(y -> map(y, PersonClientAccountDto.class));
                    }).onErrorResume(Mono::error));
        }

        return Mono.error(new GenericException("Client Type Not Supported"));


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
