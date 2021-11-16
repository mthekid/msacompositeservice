package com.starlab.msa.composite.microbiome.service.api.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class MicrobiomeAggregate {


    UserInfo userInfo;
    StressAnswer stressAnswer;
    MicroBiomeData microBiomeData;

    public MicrobiomeAggregate() {}

    public MicrobiomeAggregate(UserInfo userInfo, StressAnswer stressAnswer, MicroBiomeData microBiomeData) {
        this.userInfo = userInfo;
        this.stressAnswer = stressAnswer;
        this.microBiomeData = microBiomeData;
    }
}
