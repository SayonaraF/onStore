package com.sayonara.api.service;

import com.google.protobuf.StringValue;
import com.sayonara.grpc.CustomerProto;
import com.sayonara.grpc.CustomerServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class CustomerGrpcClientService {

    @GrpcClient("customerService")
    private CustomerServiceGrpc.CustomerServiceBlockingStub stub;

    public CustomerProto.CustomerDto getRandomCustomer() {
        return stub.getCustomer(StringValue.of("request"));
    }
}
