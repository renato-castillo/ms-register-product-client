package com.bootcamp.msregisterproductclient.service;

import com.bootcamp.msregisterproductclient.entity.CreditClient;
import com.bootcamp.msregisterproductclient.util.ICrud;
import org.springframework.stereotype.Repository;

@Repository
public interface ICreditClientService  extends ICrud<CreditClient, String> {

}
