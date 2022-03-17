package com.bootcamp.msregisterproductclient.resource;

import com.bootcamp.msregisterproductclient.dto.CompanyClientAccountDto;
import com.bootcamp.msregisterproductclient.dto.PersonClientAccountDto;
import com.bootcamp.msregisterproductclient.entity.CompanyClientAccount;
import com.bootcamp.msregisterproductclient.entity.TypeAccount;
import com.bootcamp.msregisterproductclient.exception.GenericException;
import com.bootcamp.msregisterproductclient.util.MapperUtil;
import com.bootcamp.msregisterproductclient.service.ICompanyClientAccountService;
import com.bootcamp.msregisterproductclient.webclient.IBusinessAccountService;
import com.bootcamp.msregisterproductclient.webclient.dto.BusinessAccountDto;
import com.bootcamp.msregisterproductclient.webclient.dto.PersonalAccountDto;
import org.apache.commons.lang.NotImplementedException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CompanyClientAccountResource  extends MapperUtil {

    @Autowired
    private ICompanyClientAccountService iCompanyClientAccountService;

    @Autowired
    private IBusinessAccountService businessAccountService;

    private Mono<Boolean> validateCreation(BusinessAccountDto businessAccountDto,
                                           CompanyClientAccountDto companyClientAccountDto) {

        return iCompanyClientAccountService.findByDocumentNumberAndDocumentTypeAndTypeAccountName(companyClientAccountDto.getClient().getNumberDocument(),
                        companyClientAccountDto.getClient().getDocumentType(), companyClientAccountDto.getTypeAccount().getName())
                .collectList()
                .flatMap(x -> {
                    if(x.size() == businessAccountDto.getMaxPerClient()) {
                        return Mono.error(new GenericException("Limit account per client exceed"));
                    }

                    return Mono.just(true);
                });
    }

    public Mono<CompanyClientAccountDto> create(CompanyClientAccountDto companyClientAccountDto){

        CompanyClientAccount companyClientAccount = map(companyClientAccountDto,CompanyClientAccount.class);

        if(companyClientAccount.getClient().getClientType().equalsIgnoreCase("BUSINESS")) {
            businessAccountService.findByName(companyClientAccountDto.getTypeAccount().getName())
                    .switchIfEmpty(Mono.error(new GenericException("Account Type Not Found")))
                    .flatMap(x -> {
                        String account = UUID.randomUUID().toString();
                        companyClientAccountDto.setTypeAccount(new TypeAccount(x.getName(), x.getMaxPerClient()));
                        companyClientAccountDto.setAccountNumber(account);

                        return iCompanyClientAccountService.save(companyClientAccount).map(entity -> map(entity, CompanyClientAccountDto.class));

                    }).onErrorResume(Mono::error);
        }

        return Mono.error(new GenericException("Client Type Not Supported"));
    }

    public Flux<CompanyClientAccountDto> findAll(){
        return iCompanyClientAccountService.findAll().map(x->map(x,CompanyClientAccountDto.class));
    }

    public Mono<CompanyClientAccountDto> update(CompanyClientAccountDto companyClientAccountDto){
        return iCompanyClientAccountService.findById(companyClientAccountDto.getId())
                .switchIfEmpty(Mono.error(new Exception()))
                .flatMap(p->{
                    CompanyClientAccount companyClientAccount =  map(companyClientAccountDto,CompanyClientAccount.class);
                    companyClientAccount.setUpdatedAt(LocalDateTime.now());
                    return iCompanyClientAccountService.save(companyClientAccount).map(y->map(y,CompanyClientAccountDto.class));
                });
    }

    public Mono<CompanyClientAccountDto> findById(String id){
        return iCompanyClientAccountService.findById(id)
                .switchIfEmpty(Mono.error(new Exception()))
                .map(x-> map(x,CompanyClientAccountDto.class));
    }

    public Mono<CompanyClientAccountDto> findByDocumentNumberAndDocumentTypeAndAccount(String documentNumber,
                                                                                       String documentType,
                                                                                       String account){
        return iCompanyClientAccountService.findByDocumentNumberAndDocumentTypeAndAccount(documentNumber,
                        documentType, account)
                .switchIfEmpty(Mono.error(new Exception()))
                .map(x-> map(x,CompanyClientAccountDto.class));
    }

    public Mono<Void> delete(CompanyClientAccountDto companyClientAccountDto)
    {
        return iCompanyClientAccountService.findById(companyClientAccountDto.getId())
                .switchIfEmpty(Mono.error(new Exception()))
                .flatMap(x-> iCompanyClientAccountService.deleteById(companyClientAccountDto.getId()));
    }

}
