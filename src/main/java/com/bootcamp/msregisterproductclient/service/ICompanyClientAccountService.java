package com.bootcamp.msregisterproductclient.service;

import com.bootcamp.msregisterproductclient.entity.CompanyClientAccount;
import com.bootcamp.msregisterproductclient.util.ICrud;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompanyClientAccountService extends ICrud<CompanyClientAccount, String> {
}
