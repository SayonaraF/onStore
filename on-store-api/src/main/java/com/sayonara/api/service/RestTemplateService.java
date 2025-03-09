package com.sayonara.api.service;

import com.sayonara.core.dto.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@AllArgsConstructor
public class RestTemplateService {

    private RestTemplate restTemplate;

    public CustomerDto fetchCustomerDto() {
        String url = "http://localhost:8000/generate-random-customer";

        log.info("Get CustomerDto from second service, url: {}", url);

        return restTemplate.getForObject(url, CustomerDto.class);
    }

}
