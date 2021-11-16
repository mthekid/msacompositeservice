package com.starlab.msa.composite.microbiome.service.api.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

@Getter
@Setter
@ToString
public class BacteriaInfo {

    public BacteriaInfo() {}

    public BacteriaInfo(Long id, String companyType, String bacteriaType, BigInteger bacteriaNum, String bacteiaName) {
        this.id = id;
        this.companyType = companyType;
        this.bacteriaType = bacteriaType;
        this.bacteriaNum = bacteriaNum;
        this.bacteiaName = bacteiaName;
    }

    private Long id;
    private String companyType;
    private String bacteriaType;
    private BigInteger bacteriaNum;
    private String bacteiaName;

}
