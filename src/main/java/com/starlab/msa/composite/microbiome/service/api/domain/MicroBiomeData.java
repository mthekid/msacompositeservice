package com.starlab.msa.composite.microbiome.service.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigInteger;
import java.util.*;

@Getter
@Setter
@ToString

public class MicroBiomeData {


    private Long id;
    private Long userId;
    private Date createDate;
    private List<BacteriaInfo> bacteriaInfos = new ArrayList<>();

    public MicroBiomeData() {}
}
