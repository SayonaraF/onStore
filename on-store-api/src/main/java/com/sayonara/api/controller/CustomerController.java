package com.sayonara.api.controller;

import com.sayonara.api.service.CustomerGrpcClientService;
import com.sayonara.api.service.RestTemplateService;
import com.sayonara.core.dto.CustomerDto;
import com.sayonara.grpc.CustomerProto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class CustomerController {

    private RestTemplateService restTemplateService;
    private CustomerGrpcClientService customerGrpcClientService;

    @GetMapping("/random_customer")
    public String getCustomerFromSecondService() {
        log.info("Get Customer from Second Service");
        CustomerDto customerDto = restTemplateService.fetchCustomerDto();

        return customerDto.toString();
    }

    @GetMapping("/random_customer_grpc")
    public String getCustomerFromSecondServiceGrpc() {
        log.info("Get Customer from Second Service Grpc");
        CustomerProto.CustomerDto customerDto = customerGrpcClientService.getRandomCustomer();

        return customerDto.toString();
    }
}
