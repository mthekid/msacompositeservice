package com.starlab.msa.composite.microbiome.service.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.catalina.User;

import java.util.Date;

@Getter
@ToString
@Builder
public class UserInfo {

    private Long id;
    private String name;
    private String alias;
    private double weight;
    private Date birthDay;
    private String gender;
    private String role;

    public UserInfo() {}

    public UserInfo(Long id, String name, String alias, double weight, Date birthDay, String gender, String role) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.weight = weight;
        this.birthDay = birthDay;
        this.gender = gender;
        this.role = role;
    }
}
