package com.starlab.msa.composite.microbiome.service.api.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;


@Getter
@ToString
@Builder
public class StressAnswer {

    private Long id;
    private Long userId;
    private Date createDate;
    private int q1;
    private int q2;
    private int q3;
    private int q4;
    private int q5;

    public StressAnswer() {}

    public StressAnswer(Long id, Long userId, Date createDate, int q1, int q2, int q3, int q4, int q5) {
        this.id = id;
        this.userId = userId;
        this.createDate = createDate;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
    }
}
