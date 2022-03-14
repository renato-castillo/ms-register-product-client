package com.bootcamp.msregisterproductclient.service;

import com.bootcamp.msregisterproductclient.entity.PersonClientAccount;
import com.bootcamp.msregisterproductclient.util.ICrud;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonClientAccountService  extends ICrud<PersonClientAccount, String> {
}
