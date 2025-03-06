package com.nitro.host.controller;

import com.nitro.common.enums.Actions;
import com.nitro.common.requests.EnclaveDecryptRequest;
import com.nitro.common.requests.EnclaveServiceRequest;
import com.nitro.common.responses.EnclaveServiceResponse;
import com.nitro.enclave.host.NitroEnclaveClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HostServerRest {

    private final NitroEnclaveClient client;

    public HostServerRest(NitroEnclaveClient client) {
        this.client = client;
    }

    @GetMapping("/ping")
    public String sayHello() {
        return "Hello";
    }

    @PostMapping("/echo")
    public String sendEchoRequest(EnclaveDecryptRequest enclaveDecryptRequest){
        EnclaveServiceRequest<EnclaveDecryptRequest> request = new EnclaveServiceRequest<>();
        request.setData(enclaveDecryptRequest);
        request.setAction(Actions.ECHO.name());
        EnclaveServiceResponse<EnclaveDecryptRequest> response = client.send(request);
        if (response.getIsError()) { // Handle the response
            String errorMessage = String.format("Something went wrong: %s", response.getError());
            return "error " + errorMessage;
        } else {
            String resultValue = response.getData().getValue();
            return "value " +  resultValue + " executionTimeMs " + response.getDuration();
        }
    }
}
