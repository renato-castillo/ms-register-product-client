package com.bootcamp.msregisterproductclient.util;

import com.bootcamp.msregisterproductclient.dto.CompanyClientAccountDto;
import com.bootcamp.msregisterproductclient.dto.PersonClientAccountDto;
import com.bootcamp.msregisterproductclient.exception.GenericException;
import com.bootcamp.msregisterproductclient.service.ICompanyClientAccountService;
import com.bootcamp.msregisterproductclient.service.IPersonClientAccountService;
import com.bootcamp.msregisterproductclient.webclient.IBusinessAccountService;
import com.bootcamp.msregisterproductclient.webclient.IPersonalAccountService;
import com.bootcamp.msregisterproductclient.webclient.dto.BusinessAccountDto;
import com.bootcamp.msregisterproductclient.webclient.dto.PersonalAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountValidationCreationService {

    @Autowired
    private IPersonClientAccountService personClientAccountService;

    @Autowired
    private IBusinessAccountService businessAccountService;

    @Autowired
    private IPersonalAccountService personalAccountService;

    @Autowired
    private ICompanyClientAccountService companyClientAccountService;

    public Mono<Boolean> validateOpenBalance(PersonalAccountDto personalAccountDto,
                                             PersonClientAccountDto personClientAccountDto) {

        if(personClientAccountDto.getBalance() >= personalAccountDto.getMinOpenBalance()) {
            return Mono.just(true);
        }

        return Mono.error(new GenericException("Open Balance is not valid, the first balance will be :" + personalAccountDto.getMinOpenBalance()));
    }

    public Mono<Boolean> validateOpenBalance(BusinessAccountDto businessAccountDto,
                                             CompanyClientAccountDto companyClientAccountDto) {

        if(companyClientAccountDto.getBalance() >= businessAccountDto.getMinOpenBalance()) {
            return Mono.just(true);
        }

        return Mono.error(new GenericException("Open Balance is not valid, the first balance will be :" + businessAccountDto.getMinOpenBalance()));
    }

    public Mono<Boolean> validateMaxPerClient(PersonalAccountDto personalAccountDto,
                                           PersonClientAccountDto personClientAccountDto) {

        return personClientAccountService.findByDocumentNumberAndDocumentTypeAndTypeAccountName(personClientAccountDto.getClient().getNumberDocument(),
                        personClientAccountDto.getClient().getDocumentType(), personClientAccountDto.getTypeAccount().getName())
                .collectList()
                .flatMap(x ->(x.size() == personalAccountDto.getMaxPerClient()) ? Mono.error(new GenericException("Limit account per client exceed")) : Mono.just(true));
    }

    public Mono<Boolean> validateMaxPerClient(BusinessAccountDto businessAccountDto,
                                               CompanyClientAccountDto companyClientAccountDto) {
        return companyClientAccountService.findByDocumentNumberAndDocumentTypeAndTypeAccountName(companyClientAccountDto.getClient().getNumberDocument(),
                        companyClientAccountDto.getClient().getDocumentType(), companyClientAccountDto.getTypeAccount().getName())
                .collectList()
                .flatMap(x -> (x.size() == businessAccountDto.getMaxPerClient() ? Mono.error(new GenericException("Limit account per client exceed")) : Mono.just(true)));
    }

    public Mono<PersonalAccountDto> validateTypeAccountExists(PersonClientAccountDto personClientAccountDto) {
        return personalAccountService.findByName(personClientAccountDto.getTypeAccount().getName())
                .switchIfEmpty(Mono.error(new GenericException("Account Type Not Found")))
                .onErrorResume(Mono::error);
    }

    public Mono<BusinessAccountDto> validateTypeAccountExists(CompanyClientAccountDto companyClientAccountDto) {
        return businessAccountService.findByName(companyClientAccountDto.getTypeAccount().getName())
                .switchIfEmpty(Mono.error(new GenericException("Account Type Not Found")))
                .onErrorResume(Mono::error);
    }


}
