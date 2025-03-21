package com.sayonara.core.service;

import com.sayonara.core.dto.CustomerDto;
import org.jeasy.random.EasyRandom;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    public CustomerDto getRandomCustomer() {

        EasyRandom generator = new EasyRandom();

        return generator.nextObject(CustomerDto.class);
    }

}
