package com.nitro.enclave.enclave.server.network;

import java.io.IOException;

public interface ListenerConsumer {

	void process(ListenerConnection connection) throws IOException;

}
