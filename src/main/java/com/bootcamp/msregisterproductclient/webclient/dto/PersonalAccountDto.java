package com.bootcamp.msregisterproductclient.webclient.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalAccountDto {

    private String id;

    private String name;

    private Float maintenanceCommission;

    private Integer maxMonthlyMovements;

    private Integer uniqueMovementDay;

    private Integer maxPerClient;
}
