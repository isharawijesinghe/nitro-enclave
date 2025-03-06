package com.nitro.enclave.enclave.handler;

import com.nitro.common.requests.EnclaveServiceRequest;

public interface ActionHandler<IN, OUT> {

	/**
	 * Can this handler handle the given request?
	 *
	 * @param request the incoming request
	 * @return true if yes, false if no
	 */
	boolean canHandle(EnclaveServiceRequest<IN> request);

	/**
	 * Handle the request.
	 *
	 * @param request the incoming request
	 * @return output data
	 */
	OUT handle(EnclaveServiceRequest<IN> request);

}
