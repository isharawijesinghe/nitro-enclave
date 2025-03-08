package com.nitro.enclave.handlers;

import com.nitro.common.enums.Actions;
import com.nitro.common.requests.EnclaveDecryptRequest;
import com.nitro.common.responses.EnclaveDecryptResponse;
import com.nitro.enclave.enclave.handler.AbstractActionHandler;
import com.nitro.enclave.enclave.kms.KMSClient;
import com.nitro.enclave.enclave.nsm.NsmClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class EnclaveReceiveHandler extends AbstractActionHandler<EnclaveDecryptRequest, EnclaveDecryptResponse> {

    private final Logger logger = LogManager.getLogger(EnclaveReceiveHandler.class);

    private final NsmClient nsmClient;

    private final KMSClient kmsClient;

    public EnclaveReceiveHandler(NsmClient nsmClient, KMSClient kmsClient){
        this.nsmClient = nsmClient;
        this.kmsClient = kmsClient;
    }

    @Override
    public boolean canHandle(String action) {
        return Actions.ECHO.name().equalsIgnoreCase(action);
    }

    @Override
    public EnclaveDecryptResponse handle(EnclaveDecryptRequest enclaveRequest) {
        EnclaveDecryptResponse result = new EnclaveDecryptResponse();
        String base64EncodedString = kmsClient.decrypt(enclaveRequest.getAwsCredentials(),  enclaveRequest.getValue());
        byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedString);
        String decodedString = new String(decodedBytes);
        result.setValue("Base64 encoded string " + base64EncodedString + " Decrypted result from nitro enclave is " + decodedString);
        return result;
    }
}
