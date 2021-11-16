package com.starlab.msa.composite.microbiome.service.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.GsonBuildConfig;
import com.starlab.msa.composite.microbiome.service.api.domain.MicroBiomeData;
import com.starlab.msa.composite.microbiome.service.api.domain.MicrobiomeAggregate;
import com.starlab.msa.composite.microbiome.service.api.domain.StressAnswer;
import com.starlab.msa.composite.microbiome.service.api.domain.UserInfo;
import com.starlab.msa.composite.microbiome.service.api.http.HttpErrorInfo;
import com.starlab.msa.composite.microbiome.service.api.http.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
public class MicrobiomeCompositeIntegration {

    private static final Logger LOG = LoggerFactory.getLogger(MicrobiomeCompositeIntegration.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final String gutInfoServiceUrl;
    private final String mentalhealthServiceUrl;
    private final String userInfoServiceUrl;

    @Autowired
    public MicrobiomeCompositeIntegration(
            RestTemplate restTemplate,
            ObjectMapper mapper,

            @Value("${app.gutInfo-service.host}") String gutInfoServiceHost,
            @Value("${app.gutInfo-service.port}") String gutInfoServicePort,

            @Value("${app.mentalhealth-service.host}") String mentalhealthServiceHost,
            @Value("${app.mentalhealth-service.port}") String mentalhealthServicePort,

            @Value("${app.userInfo-service.host}") String userInfoServiceHost,
            @Value("${app.userInfo-service.port}") String userInfoServicePort
    ) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        gutInfoServiceUrl = "http://" + gutInfoServiceHost + ":" + gutInfoServicePort + "/ncs-kit/microbiome/";
        mentalhealthServiceUrl = "http://" + mentalhealthServiceHost + ":" + mentalhealthServicePort + "/survey/";
        userInfoServiceUrl = "http://" + userInfoServiceHost + ":" + userInfoServicePort + "/user/";
    }

    public List<UserInfo> getUserInfos() {
        try {
            String url = userInfoServiceUrl + "all";
            LOG.debug("Will call getUserInfos API on URL {}", url);

            ResponseEntity<UserInfo[]> response = restTemplate.getForEntity(url, UserInfo[].class);
            UserInfo[] userinfos = response.getBody();

            LOG.debug("Found a userInfos");

            return Arrays.asList(userinfos);
        } catch (HttpClientErrorException ex) {
            switch (ex.getStatusCode()) {
                case NOT_FOUND:
                    throw new NotFoundException(getErrorMessage(ex));
                default:
                    LOG.warn("Got a unexpected HTTP error : {}, will rethrow it", ex.getStatusCode());
                    LOG.warn("Error body: {}", ex.getResponseBodyAsString());
                    throw ex;
            }
        }
    }

    public UserInfo getUserInfo(Long userId) {
        try {
            String url = userInfoServiceUrl + userId;
            LOG.debug("Will call getUserInfos API on URL {}", url);

            System.out.println(url);

            UserInfo userinfo = restTemplate.getForObject(url, UserInfo.class);

            LOG.debug("Found a userInfo");

            return userinfo;
        } catch (HttpClientErrorException ex) {
            switch (ex.getStatusCode()) {
                case NOT_FOUND:
                    throw new NotFoundException(getErrorMessage(ex));
                default:
                    LOG.warn("Got a unexpected HTTP error : {}, will rethrow it", ex.getStatusCode());
                    LOG.warn("Error body: {}", ex.getResponseBodyAsString());
                    throw ex;
            }
        }
    }

    public StressAnswer getUserRecentStressAnswer(Long userId) {
        try {
            String url = mentalhealthServiceUrl + "stress-info/recent-test/" + userId;
            LOG.debug("Will call StressAnswer API on URL {}", url);

            ResponseEntity<StressAnswer> response = restTemplate.getForEntity(url, StressAnswer.class);
            StressAnswer stressAnswer = response.getBody();

            LOG.debug("Found a StressAnswer");

            return stressAnswer;
        } catch (HttpClientErrorException ex) {
            switch (ex.getStatusCode()) {
                case NOT_FOUND:
                    throw new NotFoundException(getErrorMessage(ex));
                default:
                    LOG.warn("Got a unexpected HTTP error : {}, will rethrow it", ex.getStatusCode());
                    LOG.warn("Error body: {}", ex.getResponseBodyAsString());
                    throw ex;
            }
        }
    }

    public MicroBiomeData getUserRecentMicrobiomeData(Long userId) {
        try {
            String url = gutInfoServiceUrl + userId;
            LOG.debug("Will call StressAnswer API on URL {}", url);
            System.out.println(url);

            MicroBiomeData microBiomeData = restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<MicroBiomeData>() {
            }).getBody();

            LOG.debug("Found a StressAnswer");

            return microBiomeData;
        } catch (HttpClientErrorException ex) {
            switch (ex.getStatusCode()) {
                case NOT_FOUND:
                    throw new NotFoundException(getErrorMessage(ex));
                default:
                    LOG.warn("Got a unexpected HTTP error : {}, will rethrow it", ex.getStatusCode());
                    LOG.warn("Error body: {}", ex.getResponseBodyAsString());
                    throw ex;
            }
        }
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch (IOException ioex) {
            return ex.getMessage();
        }
    }
}
