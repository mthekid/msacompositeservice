package com.starlab.msa.composite.microbiome.service.api;

import com.starlab.msa.composite.microbiome.service.api.domain.MicroBiomeData;
import com.starlab.msa.composite.microbiome.service.api.domain.MicrobiomeAggregate;
import com.starlab.msa.composite.microbiome.service.api.domain.StressAnswer;
import com.starlab.msa.composite.microbiome.service.api.domain.UserInfo;
import com.starlab.msa.composite.microbiome.service.api.http.NotFoundException;
import com.starlab.msa.composite.microbiome.service.api.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompositeServiceImpl {
    private ServiceUtil serviceUtil;
    private MicrobiomeCompositeIntegration integration;

    @Autowired
    public CompositeServiceImpl(ServiceUtil serviceUtil, MicrobiomeCompositeIntegration integration) {
        this.serviceUtil = serviceUtil;
        this.integration = integration;
    }

    @GetMapping("/userinfos")
    public List<UserInfo> getAllUserInfos() {

        List<UserInfo> userInfos = integration.getUserInfos();
        if(userInfos == null) throw  new NotFoundException("No user founded");

        return userInfos;
    }

    @GetMapping(value = "/composite/recent/{user-id}", produces = "application/json")
    public MicrobiomeAggregate getRecentCompositeData(
            @PathVariable("user-id") Long userId) {

        UserInfo userInfo = integration.getUserInfo(userId);
//        System.out.println(userInfo);
        StressAnswer stressAnswer = integration.getUserRecentStressAnswer(userId);

        MicroBiomeData microBiomeData = integration.getUserRecentMicrobiomeData(userId);
        System.out.println(microBiomeData);

        if(userInfo == null) throw  new NotFoundException("No user founded");
        if(stressAnswer == null) throw  new NotFoundException("No StressAnswer founded");
        if(microBiomeData == null) throw  new NotFoundException("No MicrobiomeData founded");

        MicrobiomeAggregate microbiomeAggregate = new MicrobiomeAggregate();
        microbiomeAggregate.setStressAnswer(stressAnswer);
        microbiomeAggregate.setUserInfo(userInfo);
        microbiomeAggregate.setMicroBiomeData(microBiomeData);

        return microbiomeAggregate;
    }
}
