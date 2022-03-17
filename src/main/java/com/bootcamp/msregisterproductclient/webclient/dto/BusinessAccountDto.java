package com.bootcamp.msregisterproductclient.webclient.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessAccountDto {

    private String id;

    private String name;

    private Integer maxPerClient;

    private Float minOpenBalance;

}
