package com.bootcamp.msregisterproductclient.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TypeAccount {
    private String name;
    private Integer maxMonthlyMovements;
    private Integer limitWithoutCommission;
    private Float commissionTransaction;
}
